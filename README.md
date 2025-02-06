# Final Proyek Pemrograman Berorientasi Obyek 1
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 1</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Kelompok
<ul>
  <li>Kelompok: {Kelompok 5}</li>
  <li>Proyek: {Sistem Pengelolaan Kost}</li>
  <li>Anggota:</li>
  <ul>
    <li>Ketua: <a href="https://github.com/harisnurpazri">Haris Nurpazri</a></li>
    <li>Anggota 1: <a href="https://github.com/Pedrikdendi">Pedrik Dendi Aparel</a></li>
    <li>Anggota 2: <a href="https://github.com/NisaSilvaTriana">Nisa Silva Triana</a></li>
  </ul>
</ul>

## Judul Studi Kasus
<p>Elektronik Kost.</p>

## Penjelasan Studi Kasus
<p>Sistem pengelolaan Kost berbasis Elektronik. Sistem ini dirancang untuk mempermudah pengelolaan kost dengan pengembangan program bahasa Java GUI sebagai antarmuka pengguna dan menggunakan database MySQL.
.</p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p>Inheritance memungkinkan suatu kelas untuk mewarisi atribut dan metode dari kelas lain. Dalam kode ini, kelas Admin dan Tenant mewarisi sifat dari kelas User, sehingga tidak perlu menulis ulang atribut dan metode yang sama.

Implementasi Inheritance dalam Kode:
1.	Kelas User sebagai Parent Class:
   ### User.java

```java
public class User {
    private final IntegerProperty idUser;
    public User(int idUser) {
        this.idUser = new SimpleIntegerProperty(idUser);
    }
    public int getIdUser() {
        return idUser.get();
    }
    public IntegerProperty idUserProperty() {
        return idUser;
    }
}

```
2.	Kelas Admin sebagai Child Class:
   ### Admin.java

```java
public class Admin extends User {
    private final StringProperty kostAddress;
    private final StringProperty accountName;
    private final StringProperty accountNumber;

    public Admin(int idAdmin, String kostAddress, String accountName, String accountNumber) {
        super(idAdmin);
        this.kostAddress = new SimpleStringProperty(kostAddress);
        this.accountName = new SimpleStringProperty(accountName);
        this.accountNumber = new SimpleStringProperty(accountNumber);
    }
}

```
3.	Kelas Tenant sebagai Child Class:
   ### Tenant.java

```java
public class Tenant extends User {
    private StringProperty name;
    private StringProperty phoneNumber;
    private StringProperty address;
    private IntegerProperty idRoom;
    private DoubleProperty roomPrice;
    private ObjectProperty<Date> checkInDate;
    private ObjectProperty<Date> payDate;

    public Tenant(int idTenant, String name, String phoneNumber, String address, 
                 int idRoom, double roomPrice, Date checkInDate, Date payDate) {
        super(idTenant);
    }
}

```
Alasan Penggunaan Inheritance:
<li> Penggunaan inheritance dalam kode ini sangat masuk akal karena Admin dan Tenant pada dasarnya adalah tipe User dengan tambahan karakteristik masing-masing</li>
<li>Kode jadi lebih efisien karena tidak perlu menulis ulang properti dan method yang sama di setiap kelas</li> 
<li>Struktur data jadi lebih rapi dan mudah dipahami - semua yang berhubungan dengan identitas user dikelompokkan dalam satu kelas induk</li>
<li>Memudahkan maintenance - jika ada perubahan pada properti atau method user, cukup ubah di kelas User</li>
.</p>

### 2. Encapsulation
<p>Encapsulation memastikan bahwa data dalam suatu objek tidak bisa diakses langsung dari luar kelasnya. Ini dilakukan dengan menjadikan atribut private dan menyediakan method getter dan setter untuk mengakses atau mengubah nilainya.
  
Implementasi Encapsulation dalam Kode: 
1.	Dalam kelas Room:
### Room.java

```java
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

```
2.	Dalam kelas Message:
### Massage.java

```java
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

```
3. Dalam kelas Admin:
   ### Admin.java

```java
    public Admin(int idAdmin, String kostAddress, String accountName, String accountNumber) {
        super(idAdmin); // Memanggil konstruktor parent class
        this.kostAddress = new SimpleStringProperty(kostAddress);
        this.accountName = new SimpleStringProperty(accountName);
        this.accountNumber = new SimpleStringProperty(accountNumber);
    }

    // Getter untuk alamat kost
    public String getKostAddress() {
        return kostAddress.get();
    }

    // Properti alamat kost
    public StringProperty kostAddressProperty() {
        return kostAddress;
    }

    // Setter untuk alamat kost
    public void setKostAddress(String kostAddress) {
        this.kostAddress.set(kostAddress);
    }

    // Getter untuk nama akun
    public String getAccountName() {
        return accountName.get();
    }

    // Properti nama akun
    public StringProperty accountNameProperty() {
        return accountName;
    }

    // Setter untuk nama akun
    public void setAccountName(String accountName) {
        this.accountName.set(accountName);
    }

    // Getter untuk nomor akun
    public String getAccountNumber() {
        return accountNumber.get();
    }

    // Properti nomor akun
    public StringProperty accountNumberProperty() {
        return accountNumber;
    }

    // Setter untuk nomor akun
    public void setAccountNumber(String accountNumber) {
        this.accountNumber.set(accountNumber);
    }

    // Overriding metode dari parent class
    public int getIdAdmin() {
        return getIdUser();
    }

    public IntegerProperty idAdminProperty() {
        return idUserProperty();
    }
}

```
4. Dalam Kelas Tagihan:
### Bill.java

```java
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

```
5. Dalam Kelas Login:
### Login.java

```java
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

```
6. Dalam Kelas User:
   ### User.java

```java
   public class User {
    
    private final IntegerProperty idUser;

    public User(int idUser) {
        this.idUser = new SimpleIntegerProperty(idUser);
    }

    public int getIdUser() {
        return idUser.get();
    }

    public IntegerProperty idUserProperty() {
        return idUser;
    }
}

```
Alasan Penggunaan Encapsulation:
<li>Keamanan data terjamin karena akses langsung ke properti dibatasi</li>
<li>Memudahkan validasi data - bisa menambahkan logika validasi dalam setter method</li>
<li>Perubahan internal kelas tidak mempengaruhi kode di luar kelas</li>
<li>Kode lebih fleksibel untuk dimodifikasi karena implementasi detail tersembunyi</li>
.</p>

### 3. Polymorphism
<p>Polymorphism memungkinkan method dengan nama yang sama memiliki perilaku berbeda tergantung objek yang memanggilnya. Dalam kode ini, Admin dan Tenant mengoverride method getIdUser()
  
Implementasi Polymorphism dalam Kode:
1.	Method Overriding di kelas Admin:
   ### Admin.java

```java
public class Admin extends User {
    public int getIdAdmin() {
        return getIdUser(); 
    }

    public IntegerProperty idAdminProperty() {
        return idUserProperty();
    }
}

```
2.	Method Overriding di kelas Tenant:
   ### Tenant.java

```java
public class Tenant extends User {
    public int getIdTenant() {
        return getIdUser(); 
    }

    public IntegerProperty idTenantProperty() {
        return idUserProperty();
    }
}

```
Alasan Penggunaan Polymorphism:
<li>Meningkatkan kejelasan kode karena method yang sama bisa bekerja dengan cara berbeda</li>
<li>Method bisa disesuaikan dengan kebutuhan spesifik masing-masing kelas</li>
<li>Kode jadi lebih fleksibel dan mudah dikembangkan</li>
<li>Memudahkan maintenance karena perubahan perilaku cukup dilakukan di kelas yang relevan</li>

.</p>

### 4. Abstract
<p>Abstraction menyembunyikan detail implementasi dan hanya menyediakan fungsionalitas yang diperlukan. Dalam kode ini, kelas RoomOperations dan LoginOperations menyembunyikan detail akses database dari pengguna.
  
Implementasi Abstraction dalam Kode:
1.	Dalam kelas RoomOperations:
   ### RoomOperations.java

```java
public class RoomOperations {
    private Connection connection;

    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room_info";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {

            }
        }
        return rooms;
    }

    public void addRoom(Room room) throws SQLException {
        String query = "INSERT INTO room_info (Facilities, Size, Room_Price, Status) VALUES (?, ?, ?, ?)";

    }
}

```
2.	Dalam kelas LoginOperations:
   ### LoginOperations.java

```java
public class LoginOperations {
    private Connection connection;

    public boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM login WHERE Username = ? AND Password = ?";

    }

    public String getRole(String username) throws SQLException {
        String query = "SELECT Role FROM login WHERE Username = ?";

    }
}

```
3. Dalam Kelas AdminOperations:
   ### AdminOperations.java

```java
   public class AdminOperations {
    private Connection connection;

    public AdminOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>(); 
        String query = "SELECT * FROM admin_info"; 
      
    }

```
4. Dalam Kelas BillOperations:
   ### BillOperations.java

```java
   public class BillOperations {
    private Connection connection;
    private TenantOperations tenantOperations;

    public BillOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.tenantOperations = new TenantOperations();
    }

    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM bills";
   
   5. Dalam Kelas MassageOperations:
     ### Massage Operations.java

```java
   public class MessageOperations {
    private Connection connection;

    public MessageOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    public List<Message> getMessagesByTenantId(int tenantId) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE ID_Tenant = ?";
   
```
   6. Dalam Kelas TenantOperations:
      ### TenantOperations.java

```java
   public class TenantOperations {
    private Connection connection;

    public TenantOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Tenant> getAllTenants() throws SQLException {
        String query = "SELECT * FROM tenant_info";
        List<Tenant> tenants = new ArrayList<>();
```
## Alasan Penggunaan Abstraction:
<p>
<li>Menyederhanakan penggunaan sistem - pengguna tidak perlu tahu cara data disimpan atau diproses</li>
<li>Kode lebih mudah dipahami karena kompleksitas tersembunyi di balik method sederhana</li>
<li>Memudahkan perubahan implementasi tanpa mengubah cara penggunaan</li>
Pengelolaan error jadi lebih terstruktur

</p>

## Struktur Tabel Aplikasi

[Desain Tabel Database]![pict11](https://github.com/user-attachments/assets/b74adf9d-bdfc-461f-990e-0482739acaf5)



## Tampilan Aplikasi
[Tampilan Login]![pict1](https://github.com/user-attachments/assets/576c4afd-adf4-4fcf-bb86-8710345edad5)

[Tampilan Admin Dashboard-Fitur Kamar]![pict2](https://github.com/user-attachments/assets/021ec25d-e327-457e-a63e-2daa68709800)

[Tampilan Admin Dashboard-Fitur Penghuni]![pict3](https://github.com/user-attachments/assets/ccc63383-9a83-440a-9bf7-b546a7f3f4d0)

[Tampilan Admin Dashboard-Fitur Tagihan]![pict4](https://github.com/user-attachments/assets/819e52cd-ea4e-4e01-9716-3a72e611f10f)

[Tampilan Admin Dashboard-Fitur Pesan]![pict5](https://github.com/user-attachments/assets/2a2d9805-6678-4f2e-ad80-9e792d9c6f34)

[Tampilan Admin Dashboard-Fitur Informasi Admin]![pict6](https://github.com/user-attachments/assets/f79a10ed-24f5-4732-bc8b-cfaf9c071b86)

[Tampilan Admin Dashboard-Fitur Kelola Akun]![pict7](https://github.com/user-attachments/assets/05d68eae-0532-4057-9c79-d51d6e0de159)

[Tampilan Dashboard Penghuni-Fitur Tagihan]![pict8](https://github.com/user-attachments/assets/16c59214-80ed-48ae-a2cb-8b7a49d0260d)

[Tampilan Dashboard Penghuni-Fitur Pesan]![pict9](https://github.com/user-attachments/assets/82dff2ad-5318-4156-8859-88815b738c18)

[Tampilan Dashboard Penghuni-Fitur Admin]![pict10](https://github.com/user-attachments/assets/5e99a02f-81bb-40cc-b812-f8a513f28670)






## Demo Proyek
<ul>
  <li>Github: <a href="https://github.com/harisnurpazri/TUBESProject_ProgramE-Kots_Kelompok5_TIFRP23A/tree/main">Github</a></li>
  <li>Youtube: <a href="">[Youtube](https://youtu.be/MUAwDbUnADI)</a></li>
</ul>
