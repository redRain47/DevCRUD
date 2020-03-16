package ua.redrain47.devcrud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.repository.SkillRepository;

import java.util.List;

@Service
@Slf4j
public class SkillService implements Serviceable<Skill, Long> {
    private SkillRepository skillRepo;

    @Autowired
    public SkillService(@Qualifier("skillRepository") SkillRepository skillRepo) {
        this.skillRepo = skillRepo;
    }

    public Skill getDataById(Long id) {
        Skill skill = skillRepo.getById(id);
        log.debug("Got data by id");
        return skill;
    }

    public List<Skill> getAllData() {
        List<Skill> skillList = skillRepo.getAll();
        log.debug("Got all data");
        return skillList;
    }

    public void addData(Skill addedSkill) {
        skillRepo.save(addedSkill);
        log.debug("Added data");
    }

    public void updateDataById(Skill updatedSkill) {
        skillRepo.update(updatedSkill);
        log.debug("Updated data by id");
    }

    public void deleteDataById(Long id) {
        skillRepo.deleteById(id);
        log.debug("Deleted data by id");
    }
}
