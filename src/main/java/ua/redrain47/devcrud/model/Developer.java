package ua.redrain47.devcrud.model;

import java.util.Objects;
import java.util.Set;

public class Developer {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Skill> skillSet;
    private Account account;

    public Developer(Long id, String firstName, String lastName, Set<Skill> skillSet, Account account) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.skillSet = skillSet;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Skill> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(Set<Skill> skillSet) {
        this.skillSet = skillSet;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toSerializableString() {
        StringBuilder skillIdsStr = new StringBuilder();
        Skill[] skillArray = new Skill[skillSet.size()];
        skillSet.toArray(skillArray);

        for (int i = 0; i < skillArray.length - 1; i++) {
            skillIdsStr.append(skillArray[i].getId()).append(",");
        }
        skillIdsStr.append(skillArray[skillArray.length - 1].getId());

        if (account != null) {
            return id + " " + firstName + " " + lastName +
                    " [" + skillIdsStr + "] " + account.getId();
        }

        return id + " " + firstName + " " + lastName +
                " [" + skillIdsStr + "]";
    }

    @Override
    public String toString() {
        StringBuilder skillSetStr = new StringBuilder();

        Skill[] skillArray = new Skill[skillSet.size()];
        skillSet.toArray(skillArray);

        for (int i = 0; i < skillArray.length - 1; i++) {
            skillSetStr.append(skillArray[i].getName()).append(",");
        }
        skillSetStr.append(skillArray[skillArray.length - 1].getName());

        if (account != null) {
            return id + " | " + firstName + " | " + lastName + " | "
                    + " [" + skillSetStr + "] | " + account.getId();
        }

        return id + " | " + firstName + " | " + lastName + " | "
                + "[" + skillSetStr + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(id, developer.id) &&
                Objects.equals(firstName, developer.firstName) &&
                Objects.equals(lastName, developer.lastName) &&
                Objects.equals(skillSet, developer.skillSet) &&
                Objects.equals(account, developer.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, skillSet, account);
    }
}
