package ua.redrain47.devcrud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("api/v1/skills")
@Slf4j
public class SkillController {
    private SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody Skill skill) {
        log.debug("Request to create (POST)");

        if (isValidSkill(skill)) {
            skillService.addData(skill);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Skill> getById(@PathVariable Long id) {
        Skill skill = skillService.getDataById(id);

        return (skill != null)
                ? ResponseEntity.ok(skill)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Skill>> getAll() {
        List<Skill> skills = skillService.getAllData();

        return (skills != null && skills.size() > 0)
                ? ResponseEntity.ok(skills)
                : ResponseEntity.notFound().build();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> update(@RequestBody Skill skill) {
        if (isValidSkill(skill)) {
            if (skillService.getDataById(skill.getId()) == null) {
                return ResponseEntity.notFound().build();
            }

            skillService.updateDataById(skill);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (skillService.getDataById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        skillService.deleteDataById(id);

        return ResponseEntity.ok().build();
    }

    private boolean isValidSkill(Skill skill) {
        return skill != null && skill.getName() != null;
    }
}
