package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     private AccountService accountService;
     private MessageService messageService;

     public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
     }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages",this::messages);
        app.get("/messages",this::getAllmessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}",this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccount);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    private void register(Context context) {
        Account account = context.bodyAsClass(Account.class);
        
        //validate and register
        Account registered = accountService.register(account);


        if(registered != null){
            context.json(registered);
            context.status(200);
        }else {
            context.status(400);
        }

    }

    //this end point, grabs the login information, Checks with the service layer for the login information
    private void login(Context context) {
        Account account = context.bodyAsClass(Account.class);
        
        
        Account loginAccount = accountService.login(account);

        if(loginAccount !=  null){
            context.json(loginAccount);
            context.status(200);
        }else {
            context.status(401);
        }

    }


    private void getAllmessages(Context context){
        List<Message> messages = messageService.getAllmessages();
        context.json(messages);

    }

    private void getMessageById(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageByID(id);

        if(message != null){
            context.json(message);
        }else {
            context.result("");
        }

    }

    private void deleteMessageById(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(id);

        if(deleted != null){
            context.json(deleted);
        }else {
            context.json("");
        }




    }

    private void updateMessageById(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message incoming = context.bodyAsClass(Message.class);
        incoming.setMessage_id(id);
        Message updated = messageService.updateMessage(incoming);

        if(updated == null){
            context.status(400);
        }else{
            context.json(updated);
        }

    }

    private void getMessagesByAccount(Context ctx){
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(accountId);

        if(messages != null){
            ctx.json(messages);
        }else{
            ctx.json("");
        }

    }



  private void messages(Context context) {
    try {
        Message message = context.bodyAsClass(Message.class);
        
        if (message == null) {
            context.status(400);
            return;
        }
        

        
        Message created = messageService.createMessage(message);

        if (created != null) {
            context.json(created);
            context.status(200);
        } else {
            context.status(400);
        }
        
    } catch (Exception e) {
        System.err.println("Error in messages endpoint: " + e.getMessage());
        //e.printStackTrace();
        
        context.status(500);
    }
}


}