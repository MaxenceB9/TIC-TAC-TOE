package data;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String username;
	private String PawnType;
	private int order;
	private int[][] Grid = new int[3][3];
	private boolean win = false;
	private int score = 0;
	
	public User(String name, String PawnIco, int ord)
	{
		this.username = name;
		this.PawnType = PawnIco;
		order = ord;
	}


	public String getPawnType() {
		return PawnType;
	}

	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		if(order < 1)
		{
			this.order = 1;
		}
		else if(order > 2)
		{
			this.order = 2;
		}
		else
		{
			this.order = order;
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public int[][] getGrid() {
		return Grid;
	}

	public void addPoint(int x, int y, int val)
	{
		Grid[x][y] = val;
	}
	public void setGrid(int[][] grid) {
		Grid = grid;
	}
	public void clearGrid()
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				Grid[i][j] = 0;
			}
		}
	}


	public boolean isWin() {
		return win;
	}


	public void setWin(boolean win) {
		this.win = win;
	}


	public int getScore() {
		return score;
	}


	public void incScore() {
		this.score++;
	}
	
	public void resetScore() {
		this.score =  0;
	}
}
