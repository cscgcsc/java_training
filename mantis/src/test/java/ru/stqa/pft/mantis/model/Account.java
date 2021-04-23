package ru.stqa.pft.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mantis_user_table")
public class Account {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public Account() {
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
