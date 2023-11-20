package ua.redrain47.devcrud.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.AccountStatus;
import ua.redrain47.devcrud.service.AccountService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {
    private static final String URL = "/api/v1/accounts";
    private final AccountService accountService = mock(AccountService.class);
    private final AccountController sut = new AccountController(accountService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    private final Gson gson = new Gson();
    private final Account testAccount = new Account(4L, "qwert123@mail.com",
            AccountStatus.ACTIVE);

    @Test
    public void shouldSave() throws Exception {
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testAccount)))
                .andExpect(status().isCreated());

        verify(accountService, times(1)).addData(testAccount);
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
        when(accountService.getDataById(4L)).thenReturn(testAccount);

        mockMvc.perform(get(URL + "/4")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testAccount.getId()
                        .intValue())))
                .andExpect(jsonPath("$.email", is(testAccount.getEmail())))
                .andExpect(jsonPath("$.accountStatus", is(testAccount
                        .getAccountStatus().toString())));
    }

    @Test
    public void shouldNotGetByNonExistedId() throws Exception {
        mockMvc.perform(get(URL + "/10")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAll() throws Exception {
        List<Account> testAccountList = new ArrayList<>();

        testAccountList.add(new Account(1L, "asd@sda.com",
                AccountStatus.ACTIVE));
        testAccountList.add(new Account(2L, "qqqeqe@gmail.com",
                AccountStatus.BANNED));

        when(accountService.getAllData()).thenReturn(testAccountList);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(testAccountList.get(0)
                        .getId().intValue())))
                .andExpect(jsonPath("$[0].email", is(testAccountList.get(0)
                        .getEmail())))
                .andExpect(jsonPath("$[0].accountStatus", is(testAccountList.get(0)
                        .getAccountStatus().toString())))
                .andExpect(jsonPath("$[1].id", is(testAccountList.get(1)
                        .getId().intValue())))
                .andExpect(jsonPath("$[1].email", is(testAccountList.get(1)
                        .getEmail())))
                .andExpect(jsonPath("$[1].accountStatus", is(testAccountList.get(1)
                        .getAccountStatus().toString())));
    }

    @Test
    public void shouldNotGetAnything() throws Exception {
        when(accountService.getAllData()).thenReturn(null);

        mockMvc.perform(get(URL)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdate() throws Exception {
        when(accountService.getDataById(4L)).thenReturn(testAccount);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testAccount)))
                .andExpect(status().isOk());

        verify(accountService, times(1)).updateDataById(testAccount);
    }

    @Test
    public void shouldNotUpdateByNonExistedId() throws Exception {
        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(testAccount)))
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
        when(accountService.getDataById(4L)).thenReturn(testAccount);

        mockMvc.perform(delete(URL + "/4"))
                .andExpect(status().isOk());

        verify(accountService, times(1)).deleteDataById(testAccount.getId());
    }

    @Test
    public void shouldDeleteNonExistedId() throws Exception {
        mockMvc.perform(delete(URL + "/10"))
                .andExpect(status().isNotFound());
    }
}