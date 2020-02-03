package ua.redrain47.hw11.controller;

import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.service.SkillService;

import java.util.List;

public class SkillController {
    private SkillService skillService = new SkillService();

    public SkillController() throws DbConnectionIssueException {
    }

    public Skill getDataById(Long id) throws DbConnectionIssueException {
        return skillService.getDataById(id);
    }

    public List<Skill> getAllData() throws DbConnectionIssueException {
        return skillService.getAllData();
    }

    public boolean addData(Skill addedSkill)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        return skillService.addData(addedSkill);
    }

    public boolean updateDataById(Skill updatedSkill)
            throws DbConnectionIssueException, SuchEntityAlreadyExistsException {
        return skillService.updateDataById(updatedSkill);
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        return skillService.deleteDataById(id);
    }
}
