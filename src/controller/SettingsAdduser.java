package controller;

import java.util.List;

import data.User;
import data.UserList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import screen.Manager;

public class SettingsAdduser {
	
	@FXML
	public TextField playerOne;
	@FXML
	public TextField playerTwo;
	
	@FXML
	public RadioButton playerOneStart, playerTwoStart, Random;
	
	
	@FXML
	public void initialize()
	{
		playerOne.setText(UserList.getInstance().getList().get(0).getUsername());
		playerTwo.setText(UserList.getInstance().getList().get(1).getUsername());
		
		if(UserList.getInstance().getList().get(0).getOrder() == 1 && UserList.getInstance().isRandomOrder() == false)
		{
			playerOneStart.setSelected(true);
			playerTwoStart.setSelected(false);
			Random.setSelected(false);
		}
		else if(UserList.getInstance().getList().get(0).getOrder() == 2 && UserList.getInstance().isRandomOrder() == false)
		{
			playerTwoStart.setSelected(true);
			playerOneStart.setSelected(false);
			Random.setSelected(false);
		}
		else if(UserList.getInstance().isRandomOrder())
		{
			playerTwoStart.setSelected(false);
			playerOneStart.setSelected(false);
			Random.setSelected(true);
		}

	}
	
	
	@FXML
	public void RadioHandleClick(ActionEvent event)
	{
		RadioButton current = (RadioButton) event.getSource();
		if(current == playerOneStart)
		{
			playerTwoStart.setSelected(false);
			Random.setSelected(false);
			UserList.getInstance().setRandomOrder(false);
		}
		else if(current == playerTwoStart)
		{
			playerOneStart.setSelected(false);
			Random.setSelected(false);
			UserList.getInstance().setRandomOrder(false);

		}
		else if(current == Random)
		{
			playerOneStart.setSelected(false);
			playerTwoStart.setSelected(false);
			UserList.getInstance().setRandomOrder(true);
		}
	}
	
	@FXML
	public void SaveHandleClick(ActionEvent event)
	{
		User u1 = UserList.getInstance().getList().get(0);
		User u2 = UserList.getInstance().getList().get(1);
		u1.setUsername(playerOne.getText());
		u2.setUsername(playerTwo.getText());
		
		if(playerOneStart.isSelected())
		{
			u1.setOrder(1);
			u2.setOrder(2);
		}
		if(playerTwoStart.isSelected())
		{
			u1.setOrder(2);
			u2.setOrder(1);
		}
		if(Random.isSelected())
		{
			
		}
		
		UserList.getInstance().getList().clear();
		
		UserList.getInstance().addUser(u1);
		UserList.getInstance().addUser(u2);
		
		
		Manager.getInstance().closeModal();
	}
	
}
