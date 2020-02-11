package ua.redrain47.devcrud.util;

import ua.redrain47.devcrud.model.Account;
import ua.redrain47.devcrud.model.AccountStatus;
import ua.redrain47.devcrud.model.Developer;
import ua.redrain47.devcrud.model.Skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObjectMapper {
    public static List<Skill> mapToSkillList(ResultSet resultSet)
            throws SQLException {
        ArrayList<Skill> skillList = null;

        if (resultSet != null) {
            skillList = new ArrayList<>();

            while (resultSet.next()) {
                long skillId = resultSet.getInt("id");
                String skillName = resultSet.getString("name");

                skillList.add(new Skill(skillId, skillName));
            }
        }

        return skillList;
    }

    public static Set<Skill> mapToSkillSet(ResultSet resultSet)
            throws SQLException {
        HashSet<Skill> skillSet = null;

        if (resultSet != null) {
            skillSet = new HashSet<>();

            while (resultSet.next()) {
                long skillId = resultSet.getInt("id");
                String skillName = resultSet.getString("name");

                skillSet.add(new Skill(skillId, skillName));
            }
        }

        return skillSet;
    }

    public static List<Account> mapToAccountList(ResultSet resultSet)
            throws SQLException {
        ArrayList<Account> accountList = null;

        if (resultSet != null) {
            accountList = new ArrayList<>();

            while (resultSet.next()) {
                long accountId = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String accountStatus = resultSet.getString("account_status");

                accountList.add(new Account(accountId, email,
                        AccountStatus.valueOf(accountStatus)));
            }
        }

        return accountList;
    }

    public static List<Developer> mapToDeveloperList(ResultSet resultSet)
            throws SQLException {
        ArrayList<Developer> developerList = null;

        if (resultSet != null) {
            developerList = new ArrayList<>();

            while (resultSet.next()) {
                Long developerId = (long) resultSet.getInt("id");
                Long accountId = (long) resultSet.getInt("account_id");
                Account account = (accountId != 0)
                        ? new Account(accountId, null, null)
                        : null;
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                developerList.add(new Developer(developerId, firstName, lastName,
                        null, account));

            }
        }

        return developerList;
    }
}
