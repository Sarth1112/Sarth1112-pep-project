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

  


    //logic for the messages API
    public Message messages(Message message){
        
        if(message.getMessage_text() == null || message.getMessage_text().isBlank()){
            return null;
        }
        if(message.getMessage_text().length() > 255){
            return null;
        }
        
        //getPosted by is the foreign key which references to account_id
        Account existingAccount = accountDAO.getAccountById(message.getPosted_by());

        if(existingAccount == null){
            return null;
        }
        return dao.insertMessage(message);


    }

    
}
