package model;

import javafx.beans.property.*;

public class Login {
    private final IntegerProperty idAccount;
    private final StringProperty name;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty role;

    public Login(int idAccount, String name, String username, String password, String role) {
        this.idAccount = new SimpleIntegerProperty(idAccount);
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    public int getIdAccount() {
        return idAccount.get();
    }

    public IntegerProperty idAccountProperty() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount.set(idAccount);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }
}
