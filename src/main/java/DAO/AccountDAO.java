package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {


    public Account getAccountByUserName(String username){
            Connection connection = ConnectionUtil.getConnection();
            try{
                String sql = "SELECT * From Account WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, username);

                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Account account = new Account(rs.getInt("account_id"),
                                                  rs.getString("username"),
                                                  rs.getString("password"));

                    return account;
                }
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return null;

    }

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, 1);
            
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();
            
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                // Return new Account with the generated ID
                return new Account(generatedId, account.getUsername(), account.getPassword());
            }
            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUserNameAndPassword(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * From Account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                                              rs.getString("username"),
                                              rs.getString("password"));

                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    return null;
    }

     public Account getAccountById(int accountId) {
        // Use try-with-resources for automatic resource management
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Account WHERE account_id = ?")) {
            
            preparedStatement.setInt(1, accountId);

            try (ResultSet rs = preparedStatement.executeQuery()) { // Use try-with-resources for ResultSet
                if (rs.next()) {
                    return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
}

    
    
}
