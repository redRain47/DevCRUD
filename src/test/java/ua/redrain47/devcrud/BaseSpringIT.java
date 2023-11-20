package ua.redrain47.devcrud;

import com.google.gson.Gson;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.redrain47.devcrud.repository.AccountRepository;
import ua.redrain47.devcrud.repository.DeveloperRepository;
import ua.redrain47.devcrud.repository.SkillRepository;

import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
public abstract class BaseSpringIT {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected Gson gson;

    @Autowired
    protected SkillRepository skillRepository;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected DeveloperRepository developerRepository;

    @Configuration
    @EnableWebMvc
    @ComponentScan("ua.redrain47")
    protected static class TextContextConfiguration {

//        @Bean
//        public SkillService skillService() {
//            return new SkillService(mock(SkillRepository.class));
//        }
//
//        @Bean
//        public AccountService accountService() {
//            return new AccountService(mock(AccountRepository.class));
//        }
//
//        @Bean
//        public DeveloperService developerService() {
//            return new DeveloperService(mock(DeveloperRepository.class));
//        }

        @Bean
        public SkillRepository skillRepository() {
            return mock(SkillRepository.class);
        }

        @Bean
        public AccountRepository accountRepository() {
            return mock(AccountRepository.class);
        }

        @Bean
        public DeveloperRepository developerRepository() {
            return mock(DeveloperRepository.class);
        }

        @Bean
        @Autowired
        public MockMvc mockMvc(WebApplicationContext webCtx) {
            return MockMvcBuilders
                    .webAppContextSetup(webCtx)
                    .apply(springSecurity())
                    .build();
        }

        @Bean
        public Gson gson() {
            return new Gson();
        }
    }
}
