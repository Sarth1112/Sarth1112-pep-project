package DAO;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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

    public List<Message> getAllmessages(){
        List<Message> messages = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message msg = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(msg);
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;

    }

    public Message getMessageByID(int id){
        Message message = null;
         try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }

            
            }catch(SQLException e){
            e.printStackTrace();
        }
        return message;
       

    }

    public void deleteMessageById(int id){
        try (Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void updateMessageById(Message message){
        try(Connection conn = ConnectionUtil.getConnection()) {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message.getMessage_id());

            ps.executeUpdate();


            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}