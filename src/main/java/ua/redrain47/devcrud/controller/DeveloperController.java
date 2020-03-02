package ua.redrain47.devcrud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.Developer;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.service.AccountService;
import ua.redrain47.devcrud.service.DeveloperService;
import ua.redrain47.devcrud.service.SkillService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/v1/developers")
@Slf4j
public class DeveloperController {
    private DeveloperService developerService;
    private SkillService skillService;
    private AccountService accountService;

    @Autowired
    public DeveloperController(DeveloperService developerService, SkillService skillService, AccountService accountService) {
        this.developerService = developerService;
        this.skillService = skillService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Developer developer) {
        log.debug("Request to create (POST)");

        if (isValidDeveloper(developer)) {
            developerService.addData(developer);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getById(@PathVariable Long id) {
        Developer developer = developerService.getDataById(id);

        return (developer != null)
                ? ResponseEntity.ok(developer)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Developer>> getAll() {
        List<Developer> developers = developerService.getAllData();

        return (developers != null && developers.size() > 0)
                ? ResponseEntity.ok(developers)
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Developer developer) {
        if (isValidDeveloper(developer)) {
            if (developerService.getDataById(developer.getId()) == null) {
                return ResponseEntity.notFound().build();
            }

            developerService.updateDataById(developer);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (developerService.getDataById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        developerService.deleteDataById(id);

        return ResponseEntity.ok().build();
    }

    private boolean isValidDeveloper(Developer developer) throws DbStorageException {
        return developer != null && developer.getFirstName() != null
                && developer.getLastName() != null
                && isValidSkillSet(developer.getSkillSet())
                && isValidAccountId(developer.getAccount());
    }

    private boolean isValidAccountId(Account account) throws DbStorageException {
        return account == null
                || accountService.getDataById(account.getId()) != null;
    }

    private boolean isValidSkillSet(Set<Skill> skillSet)
            throws DbStorageException {
        if (skillSet == null) {
            return false;
        }

        Set<Long> originalIds = new HashSet<>();

        for (Skill skill : skillSet) {
            if (!originalIds.add(skill.getId())
                    || skillService.getDataById(skill.getId()) == null) {
                return false;
            }
        }

        return true;
    }
}
