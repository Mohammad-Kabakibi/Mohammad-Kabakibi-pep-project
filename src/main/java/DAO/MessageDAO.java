package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    
    public Message addMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message(posted_by, message_text, time_posted_epoch) values(?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys(); // to get the id of the inserted message
            if(resultSet.next()){
                int messageId = resultSet.getInt(1);   
                message.setMessage_id(messageId);
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message;";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Message> messages = new ArrayList<>();
            
            while(resultSet.next()){
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                Long time_posted_epoch = resultSet.getLong("time_posted_epoch");
                messages.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
            }
            return messages;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                Long time_posted_epoch = resultSet.getLong("time_posted_epoch");
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    
    public List<Message> getMessagesbyPostedBy(int posted_by){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Message> messages = new ArrayList<>();

            while(resultSet.next()){
                int message_id = resultSet.getInt("message_id");
                String message_text = resultSet.getString("message_text");
                Long time_posted_epoch = resultSet.getLong("time_posted_epoch");
                messages.add(new Message(message_id, posted_by, message_text, time_posted_epoch));
            }
            return messages;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public boolean deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "delete from message where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            
            int deleted_rows = preparedStatement.executeUpdate();
            return deleted_rows != 0; // should be 1
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateMessageById(int message_id, String new_text){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "update message set message_text = ? where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, new_text);
            preparedStatement.setInt(2, message_id);
            
            int updated_rows = preparedStatement.executeUpdate();
            return updated_rows != 0; // should be 1
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


}
