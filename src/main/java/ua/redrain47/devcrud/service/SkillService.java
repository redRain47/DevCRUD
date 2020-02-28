package ua.redrain47.devcrud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.redrain47.devcrud.config.RepositoryConfig;
import ua.redrain47.devcrud.exceptions.DbConnectionIssueException;
import ua.redrain47.devcrud.exceptions.DeletingReferencedRecordException;
import ua.redrain47.devcrud.exceptions.SuchEntityAlreadyExistsException;
import ua.redrain47.devcrud.repository.SkillRepository;
import ua.redrain47.devcrud.model.Skill;

import java.util.List;

@Service
@Import(RepositoryConfig.class)
@Slf4j
public class SkillService {
    private SkillRepository skillRepo;

//    public SkillService() throws DbConnectionIssueException {
//        try {
//            this.skillRepo = new JdbcSkillRepositoryImpl();
//            log.debug("Instance created");
//        } catch (DbConnectionIssueException e) {
//            log.error(e.getMessage());
//            throw e;
//        }
//    }

    @ExceptionHandler(DbConnectionIssueException.class)
    public void handleException(DbConnectionIssueException e)
            throws DbConnectionIssueException {
        log.error(e.getMessage());
        throw e;
    }

    @Autowired
    public SkillService(@Qualifier("skillRepository") SkillRepository skillRepo) {
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

    public void addData(Skill addedSkill)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        try {
            skillRepo.save(addedSkill);
            log.debug("Added data");
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void updateDataById(Skill updatedSkill)
            throws SuchEntityAlreadyExistsException, DbConnectionIssueException {
        try {
            skillRepo.update(updatedSkill);
            log.debug("Updated data by id");
        } catch (SuchEntityAlreadyExistsException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteDataById(Long id)
            throws DeletingReferencedRecordException,
            DbConnectionIssueException {
        try {
            skillRepo.deleteById(id);
            log.debug("Deleted data by id");
        } catch (DeletingReferencedRecordException e) {
            log.warn(e.getMessage());
            throw e;
        } catch (DbConnectionIssueException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
