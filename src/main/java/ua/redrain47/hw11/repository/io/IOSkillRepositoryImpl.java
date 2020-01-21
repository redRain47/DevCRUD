package ua.redrain47.hw11.repository.io;

import ua.redrain47.hw11.repository.SkillRepository;
import ua.redrain47.hw11.util.IOUtil;
import ua.redrain47.hw11.model.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IOSkillRepositoryImpl implements SkillRepository {
    private IOUtil fileRepo = new IOUtil("src\\main\\resources\\files\\skills.txt");
    private static final String SPLITTER = " ";
    private Long autoIncrId = 0L;

    public IOSkillRepositoryImpl() {
        ArrayList<Skill> skills = (ArrayList<Skill>) this.getAll();

        if (skills != null) {
            for (Skill skill : skills) {
                autoIncrId = Math.max(skill.getId(), autoIncrId);
            }
        }
    }

    public boolean isEmpty() {
       return fileRepo.isEmpty();
    }

    public boolean save(Skill newSkill) {
        if (newSkill != null) {
            newSkill.setId(++autoIncrId);
            fileRepo.save(newSkill.toString());
            return true;
        }

        return false;
    }

    public Skill getById(Long searchId) {
        if (searchId != null) {
            ArrayList<Skill> skills = (ArrayList<Skill>) this.getAll();

            if (skills != null) {
                for (Skill skill : skills) {
                    if (skill.getId().equals(searchId)) {
                        return skill;
                    }
                }
            }
        }

        return null;
    }

    public List<Skill> getAll() {
        ArrayList<Skill> skills = null;
        ArrayList<String> records = (ArrayList<String>) fileRepo.readAll();

        if (records != null) {
            String[] splitData;
            Long curId;
            String curName;
            skills = new ArrayList<>(20);

            for (String record : records) {
                if (!Objects.equals(record, "")) {
                    splitData = record.split(SPLITTER);
                    curId = Long.parseLong(splitData[0]);
                    curName = splitData[1];
                    skills.add(new Skill(curId, curName));
                }
            }
        }

        return skills;
    }

    public boolean update(Skill updatedSkill) {
        if (updatedSkill != null) {
            Long searchId = updatedSkill.getId();
            ArrayList<Skill> skills = (ArrayList<Skill>) this.getAll();

            if (skills != null) {
                fileRepo.clear();
                for (Skill skill : skills) {
                    if (skill.getId().equals(searchId)) {
                        skill.setName(updatedSkill.getName());
                    }

                    fileRepo.save(skill.toString());
                }

                return true;
            }
        }

        return false;
    }

    public boolean deleteById(Long deletedId) {
        if (deletedId != null) {
            ArrayList<Skill> skills = (ArrayList<Skill>) this.getAll();

            if (skills != null) {
                fileRepo.clear();
                for (Skill skill : skills) {
                    if (!skill.getId().equals(deletedId)) {
                        fileRepo.save(skill.toString());
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
    public Long getAutoIncrId() {
        return autoIncrId;
    }
}
