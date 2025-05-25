package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    private AccountDAO dao;

    public AccountService(){
        dao = new AccountDAO();
    }

    public AccountService(AccountDAO dao){
        this.dao = dao;
    }


    public Account register(Account account){
        //check username is not blank

        if (account.getUsername() == null || account.getUsername().trim().isEmpty()){
            return null;
        }


        //check password is atleast 4 Characters:
        if(account.getPassword() == null || account.getPassword().length() < 4){
            return null;
        }

        //check username is unique
        if(dao.getAccountByUserName(account.getUsername()) != null){
            return null;
        }


        //All good
        return dao.insertAccount(account);
    

    } 

    //Login Logic: using DAO which interacts with the database. Get the username and password.
    public Account login(Account account){
        
        return dao.getAccountByUserNameAndPassword(account.getUsername(),account.getPassword());
    }
    
}
