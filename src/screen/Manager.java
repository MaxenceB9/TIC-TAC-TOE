package screen;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Manager {
	private static Manager instance;
	private static Stage primaryStage = null;
	
	private Stage ModalStage;
	private static final Modal MODAL = new Modal();



	public static void init() {
		instance = new Manager();
	}
	
	
	public static void setPrimaryStage(Stage primaryStage) {
		Manager.primaryStage = primaryStage;
	}


	public void openModal(ModalType MT, String title, boolean fix)
	{
		try {
			String path = MODAL.getModal(MT);
			if(path == null)
			{
				throw new IllegalArgumentException("Modal inconnu : " + MT);
			}
			
			Parent root = FXMLLoader.load(getClass().getResource(path));
			ModalStage = new Stage();
			ModalStage.setTitle(title);
			ModalStage.initOwner(Manager.primaryStage);
			
			if(fix == true)
			{
				ModalStage.initModality(Modality.WINDOW_MODAL);	
			}
			else
			{
				ModalStage.initModality(Modality.NONE);	
			}
			ModalStage.setScene(new Scene(root));
			ModalStage.setResizable(false);
			ModalStage.show();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void closeModal()
	{
		ModalStage.close();
	}
	
	
	public static Manager getInstance()
	{
		if(instance == null)
		{
			init();
		}
		return instance;
	}

}
