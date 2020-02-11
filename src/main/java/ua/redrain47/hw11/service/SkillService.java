package ua.redrain47.hw11.service;

import lombok.extern.slf4j.Slf4j;
import ua.redrain47.hw11.exceptions.DbConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.repository.SkillRepository;
import ua.redrain47.hw11.repository.JdbcSkillRepositoryImpl;

import java.util.List;

@Slf4j
public class SkillService {
    private SkillRepository skillRepo;

    public SkillService() throws DbConnectionIssueException {
        try {
            this.skillRepo = new JdbcSkillRepositoryImpl();
            log.debug("Instance created");
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public SkillService(SkillRepository skillRepo) {
        this.skillRepo = skillRepo;
    }

    public Skill getDataById(Long id) throws DbConnectionIssueException {
        try {
            Skill skill = skillRepo.getById(id);
            log.debug("Got data by id");
            return skill;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public List<Skill> getAllData() throws DbConnectionIssueException {
        try {
            List<Skill> skillList = skillRepo.getAll();
            log.debug("Got all data");
            return skillList;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean addData(Skill addedSkill)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        try {
            skillRepo.save(addedSkill);
            log.debug("Added data");
            return true;
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean updateDataById(Skill updatedSkill)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        try {
            skillRepo.update(updatedSkill);
            log.debug("Updated data by id");
            return true;
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        try {
            skillRepo.deleteById(id);
            log.debug("Deleted data by id");
            return true;
        } catch (DeletingReferencedRecordException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
