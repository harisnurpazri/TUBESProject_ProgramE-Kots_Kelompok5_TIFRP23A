package model;

import javafx.beans.property.*;

import java.sql.Date;

public class Bill {
    private IntegerProperty idTransaction;
    private IntegerProperty idTenant;
    private StringProperty name;
    private IntegerProperty idRoom;
    private DoubleProperty roomPrice;
    private ObjectProperty<Date> paymentDate;
    private DoubleProperty amountPaid;

    public Bill(int idTransaction, int idTenant, String name, int idRoom, double roomPrice, Date paymentDate, double amountPaid) {
        this.idTransaction = new SimpleIntegerProperty(idTransaction);
        this.idTenant = new SimpleIntegerProperty(idTenant);
        this.name = new SimpleStringProperty(name);
        this.idRoom = new SimpleIntegerProperty(idRoom);
        this.roomPrice = new SimpleDoubleProperty(roomPrice);
        this.paymentDate = new SimpleObjectProperty<>(paymentDate);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
    }

    public int getIdTransaction() {
        return idTransaction.get();
    }

    public IntegerProperty idTransactionProperty() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction.set(idTransaction);
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

    public Date getPaymentDate() {
        return paymentDate.get();
    }

    public ObjectProperty<Date> paymentDateProperty() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate.set(paymentDate);
    }

    public double getAmountPaid() {
        return amountPaid.get();
    }

    public DoubleProperty amountPaidProperty() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }
}
