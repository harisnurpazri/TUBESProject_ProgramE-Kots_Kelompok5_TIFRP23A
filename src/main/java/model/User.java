package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class User {
    // Properti untuk menyimpan ID user
    private final IntegerProperty idUser;

    // Konstruktor untuk menginisialisasi objek User
    public User(int idUser) {
        this.idUser = new SimpleIntegerProperty(idUser);
    }

    // Metode getter untuk ID user
    public int getIdUser() {
        return idUser.get();
    }

    // Metode untuk mendapatkan properti ID user
    public IntegerProperty idUserProperty() {
        return idUser;
    }
}
