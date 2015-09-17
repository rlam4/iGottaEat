package iGottaEat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class IGEDriver {

	ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>(); 
	ArrayList<User> users = new ArrayList<User>();
	
	private static final String CONSUMER_KEY = "NVaDEK37pXM5ZGRfnN3NRg";
	private static final String CONSUMER_SECRET = "RgH-3tzg5URWf-hK8G3NA8anG-s";
	private static final String TOKEN = "V2CM0wuGT55RQ-QNzRzpLsKNZElNMThW";
	private static final String TOKEN_SECRET = "ddiuTnligH82cESsnS_TNi6xHh8";

	public static void main(String[] args) {
		String userLocation;
		ArrayList<Restaurant> restaurants = null;
		Scanner sc = new Scanner(System.in);
		YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
		
		System.out.println("Please enter your location:");
		userLocation = sc.next();
		restaurants = yelpApi.queryAPI(yelpApi, userLocation);
		randomPlace(restaurants);
	}


	// Produces random place. User can see next option by typing any input
	private static void randomPlace(ArrayList<Restaurant> places) {
		Scanner sc = new Scanner(System.in);
		String ans = "";
		int i = 0; // counter to iterate through arraylist, reset to 0 when reshuffled

		places = shuffle(places); // shuffles places like a card deck

		// Continues to provide user with "random" places to eat until "n" is typed
		do {
			System.out.println("Go eat at " + places.get(i).getName());
			System.out.println("Don't like it? Enter any key to get another place or type n to quit.");
			ans = sc.next();
			i++;

			// reshuffles arraylist once all options are used
			if (i == places.size()) {
				places = shuffle(places);
				i = 0;
			}
		} while (!ans.equals("n"));
		sc.close();
	}

	// Shuffles arraylist like a deck of cards
	private static ArrayList<Restaurant> shuffle(ArrayList<Restaurant> places) {
		int j = 0;
		Restaurant temp = null;

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
