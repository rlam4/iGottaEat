package iGottaEat;
import java.util.*;

public class User {
	
	private String email, password, lastName, firstName;
	ArrayList<ArrayList<Restaurant>> savedLists = new ArrayList<ArrayList<Restaurant>>();
	ArrayList<Restaurant> favorites = new ArrayList<Restaurant>();
	ArrayList<Restaurant> recommendations = new ArrayList<Restaurant>();
	
	/*Since the number of recentlyVisited has not yet been determined, no queue is used yet*/
	ArrayList<Restaurant> recentlyVisited = new ArrayList<Restaurant>();
	
	/*New user constructor*/
	public User (String email, String password, String lastName, String firstName){
		this.email = email;
		this.password = password;
		this.lastName = lastName;
		this.firstName = firstName;
		
	}
	
	/*Add method, one size fits all*/
	public void addRestaurant (ArrayList<Restaurant> rlist, Restaurant r){
		rlist.add(r);
	}
	
	/*Delete method, one size fits all*/
	public void removeRestaurant (ArrayList<Restaurant> rlist, Restaurant r){
		rlist.remove(r);
	}
	
	/*Add certain lists to individual playlist*/
	public void addPlaylist (ArrayList<Restaurant> rlist){
		savedLists.add(rlist);
	}
	
	/*Recently visited functions*/
	public void addRecent (Restaurant r){
		recentlyVisited.add(r);
	}
	
	/*Since recently visited is indefinite right now, we return the arraylist reverse*/
	public ArrayList<Restaurant> getRecent(){
		ArrayList<Restaurant>temp = new ArrayList<Restaurant>(recentlyVisited); 
		Collections.reverse(temp);
		return temp;
	}
	
	
	
}
