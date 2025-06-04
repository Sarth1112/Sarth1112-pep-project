package DAO;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

   public Message insertMessage(Message message){
        // Use try-with-resources for automatic resource management
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO Message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
           
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) { // Use try-with-resources for ResultSet as well
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        message.setMessage_id(generatedId);
                        return message;
                    }
                }
            }
                
        } catch (SQLException e) {
            System.err.println("Error inserting message: " + e.getMessage());
            e.printStackTrace();
        } 
        return null; 
    }
}