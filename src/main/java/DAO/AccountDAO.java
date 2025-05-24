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

    public Account inserAccount(Account account) {
            Connection connection = ConnectionUtil.getConnection();
            try {
               
                //Write SQL logic here
                String sql = "INSERT Into Account (account_id,username,password) VALUES (?,?,?) " ;
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                //write preparedStatement's setString and setInt methods here.
                preparedStatement.setInt(1, account.getAccount_id());
                preparedStatement.setString(2,account.getUsername());
                preparedStatement.setString(4, account.getPassword());
    
                preparedStatement.executeUpdate();
                return account;
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;

    }
    
}
