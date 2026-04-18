package application;
	
import data.User;
import data.UserList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import screen.Manager;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Manager.init();
			Manager.setPrimaryStage(primaryStage);
			UserList.init();
			initPlayer();
			
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
			Scene scene = new Scene(root,800,500);
			primaryStage.setResizable(false);
			primaryStage.setTitle("TIC TAC TOE by MaxenceB9");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {	
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void initPlayer()
	{
		User u1 = new User("Joe", "x", 1);
		User u2 = new User("Anatol", "o", 2);

		UserList.getInstance().addUser(u1);
		UserList.getInstance().addUser(u2);
	}
	
	
}
