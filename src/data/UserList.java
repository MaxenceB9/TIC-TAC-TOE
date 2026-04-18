package data;

import java.util.ArrayList;
import java.util.List;

public class UserList {
	private static  UserList instance;
	private List<User> list; //liste des joueurs
	private boolean RandomOrder = false;

	public static void init() //on initialise la class
	{
		instance = new UserList();
	}
	
	private UserList()
	{
		list = new ArrayList<User>();
	}
	
	public static UserList getInstance() // on récupère l'instance de la class
	{
		if(instance == null)
		{
			init();
		}
		return instance;
	}

	public List<User> getList() { //on récupère la liste des joueurs
		return list;
	}

	public void addUser(User p) { //on ajoute un joueurs
		if(list.size() <= 2)
		{
			this.list.add(p);
		}
	}
	public boolean isRandomOrder() // on retourne la valeur de random order
	{
		return RandomOrder;
	}
	 
	public void setRandomOrder(boolean b) // on défini la valeur de random order
	{
		this.RandomOrder = b;
	}
}
