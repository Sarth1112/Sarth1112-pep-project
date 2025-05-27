package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

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
     }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages",this::messages);

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

     private void messages(Context context) {
        Message message = context.bodyAsClass(Message.class);
        
        
        Message messages = messageService.messages(message);

        if(messages !=  null){
            context.json(messages);
            context.status(200);
        }else {
            context.status(400);
        }

    }


}