package Operations;

import com.mycompany.electronic_kostprogram.DatabaseConnection;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomOperations {
    private Connection connection;

    public RoomOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room_info";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idRoom = resultSet.getInt("ID_Room");
                String facilities = resultSet.getString("Facilities");
                String size = resultSet.getString("Size");
                double roomPrice = resultSet.getDouble("Room_Price");
                String status = resultSet.getString("Status");
                rooms.add(new Room(idRoom, facilities, size, roomPrice, status));
            }
        }
        return rooms;
    }

    public void addRoom(Room room) throws SQLException {
        String query = "INSERT INTO room_info (Facilities, Size, Room_Price, Status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, room.getFacilities());
            statement.setString(2, room.getSize());
            statement.setDouble(3, room.getRoomPrice());
            statement.setString(4, room.getStatus());
            statement.executeUpdate();
        }
    }

    public void updateRoom(Room room) throws SQLException {
        String query = "UPDATE room_info SET Facilities = ?, Size = ?, Room_Price = ? WHERE ID_Room = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, room.getFacilities());
            statement.setString(2, room.getSize());
            statement.setDouble(3, room.getRoomPrice());
            statement.setInt(4, room.getIdRoom());
            statement.executeUpdate();
        }
    }

    public void deleteRoom(int idRoom) throws SQLException {
        String query = "DELETE FROM room_info WHERE ID_Room = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idRoom);
            statement.executeUpdate();
        }
    }

    public void updateRoomStatus(int idRoom, String status) throws SQLException {
        String query = "UPDATE room_info SET Status = ? WHERE ID_Room = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status);
            statement.setInt(2, idRoom);
            statement.executeUpdate();
        }
    }
    
    public Room getRoomById(int idRoom) throws SQLException {
        String query = "SELECT * FROM room_info WHERE ID_Room = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idRoom);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Room(
                    resultSet.getInt("ID_Room"),
                    resultSet.getString("Facilities"),
                    resultSet.getString("Size"),
                    resultSet.getDouble("Room_Price"),
                    resultSet.getString("Status")
                );
            }
        }
        return null;
    }

}
