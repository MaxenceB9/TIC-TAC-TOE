package controller;

import java.io.IOException;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import data.User;
import data.UserList;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import screen.Manager;
import screen.ModalType;

public class GameControl {
	@FXML
	public Label textInfo, playerOnePoint, playerTwoPoint;

	@FXML
	public GridPane gameGrid;

	private User currentPlayer;

	@FXML 
	public Button restartGame, leave;
	
	private List<User> players = UserList.getInstance().getList(); // on récupère la liste des joueur

	private int nbButtonCheck = 0; //nb de bouton cliquer sur le grid pane
	
	
	private boolean playerWin = false;

	@FXML
	public void initialize() {
		
		newGame();
	}

	@FXML
	public void ButtonHandleClick(ActionEvent ovent) { 
		Button current = (Button) ovent.getSource();

		//on récupère la position du bouton dans le gridpane
		int row = (GridPane.getRowIndex(current) == null) ? 0 : GridPane.getRowIndex(current);
		int col = (GridPane.getColumnIndex(current) == null) ? 0 : GridPane.getColumnIndex(current);

		for(User u : players) //verifier que aucun joueur a gagner
		{
			if(u.isWin())
			{
				playerWin = true;
			}
		}
		
		if (current.getText().isEmpty() && playerWin == false) { // si le bouton cliquer est vide et que aucun des 2 joueurs a gagner alors on fait les actions de cette fonction
			nbButtonCheck++;
			current.setText(currentPlayer.getPawnType()); //on ajoute 'x' ou 'o' en fonction du joueur qui clique
			currentPlayer.addPoint(col, row, 1); 
			
			if (currentPlayer.getOrder() == 1) { //on change de joueur
				// enregistrer dans un tableau pour chaque joueur la position de chaque btn
												// ou il a cliquer
				for (User u : players) {
					if (u.getOrder() == 2) {
						currentPlayer = u;
						break;
					}
				}
			} else if (currentPlayer.getOrder() == 2) { //on change de joueur
				for (User u : players) {
					if (u.getOrder() == 1) {
						currentPlayer = u;
						break;
					}
				}
			}
			showCurrentPlayer(currentPlayer.getUsername());
			checkWin();
		} else {
			textInfo.setText("Plateau complet");
		}
	}

	@FXML
	public void leaveGame()
	{
		Manager.getInstance().closeModal();
	}
	
	@FXML
	public void resetGame()
	{
		for(User u : players)
		{
			u.resetScore();
		}
		newGame();
		updateScore();
	}
	@FXML
	public void newGame()
	{
		
		//si on veut un ordre aléatoire
		if(UserList.getInstance().isRandomOrder() == true)
		{
			int RandomOrder = (int)(Math.random() * 2 ) + 1;
			players.get(0).setOrder(RandomOrder);
			players.get(1).setOrder(3 - RandomOrder);
		}
		
		for(Node n : gameGrid.getChildren())
		{
			Button current = (Button) n;
			
			current.setText("");
		}
		//on reset le contenue des grilles cacher de chaque joueur
		for(User u : players)
		{
			u.clearGrid();
		}
		nbButtonCheck=0;
		
		//on remet les variable pour savoir qui a gagner a false
		for(User u : players)
		{
			u.setWin(false);
		}
		playerWin = false;
		
		gameController(false); // on n'affiche plus les controller pour relancer la game ou quitter
		clearWinfont();
		if(players.get(0).getScore() == 0 && players.get(1).getScore() == 0)
		{
			playerTwoPoint.setTextFill(Color.BLACK);
			playerOnePoint.setTextFill(Color.BLACK);
		}
		
		for(User u : players)
		{
			if(u.getScore() == 0)
			{
				if(u.getOrder() == 1)
				{
					playerOnePoint.setTextFill(Color.BLACK);
				}
				else
				{
					playerTwoPoint.setTextFill(Color.BLACK);
				}
			}
		}
		setButtonStyle();	// on remet le thèmes des boutons de la grille à leur couleur d'origine
		getFirstPlayer(); // on récupère le premier joueur
		updateScore();
	}
	
	@FXML
	public void openRules()
	{
		Manager.getInstance().openModal(ModalType.RULE, "Règles du jeu", false);
	}

	private void checkWin() {
			
		if(nbButtonCheck == 9)
		{
			textInfo.setText("Egalité");
			gameController(true);
		}
			
		for(User u : players)
		{
			if(checkRow(u.getGrid()) && u.isWin() == false)
			{
				u.setWin(true);
			}
			if(checkCol(u.getGrid()) && u.isWin() == false)
			{
				u.setWin(true);
			}
			if(checkDiag(u.getGrid()) && u.isWin() == false)
			{
				u.setWin(true);
			}
			
			if(u.isWin())
			{
				gameController(true);
				break;
			}
		}
		
		whoWin();
	}

	private boolean checkRow(int[][] tab) {
	    for (int i = 0; i < 3; i++) {
	        if (tab[0][i] == 1 && tab[1][i] == 1 && tab[2][i] == 1) {
	            glowLine(i, i, i, 0, 1, 2);
	            return true;
	        }
	    }
	    return false;
	}

	private boolean checkCol(int[][] tab) {
	    for (int i = 0; i < 3; i++) {
	        if (tab[i][0] == 1 && tab[i][1] == 1 && tab[i][2] == 1) {
	            glowLine(0, 1, 2, i, i, i);
	            return true;
	        }
	    }
	    return false;
	}
	
	private boolean checkDiag(int[][] tab)
	{
		
		if((tab[0][0] == 1 && tab[1][1] == 1 && tab[2][2] == 1) == true)
		{
			glowLine(0, 1, 2, 0, 1, 2);
			return true;
		}
		else if((tab[0][2] == 1 && tab[1][1] == 1 && tab[2][0] == 1) == true)
		{
			glowLine(0, 1, 2, 2, 1, 0);
			return true;
		}
		return false;
	}

	private void whoWin() { // on regarde qui a gagné
	    for (User u : players) {
	        if (u.isWin()) { // on affiche que le joueur qui a gagné
	            textInfo.setText(String.format("%s a gagné", u.getUsername()));
	            u.incScore();
	            setWinFont();
	            gameController(true);
	            updateScore();
	            return;
	        }
	    }
	}
	
	private void setWinFont() // on change la font de textInfo pour montrer qui a gagner
	{
		Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20);
		textInfo.setFont(font);
		textInfo.setTextFill(Color.GREEN);
	}
	private void clearWinfont() // on remet a la normal la font de textInfo
	{
		Font font = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 18);
		textInfo.setFont(font);
		textInfo.setTextFill(Color.BLACK);
	}
	
	private void updateScore() // on mets a jour l'affichage du score de chaque joueur
	{
		playerOnePoint.setText(String.format("%s : %d pts", players.get(0).getUsername().length() > 10 ? players.get(0).getUsername().substring(0,10) : players.get(0).getUsername(), players.get(0).getScore()));
		playerTwoPoint.setText(String.format("%s : %d pts", players.get(1).getUsername().length() > 10 ? players.get(1).getUsername().substring(0,10) : players.get(1).getUsername(), players.get(1).getScore()));
		
		if(players.get(0).getScore() > players.get(1).getScore())
		{
			playerOnePoint.setTextFill(Color.GREEN);
			playerTwoPoint.setTextFill(Color.RED);
		}
		else if (players.get(0).getScore() < players.get(1).getScore())
		{
			playerTwoPoint.setTextFill(Color.GREEN);
			playerOnePoint.setTextFill(Color.RED);
		}
		else if(players.get(0).getScore() == players.get(1).getScore() && (players.get(0).getScore() != 0 && players.get(1).getScore() != 0))
		{
			playerTwoPoint.setTextFill(Color.ORANGE);
			playerOnePoint.setTextFill(Color.ORANGE);
		}
	}
	private void getFirstPlayer() // on récupère le premier joueur
	{
		for (User u : players) {
			if (u.getOrder() == 1) {
				currentPlayer = u;
				showCurrentPlayer(u.getUsername());
				break;
			}
		}
	}
	private void showCurrentPlayer(String playerName) { // on affiche le prochain joueur / le joueur qui joue
		textInfo.setText(String.format("%s à votre tour...", playerName.length() > 10 ? playerName.substring(0, 10) : playerName));
	}
	
	private void gameController(boolean val) // on affiche ou pas les controllers
	{
		restartGame.setVisible(val);
		leave.setVisible(val);
	}
	
	
	
	private void glowLine(int x1, int x2, int x3, int y1, int y2, int y3) // surligner la ligne / diagonal / colonne qui fait gagner
	{
		glowCase(x1,y1);
		glowCase(x2,y2);
		glowCase(x3, y3);
	}
	
	private void glowCase(int x, int y) // on change la font d'un bouton
	{
		for(Node e : gameGrid.getChildren())
		{
			int Xe = (GridPane.getRowIndex(e) == null) ? 0 : GridPane.getRowIndex(e);
			int Ye = (GridPane.getColumnIndex(e) == null) ? 0 : GridPane.getColumnIndex(e);
			
			
			if(Xe == x && Ye == y)
			{
				Button current = (Button)e;
				
				current.setBackground(Background.fill(Color.GREEN));
				Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20);
				current.setFont(font);
				current.setTextFill(Color.WHITE);

			}
		}
	}
	
	private void setButtonStyle() //hanger le style du btn
	{
		for(Node e : gameGrid.getChildren())
		{
			Button current = (Button)e;
			current.setBackground(Background.fill(Color.WHITE));
			Font font = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20);
			current.setFont(font);
			Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
			current.setBorder(border);
			current.setTextFill(Color.BLACK);
		}
		
	}
}
