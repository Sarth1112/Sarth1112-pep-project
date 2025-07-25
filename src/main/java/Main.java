import Controller.SocialMediaController;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
// public class Main {
//     public static void main(String[] args) {
//         SocialMediaController controller = new SocialMediaController();
//         Javalin app = controller.startAPI();
//         app.start(8080);
//     }
// }

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        
        System.out.println("Starting server on port 8080...");
        app.start(8080);
        System.out.println("Server started successfully!");
    }
}
