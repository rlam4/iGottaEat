package iGottaEat;
import java.util.*;




public class IGEDriver {
	
	ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>(); 
	ArrayList<User> users = new ArrayList<User>();
	
	public static void main(String[] args) {
	  ArrayList<String> places = new ArrayList<String>();
	  
    places = manualEntry();
    randomPlace(places);
    
		/*This is a place holder until we can get our GUI operational.*/
    /*
		System.out.println("Welcome to the random food generator. Here are your options:");
		System.out.println("1 - Favorite Playlists");
		System.out.println("2 - Create Random Playlist");
		System.out.println("3 - Popular Restaurants");
		System.out.println("4 - Generate random restaurant");*/
	}
	
	// Outline of actual process
	
	// User manually enters in each place. Type "whatever" to signify end of list
	private static ArrayList<String> manualEntry() {
    Scanner sc = new Scanner(System.in);
    ArrayList<String> places = new ArrayList<String>();
    String place = "";
    
    System.out.println("Welcome to the Random Food Generator you kind and generous individual");
    System.out.println("Please manually enter the places you would like to eat.\nType whatever when done.");
    place = sc.next();
    
    // keeps adding places to arraylist until user types "whatever"
    while (!place.equals("whatever")) {
      places.add(place);
      System.out.println("Added: " + place); //debug info
      place = sc.next();
    }
    System.out.println("Done.");//debug info
    
    return places;
  }
  
	// Produces random place. User can see next option by typing any input
  private static void randomPlace(ArrayList<String> places) {
    Scanner sc = new Scanner(System.in);
    String ans = "";
    int i = 0; // counter to iterate through arraylist, reset to 0 when reshuffled
    
    places = shuffle(places); // shuffles places like a card deck
    
    // debug info
    for (int j = 0; j < places.size(); j++) { System.out.print(places.get(j));}
    System.out.println();
    
    // Continues to provide user with "random" places to eat until "n" is typed
    do {
      System.out.println("Go eat at " + places.get(i));
      System.out.println("Don't like it? Enter any key to get another place or type n to quit.");
      ans = sc.next();
      i++;
      
      // reshuffles arraylist once all options are used
      if (i == places.size()) {
        places = shuffle(places);
        i = 0;
        
        //debug info
        for (int j = 0; j < places.size(); j++) { System.out.print(places.get(j));}
        System.out.println();
      }
    } while (!ans.equals("n"));
  }
  
  // Shuffles arraylist like a deck of cards
  private static ArrayList<String> shuffle(ArrayList<String> places) {
    int j = 0;
    String temp = "";
    
    // Randomly swaps one index with another
    for (int i = 0; i < places.size(); i++) {
      j = ThreadLocalRandom.current().nextInt(0, i + 1);
      temp = places.get(j);
      places.set(j, places.get(i));
      places.set(i, temp);
    }
    return places;
  }
}
