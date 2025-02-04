package model;

import javafx.beans.property.*;

import java.sql.Date;

public class Tenant extends User {
    private StringProperty name;
    private StringProperty phoneNumber;
    private StringProperty address;
    private IntegerProperty idRoom;
    private DoubleProperty roomPrice;
    private ObjectProperty<Date> checkInDate;
    private ObjectProperty<Date> payDate;

    public Tenant(int idTenant, String name, String phoneNumber, String address, int idRoom, double roomPrice, Date checkInDate, Date payDate) {
        super(idTenant);
        this.name = new SimpleStringProperty(name);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address = new SimpleStringProperty(address);
        this.idRoom = new SimpleIntegerProperty(idRoom);
        this.roomPrice = new SimpleDoubleProperty(roomPrice);
        this.checkInDate = new SimpleObjectProperty<>(checkInDate);
        this.payDate = new SimpleObjectProperty<>(payDate);
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

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getIdRoom() {
        return idRoom.get();
    }

    public IntegerProperty idRoomProperty() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom.set(idRoom);
    }

    public double getRoomPrice() {
        return roomPrice.get();
    }

    public DoubleProperty roomPriceProperty() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice.set(roomPrice);
    }

    public Date getCheckInDate() {
        return checkInDate.get();
    }

    public ObjectProperty<Date> checkInDateProperty() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate.set(checkInDate);
    }

    public Date getPayDate() {
        return payDate.get();
    }

    public ObjectProperty<Date> payDateProperty() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate.set(payDate);
    }

    public int getIdTenant() {
        return getIdUser();
    }

    public IntegerProperty idTenantProperty() {
        return idUserProperty();
    }
}
