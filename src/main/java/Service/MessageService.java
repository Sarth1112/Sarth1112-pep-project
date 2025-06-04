package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

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
}