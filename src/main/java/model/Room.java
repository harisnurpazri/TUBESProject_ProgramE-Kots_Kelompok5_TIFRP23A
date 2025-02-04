package model;

import javafx.beans.property.*;

public class Room {
    private IntegerProperty idRoom;
    private StringProperty facilities;
    private StringProperty size;
    private DoubleProperty roomPrice;
    private StringProperty status;

    public Room(int idRoom, String facilities, String size, double roomPrice, String status) {
        this.idRoom = new SimpleIntegerProperty(idRoom);
        this.facilities = new SimpleStringProperty(facilities);
        this.size = new SimpleStringProperty(size);
        this.roomPrice = new SimpleDoubleProperty(roomPrice);
        this.status = new SimpleStringProperty(status);
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

    public String getFacilities() {
        return facilities.get();
    }

    public StringProperty facilitiesProperty() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities.set(facilities);
    }

    public String getSize() {
        return size.get();
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
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

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
