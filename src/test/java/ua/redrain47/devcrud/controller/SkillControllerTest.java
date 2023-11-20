package ua.redrain47.devcrud.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.redrain47.devcrud.model.Skill;
import ua.redrain47.devcrud.service.SkillService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SkillControllerTest {
    private static final String URL = "/api/v1/skills";
    private final SkillService skillService = mock(SkillService.class);
    private final SkillController sut = new SkillController(skillService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    private final Gson gson = new Gson();
    private final Skill testSkill = new Skill(4L, "Java");

    @Test
    public void shouldSave() throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testSkill)))
                .andExpect(status().isCreated());

        verify(skillService, times(1)).addData(testSkill);
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
        when(skillService.getDataById(4L)).thenReturn(testSkill);

        mockMvc.perform(get(URL + "/4")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testSkill.getId()
                        .intValue())))
                .andExpect(jsonPath("$.name", is(testSkill.getName())));
    }

    @Test
    public void shouldNotGetByNonExistedId() throws Exception {
        mockMvc.perform(get(URL + "/10")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAll() throws Exception {
        List<Skill> testSkillList = new ArrayList<>();

        testSkillList.add(new Skill(1L, "C++"));
        testSkillList.add(new Skill(2L, "JS"));

        when(skillService.getAllData()).thenReturn(testSkillList);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(testSkillList.get(0)
                        .getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(testSkillList.get(0)
                        .getName())))
                .andExpect(jsonPath("$[1].id", is(testSkillList.get(1)
                        .getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(testSkillList.get(1)
                        .getName())));
    }

    @Test
    public void shouldNotGetAnything() throws Exception {
        when(skillService.getAllData()).thenReturn(null);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdate() throws Exception {
        when(skillService.getDataById(4L)).thenReturn(testSkill);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testSkill)))
                .andExpect(status().isOk());

        verify(skillService, times(1)).updateDataById(testSkill);
    }

    @Test
    public void shouldNotUpdateByNonExistedId() throws Exception {
        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testSkill)))
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
        when(skillService.getDataById(4L)).thenReturn(testSkill);

        mockMvc.perform(delete(URL + "/4"))
                .andExpect(status().isOk());

        verify(skillService, times(1)).deleteDataById(testSkill.getId());
    }

    @Test
    public void shouldDeleteNonExistedId() throws Exception {
        mockMvc.perform(delete(URL + "/10"))
                .andExpect(status().isNotFound());
    }
}