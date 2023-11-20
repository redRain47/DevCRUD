package ua.redrain47.devcrud.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ua.redrain47.devcrud.BaseSpringIT;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.AccountStatus;
import ua.redrain47.devcrud.model.Developer;
import ua.redrain47.devcrud.model.Skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeveloperControllerIT extends BaseSpringIT {
    private static final String URL = "/api/v1/developers";
    private final Developer testDeveloper;
    private final List<Developer> testDeveloperList;
    private final Account testAccount;
    private final Skill testSkill;

    public DeveloperControllerIT() {
        testAccount = new Account(1L, "asd@asd.com", AccountStatus.ACTIVE);
        testSkill = new Skill(1L, "Java");
        testDeveloper = new Developer(
                4L,
                "Ricardo",
                "Milos",
                Collections.singleton(testSkill),
                testAccount
        );

        testDeveloperList = new ArrayList<>();
        testDeveloperList.add(testDeveloper);
        testDeveloperList.add(new Developer(
                        2L,
                        "asd",
                        "qwe",
                        Collections.singleton(new Skill(1L, "Java")),
                        new Account(2L, "qwe@qwe.com", AccountStatus.BANNED)
                )
        );
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldSave() throws Exception {
        when(accountRepository.getById(1L)).thenReturn(testAccount);
        when(skillRepository.getById(1L)).thenReturn(testSkill);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testDeveloper)))
                .andExpect(status().isCreated());

        verify(developerRepository, times(1)).save(testDeveloper);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldNotSaveByBadRequest() throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson("asdasd")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    public void shouldGetById() throws Exception {
        when(developerRepository.getById(4L)).thenReturn(testDeveloper);

        mockMvc.perform(get(URL + "/4")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testDeveloper.getId()
                        .intValue())))
                .andExpect(jsonPath("$.firstName", is(testDeveloper.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testDeveloper.getLastName())))
                .andExpect(jsonPath("$.skillSet[0].id", is(testDeveloper.getSkillSet()
                        .iterator().next().getId().intValue())))
                .andExpect(jsonPath("$.skillSet[0].name", is(testDeveloper.getSkillSet()
                        .iterator().next().getName())))
                .andExpect(jsonPath("$.account.id", is(testDeveloper.getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$.account.email", is(testDeveloper.getAccount()
                        .getEmail())))
                .andExpect(jsonPath("$.account.accountStatus", is(testDeveloper.getAccount()
                        .getAccountStatus().toString())))
        ;
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    public void shouldNotGetByNonExistedId() throws Exception {
        mockMvc.perform(get(URL + "/10")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    public void shouldGetAll() throws Exception {
        when(developerRepository.getAll()).thenReturn(testDeveloperList);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(testDeveloperList.get(0)
                        .getId()
                        .intValue())))
                .andExpect(jsonPath("$[0].firstName", is(testDeveloperList.get(0)
                        .getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(testDeveloperList.get(0)
                        .getLastName())))
                .andExpect(jsonPath("$[0].skillSet[0].id", is(testDeveloperList.get(0)
                        .getSkillSet()
                        .iterator().next().getId().intValue())))
                .andExpect(jsonPath("$[0].skillSet[0].name", is(testDeveloperList.get(0)
                        .getSkillSet()
                        .iterator().next().getName())))
                .andExpect(jsonPath("$[0].account.id", is(testDeveloperList.get(0)
                        .getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[0].account.email", is(testDeveloperList.get(0)
                        .getAccount()
                        .getEmail())))
                .andExpect(jsonPath("$[0].account.accountStatus", is(testDeveloperList.get(0)
                        .getAccount()
                        .getAccountStatus().toString())))

                .andExpect(jsonPath("$[1].id", is(testDeveloperList.get(1)
                        .getId()
                        .intValue())))
                .andExpect(jsonPath("$[1].firstName", is(testDeveloperList.get(1)
                        .getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(testDeveloperList.get(1)
                        .getLastName())))
                .andExpect(jsonPath("$[1].skillSet[0].id", is(testDeveloperList.get(1)
                        .getSkillSet()
                        .iterator().next().getId().intValue())))
                .andExpect(jsonPath("$[1].skillSet[0].name", is(testDeveloperList.get(1)
                        .getSkillSet()
                        .iterator().next().getName())))
                .andExpect(jsonPath("$[1].account.id", is(testDeveloperList.get(1)
                        .getAccount()
                        .getId().intValue())))
                .andExpect(jsonPath("$[1].account.email", is(testDeveloperList.get(1)
                        .getAccount()
                        .getEmail())))
                .andExpect(jsonPath("$[1].account.accountStatus", is(testDeveloperList.get(1)
                        .getAccount()
                        .getAccountStatus().toString())));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "USER"})
    public void shouldNotGetAnything() throws Exception {
        when(developerRepository.getAll()).thenReturn(null);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdate() throws Exception {
        when(accountRepository.getById(1L)).thenReturn(testAccount);
        when(skillRepository.getById(1L)).thenReturn(testSkill);
        when(developerRepository.getById(4L)).thenReturn(testDeveloper);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testDeveloper)))
                .andExpect(status().isOk());

        verify(developerRepository, times(1)).update(testDeveloper);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldNotUpdateByNonExistedId() throws Exception {
        when(developerRepository.getById(4L)).thenReturn(null);
        when(accountRepository.getById(1L)).thenReturn(testAccount);
        when(skillRepository.getById(1L)).thenReturn(testSkill);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testDeveloper)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldNotUpdateByBadRequest() throws Exception {
        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson("asdasda")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldDelete() throws Exception {
        when(developerRepository.getById(4L)).thenReturn(testDeveloper);

        mockMvc.perform(delete(URL + "/4"))
                .andExpect(status().isOk());

        verify(developerRepository, times(1)).deleteById(testDeveloper.getId());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldDeleteNonExistedId() throws Exception {
        mockMvc.perform(delete(URL + "/10"))
                .andExpect(status().isNotFound());
    }
}
