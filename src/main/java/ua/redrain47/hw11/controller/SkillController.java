package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.repository.SkillRepository;
import ua.redrain47.hw11.repository.io.IOSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private SkillRepository skillRepo;

    public SkillController() {
        this.skillRepo = new IOSkillRepositoryImpl();
    }

    public Skill getDataById(Long id) {
        return skillRepo.getById(id);
    }

    public List<Skill> getAllData() {
        return skillRepo.getAll();
    }

    public boolean addData(Skill addedSkill) {
        return skillRepo.save(addedSkill);
    }

    public boolean updateDataById(Skill updatedSkill) {
        return skillRepo.update(updatedSkill);
    }

    public boolean deleteDataById(Long id) {
        return skillRepo.deleteById(id);
    }

    public boolean isEmptyData() {
        return skillRepo.isEmpty();
    }

    public Long getLastId() {
        return skillRepo.getAutoIncrId();
    }
}
