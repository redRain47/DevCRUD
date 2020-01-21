package ua.redrain47.hw11.repository.io;

import ua.redrain47.hw11.model.Account;
import ua.redrain47.hw11.model.Developer;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.repository.DeveloperRepository;
import ua.redrain47.hw11.util.IOUtil;

import java.util.*;

public class IODeveloperRepositoryImpl implements DeveloperRepository {
    private IOUtil fileRepo = new IOUtil("src\\main\\resources\\files\\developers.txt");
    private static final String SPLITTER = " ";
    private Long autoIncrId = 0L;

    public IODeveloperRepositoryImpl() {
        ArrayList<Developer> developers = (ArrayList<Developer>) this.getAll();

        if (developers != null) {
            for (Developer developer : developers) {
                autoIncrId = Math.max(developer.getId(), autoIncrId);
            }
        }
    }

    @Override
    public boolean save(Developer newDeveloper) {
        if (newDeveloper != null) {
            newDeveloper.setId(++autoIncrId);
            fileRepo.save(newDeveloper.toSerializableString());
            return true;
        }

        return false;
    }

    @Override
    public Developer getById(Long searchId) {
        if (searchId != null) {
            ArrayList<Developer> developers = (ArrayList<Developer>) this.getAll();

            for (Developer developer : developers) {
                if (developer.getId().equals(searchId)) {
                    return developer;
                }
            }
        }

        return null;
    }

    @Override
    public List<Developer> getAll() {
        ArrayList<String> records = (ArrayList<String>) fileRepo.readAll();
        return parseDeveloperList(records);
    }

    private List<Developer> parseDeveloperList(List<String> recordList) {
        ArrayList<Developer> developerList = null;

        if (recordList != null) {
            String[] splitData;
            Long curId;
            String curFirstName;
            String curLastName;
            Long curAccountId;
            Set<Skill> curSkillSet;
            Account curAccount = null;
            IOAccountRepositoryImpl accountRepo = new IOAccountRepositoryImpl();

            developerList = new ArrayList<>(20);

            for (String record : recordList) {
                if (!Objects.equals(record, "")) {
                    splitData = record.split(SPLITTER);
                    curId = Long.parseLong(splitData[0]);
                    curFirstName = splitData[1];
                    curLastName = splitData[2];
                    splitData[3] = splitData[3]
                            .substring(1, splitData[3].length() - 1);

                    curSkillSet = parseSkillSet(splitData[3]);

                    if (splitData.length == 5) {
                        curAccountId = Long.parseLong(splitData[4]);
                        curAccount = accountRepo.getById(curAccountId);
                    }

                    developerList.add(new Developer(curId, curFirstName,
                            curLastName, curSkillSet, curAccount));
                    curAccount = null;
                }
            }
        }

        return developerList;
    }

    private Set<Skill> parseSkillSet(String record) {
        IOSkillRepositoryImpl skillRepo = new IOSkillRepositoryImpl();
        Set<Skill> skillSet = null;
        String[] curSkillIds;
        Long tempSkillId;

        if (!Objects.equals(record, "")) {
            skillSet = new HashSet<>();

            if (record.contains(",")) {
                curSkillIds = record.split(",");

                for (String idStr : curSkillIds) {
                    tempSkillId = Long.parseLong(idStr);
                    skillSet.add(skillRepo.getById(tempSkillId));
                }
            } else {
                tempSkillId = Long.parseLong(record);
                skillSet.add(skillRepo.getById(tempSkillId));
            }
        }

        return skillSet;
    }

    @Override
    public boolean update(Developer updatedDeveloper) {
        if (updatedDeveloper != null) {
            Long searchId = updatedDeveloper.getId();
            ArrayList<Developer> developers = (ArrayList<Developer>) this.getAll();

            if (developers != null) {
                fileRepo.clear();

                for (Developer developer : developers) {
                    if (developer.getId().equals(searchId)) {
                        developer = updatedDeveloper;
                    }

                    fileRepo.save(developer.toSerializableString());
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(Long deletedId) {
        if (deletedId != null) {
            ArrayList<Developer> developers = (ArrayList<Developer>) this.getAll();

            if (developers != null) {
                fileRepo.clear();
                for (Developer developer : developers) {
                    if (!developer.getId().equals(deletedId)) {
                        fileRepo.save(developer.toSerializableString());
                    }
                }

                if (fileRepo.isEmpty()) {
                    autoIncrId = 0L;
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return fileRepo.isEmpty();
    }

    @Override
    public Long getAutoIncrId() {
        return autoIncrId;
    }
}
