package ua.redrain47.devcrud.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.AccountStatus;
import ua.redrain47.devcrud.model.Developer;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.service.AccountService;
import ua.redrain47.devcrud.service.DeveloperService;
import ua.redrain47.devcrud.service.SkillService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeveloperControllerTest {
    private static final String URL = "/api/v1/developers";
    private final DeveloperService developerService = mock(DeveloperService.class);
    private final AccountService accountService = mock(AccountService.class);
    private final SkillService skillService = mock(SkillService.class);
    private final DeveloperController sut = new DeveloperController(developerService,
            skillService, accountService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    private final Gson gson = new Gson();
    private final Developer testDeveloper;
    private final List<Developer> testDeveloperList;
    private final Account testAccount;
    private final Skill testSkill;

    public DeveloperControllerTest() {
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
    public void shouldSave() throws Exception {
        when(accountService.getDataById(1L)).thenReturn(testAccount);
        when(skillService.getDataById(1L)).thenReturn(testSkill);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testDeveloper)))
                .andExpect(status().isCreated());

        verify(developerService, times(1)).addData(testDeveloper);
    }

    @Test
    public void shouldNotSaveByBadRequest() throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson("asdasd")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetById() throws Exception {
        when(developerService.getDataById(4L)).thenReturn(testDeveloper);

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
    public void shouldNotGetByNonExistedId() throws Exception {
        mockMvc.perform(get(URL + "/10")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAll() throws Exception {
        when(developerService.getAllData()).thenReturn(testDeveloperList);

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
    public void shouldNotGetAnything() throws Exception {
        when(developerService.getAllData()).thenReturn(null);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdate() throws Exception {
        when(accountService.getDataById(1L)).thenReturn(testAccount);
        when(skillService.getDataById(1L)).thenReturn(testSkill);
        when(developerService.getDataById(4L)).thenReturn(testDeveloper);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testDeveloper)))
                .andExpect(status().isOk());

        verify(developerService, times(1)).updateDataById(testDeveloper);
    }

    @Test
    public void shouldNotUpdateByNonExistedId() throws Exception {
        when(accountService.getDataById(1L)).thenReturn(testAccount);
        when(skillService.getDataById(1L)).thenReturn(testSkill);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testDeveloper)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotUpdateByBadRequest() throws Exception {
        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson("asdasda")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDelete() throws Exception {
        when(developerService.getDataById(4L)).thenReturn(testDeveloper);

        mockMvc.perform(delete(URL + "/4"))
                .andExpect(status().isOk());

        verify(developerService, times(1)).deleteDataById(testDeveloper.getId());
    }

    @Test
    public void shouldDeleteNonExistedId() throws Exception {
        mockMvc.perform(delete(URL + "/10"))
                .andExpect(status().isNotFound());
    }
}