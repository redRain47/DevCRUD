package ua.redrain47.hw11.service;

import lombok.extern.slf4j.Slf4j;
import ua.redrain47.hw11.exceptions.ConnectionIssueException;
import ua.redrain47.hw11.exceptions.DeletingReferencedRecordException;
import ua.redrain47.hw11.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.hw11.model.Skill;
import ua.redrain47.hw11.repository.SkillRepository;
import ua.redrain47.hw11.repository.jdbc.JdbcSkillRepositoryImpl;

import java.util.List;

@Slf4j
public class SkillService {
    private SkillRepository skillRepo;

    public SkillService() throws ConnectionIssueException {
        try {
            this.skillRepo = new JdbcSkillRepositoryImpl();
            log.debug("SkillService -> Instance created");
        } catch (ConnectionIssueException e) {
            log.error("SkillService -> " + e);
            throw e;
        }
    }

    public Skill getDataById(Long id) throws ConnectionIssueException {
        try {
            Skill skill = skillRepo.getById(id);
            log.debug("SkillService -> Got data by id");
            return skill;
        } catch (ConnectionIssueException e) {
            log.error("SkillService -> " + e);
            throw e;
        }
    }

    public List<Skill> getAllData() throws ConnectionIssueException {
        try {
            List<Skill> skillList = skillRepo.getAll();
            log.debug("SkillService -> Got all data");
            return skillList;
        } catch (ConnectionIssueException e) {
            log.error("SkillService -> " + e);
            throw e;
        }
    }

    public boolean addData(Skill addedSkill)
            throws SuchEntityAlreadyExistsException, ConnectionIssueException {
        try {
            skillRepo.save(addedSkill);
            log.debug("SkillService -> Added data");
            return true;
        } catch (SuchEntityAlreadyExistsException
                | ConnectionIssueException e) {
            log.error("SkillService -> " + e);
            throw e;
        }
    }

    public boolean updateDataById(Skill updatedSkill)
            throws SuchEntityAlreadyExistsException, ConnectionIssueException {
        try {
            skillRepo.update(updatedSkill);
            log.debug("SkillService -> Updated data by id");
            return true;
        } catch (SuchEntityAlreadyExistsException
                | ConnectionIssueException e) {
            log.error("SkillService -> " + e);
            throw e;
        }
    }

    public boolean deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            ConnectionIssueException {
        try {
            skillRepo.deleteById(id);
            log.debug("SkillService -> Deleted data by id");
            return true;
        } catch (DeletingReferencedRecordException
                | ConnectionIssueException e) {
            log.error("SkillService -> " + e);
            throw e;
        }
    }
}
