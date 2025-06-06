package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import java.util.*;

public class MessageService {

    private MessageDAO dao;
    private AccountDAO accountDAO;

    public MessageService(){
        this.dao = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    public Message createMessage(Message message) {
        if (message == null) {
            return null;
        }
        
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty()) {
            return null;
        }
        
        if (message.getMessage_text().length() > 255) {
            return null;
        }
        
        
        try {
            Account account = accountDAO.getAccountById(message.getPosted_by());
            if (account == null) {
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error checking account existence: " + e.getMessage());
            return null;
        }
        // In your MessageService.createMessage method, add:
        Message created = dao.insertMessage(message);
        if (created != null) {
            System.out.println("Created message with ID: " + created.getMessage_id());
        }
        return created;
    }

    public List<Message> getAllmessages(){
        return dao.getAllmessages();
    }

    public Message getMessageByID(int id){
       return dao.getMessageByID(id);

    }

    public Message deleteMessageById(int id){
        Message toDelete = dao.getMessageByID(id);

        if(toDelete != null){
            dao.deleteMessageById(id);
        }

        return toDelete;
        

    }

    //updating the existing here, then passing it to database
   public Message updateMessage(Message message) {
    if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
        return null;
    }

    Message existing = dao.getMessageByID(message.getMessage_id());
    if (existing == null) {
        return null; // Message doesn't exist
    }

    existing.setMessage_text(message.getMessage_text());
    dao.updateMessageById(existing);
    return existing;
    }


    public List<Message> getMessagesByAccountId(int accountId){
        return dao.getMessagesByAccountId(accountId);
    }
}