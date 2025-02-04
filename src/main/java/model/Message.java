package model;

import javafx.beans.property.*;

public class Message {
    private IntegerProperty idMessage;
    private IntegerProperty idTenant;
    private StringProperty name;
    private StringProperty message;

    public Message(int idMessage, int idTenant, String name, String message) {
        this.idMessage = new SimpleIntegerProperty(idMessage);
        this.idTenant = new SimpleIntegerProperty(idTenant);
        this.name = new SimpleStringProperty(name);
        this.message = new SimpleStringProperty(message);
    }

    public int getIdMessage() {
        return idMessage.get();
    }

    public IntegerProperty idMessageProperty() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage.set(idMessage);
    }

    public int getIdTenant() {
        return idTenant.get();
    }

    public IntegerProperty idTenantProperty() {
        return idTenant;
    }

    public void setIdTenant(int idTenant) {
        this.idTenant.set(idTenant);
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

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}
