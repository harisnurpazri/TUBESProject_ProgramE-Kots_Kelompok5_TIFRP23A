package model;

import javafx.beans.property.*;

public class Admin extends User {
    // Properti untuk menyimpan alamat kost
    private final StringProperty kostAddress;
    
    // Properti untuk menyimpan nama akun
    private final StringProperty accountName;
    
    // Properti untuk menyimpan nomor akun
    private final StringProperty accountNumber;

    // Konstruktor untuk menginisialisasi objek Admin
    public Admin(int idAdmin, String kostAddress, String accountName, String accountNumber) {
        super(idAdmin);
        this.kostAddress = new SimpleStringProperty(kostAddress);
        this.accountName = new SimpleStringProperty(accountName);
        this.accountNumber = new SimpleStringProperty(accountNumber);
    }

    // Metode getter untuk alamat kost
    public String getkostAddress() {
        return kostAddress.get();
    }

    // Metode untuk mendapatkan properti alamat kost
    public StringProperty kostAddressProperty() {
        return kostAddress;
    }

    // Metode getter untuk nama akun
    public String getaccountName() {
        return accountName.get();
    }

    // Metode untuk mendapatkan properti nama akun
    public StringProperty accountNameProperty() {
        return accountName;
    }

    // Metode getter untuk nomor akun
    public String getaccountNumber() {
        return accountNumber.get();
    }

    // Metode untuk mendapatkan properti nomor akun
    public StringProperty accountNumberProperty() {
        return accountNumber;
    }

    // Metode setter untuk alamat kost
    public void setkostAddress(String kostAddress) {
        this.kostAddress.set(kostAddress);
    }

    // Metode setter untuk nama akun
    public void setaccountName(String accountName) {
        this.accountName.set(accountName);
    }

    // Metode setter untuk nomor akun
    public void setaccountNumber(String accountNumber) {
        this.accountNumber.set(accountNumber);
    }

    public int getIdAdmin() {
        return getIdUser();
    }

    public IntegerProperty idAdminProperty() {
        return idUserProperty();
    }
}
