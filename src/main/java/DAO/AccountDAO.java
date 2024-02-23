package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account(username, password) values(?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            return getAccountByUsername(account.getUsername());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
