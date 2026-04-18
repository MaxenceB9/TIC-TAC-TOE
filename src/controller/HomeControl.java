package controller;

import data.UserList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import screen.Manager;
import screen.ModalType;

public class HomeControl {
	
	
	@FXML
	public void runONvONparty()
	{
		int userListSize = UserList.getInstance().getList().size();
		
		if(userListSize == 2)
		{
			Manager.getInstance().openModal(ModalType.GAME, "TIC TAC TOE | 1vs1", false);
		}
	}
	
	@FXML
	public void editPlayer()
	{
		Manager.getInstance().openModal(ModalType.SETTIGNS_PLAYER, "Editer les joueurs", true);
	}

	@FXML
	public void openRules()
	{
		Manager.getInstance().openModal(ModalType.RULE, "Règles du jeu", false);
	}
}
