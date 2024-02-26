package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message) {
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 256){ // valid message
            return messageDAO.addMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }


    public List<Message> getMessagesByUser(int user_id) {
        return messageDAO.getMessagesbyPostedBy(user_id);
    }


    public Message getMessageById(int message_id) {
        if(message_id >= 0){ // valid id
            return messageDAO.getMessageById(message_id);
        }
        return null;
    }

    public Message deleteMessageById(int message_id) {
        if(message_id >= 0){ // valid id
            Message message = messageDAO.getMessageById(message_id);
            if(message != null){
                if(messageDAO.deleteMessageById(message_id)){
                    return message;
                }
            }
        }
        return null;
    }

    public Message updateMessageById(int message_id, String new_text) {
        if(message_id >= 0){ // valid id
            if(!new_text.isBlank() && new_text.length() < 256){
                Message message = messageDAO.getMessageById(message_id);
                if(message != null){
                    return messageDAO.updateMessageById(message, new_text);
                }
            }
        }
        return null;
    }
}
