package ua.redrain47.devcrud.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ua.redrain47.devcrud.BaseSpringIT;
import ua.redrain47.devcrud.model.Skill;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SkillControllerIT extends BaseSpringIT {
    private static final String URL = "/api/v1/skills";
    private final Skill testSkill = new Skill(4L, "Java");

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldSave() throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testSkill)))
                .andExpect(status().isCreated());

        verify(skillRepository, times(1)).save(testSkill);
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
        when(skillRepository.getById(4L)).thenReturn(testSkill);

        mockMvc.perform(get(URL + "/4")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testSkill.getId()
                        .intValue())))
                .andExpect(jsonPath("$.name", is(testSkill.getName())));
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
        List<Skill> testSkillList = new ArrayList<>();

        testSkillList.add(new Skill(1L, "C++"));
        testSkillList.add(new Skill(2L, "JS"));

        when(skillRepository.getAll()).thenReturn(testSkillList);

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
    @WithMockUser(roles = {"ADMIN", "USER"})
    public void shouldNotGetAnything() throws Exception {
        when(skillRepository.getAll()).thenReturn(null);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldUpdate() throws Exception {
        when(skillRepository.getById(4L)).thenReturn(testSkill);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testSkill)))
                .andExpect(status().isOk());

        verify(skillRepository, times(1)).update(testSkill);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldNotUpdateByNonExistedId() throws Exception {
        when(skillRepository.getById(4L)).thenReturn(null);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testSkill)))
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
        when(skillRepository.getById(4L)).thenReturn(testSkill);

        mockMvc.perform(delete(URL + "/4"))
                .andExpect(status().isOk());

        verify(skillRepository, times(1)).deleteById(testSkill.getId());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldDeleteNonExistedId() throws Exception {
        mockMvc.perform(delete(URL + "/10"))
                .andExpect(status().isNotFound());
    }
}
