package ua.redrain47.devcrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.redrain47.devcrud.exceptions.DbStorageException;
import ua.redrain47.devcrud.repository.AccountRepository;
import ua.redrain47.devcrud.repository.DeveloperRepository;
import ua.redrain47.devcrud.repository.SkillRepository;
import ua.redrain47.devcrud.repository.jdbc.JdbcAccountRepositoryImpl;
import ua.redrain47.devcrud.repository.jdbc.JdbcDeveloperRepositoryImpl;
import ua.redrain47.devcrud.repository.jdbc.JdbcSkillRepositoryImpl;

@Configuration
@ComponentScan
public class RepositoryConfig {
    @Bean(name = "skillRepository")
    public SkillRepository skillRepository() throws DbStorageException {
        return new JdbcSkillRepositoryImpl();
    }
    @Bean(name = "accountRepository")
    public AccountRepository accountRepository() throws DbStorageException {
        return new JdbcAccountRepositoryImpl();
    }
    @Bean(name = "developerRepository")
    public DeveloperRepository developerRepository() throws DbStorageException {
        return new JdbcDeveloperRepositoryImpl();
    }
}
