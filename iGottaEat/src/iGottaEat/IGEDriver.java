package iGottaEat;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import rfg.TwoStepOAuth;
import rfg.YelpAPI;
import rfg.YelpAPI.YelpAPICLI;


public class IGEDriver {
	
	/*
	 * The following variables are for the Yelp API 
	 */
	private static final String API_HOST = "api.yelp.com";
	private static final String DEFAULT_TERM = "food";
	private static final String DEFAULT_LOCATION = "20742";
	private static final int SEARCH_LIMIT = 10;
	private static final String SEARCH_PATH = "/v2/search";
	private static final String CONSUMER_KEY = "NVaDEK37pXM5ZGRfnN3NRg";
	private static final String CONSUMER_SECRET = "RgH-3tzg5URWf-hK8G3NA8anG-s";
	private static final String TOKEN = "V2CM0wuGT55RQ-QNzRzpLsKNZElNMThW";
	private static final String TOKEN_SECRET = "ddiuTnligH82cESsnS_TNi6xHh8";

	OAuthService service;
	Token accessToken;

	
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
  
  /*
   * The following is getting information from the Yelp API
   */
  
  /**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * @param consumerKey Consumer key
	 * @param consumerSecret Consumer secret
	 * @param token Token
	 * @param tokenSecret Token secret
	 */
	public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
		this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);
	}

	/**
	 * Creates and sends a request to the Search API by term and location.
	 * <p>
	 * 
	 * @param term <tt>String</tt> of the search term to be queried
	 * @param location <tt>String</tt> of the location
	 * @return <tt>String</tt> JSON Response
	 */
	public String searchForBusinessesByLocation(String term, String location) {
	  OAuthRequest request = createOAuthRequest(SEARCH_PATH);
	  request.addQuerystringParameter("term", term);
	  request.addQuerystringParameter("location", location);
	  request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
	  return sendRequestAndGetResponse(request);
	}
	
	/**
	 * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
	 * 
	 * @param path API endpoint to be queried
	 * @return <tt>OAuthRequest</tt>
	 */
	private OAuthRequest createOAuthRequest(String path) {
	  OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
	  return request;
	}

	/**
	 * Sends an {@link OAuthRequest} and returns the {@link Response} body.
	 * 
	 * @param request {@link OAuthRequest} corresponding to the API request
	 * @return <tt>String</tt> body of API response
	 */
	private String sendRequestAndGetResponse(OAuthRequest request) {
	  System.out.println("Querying " + request.getCompleteUrl() + " ...");
	  this.service.signRequest(this.accessToken, request);
	  Response response = request.send();
	  return response.getBody();
	}

	/**
	 * Queries the Yelp Search API and populates the arraylists 
	 * 
	 * @param yelpApi <tt>YelpAPI</tt> service instance
	 * @param yelpApiCli <tt>YelpAPICLI</tt> command line arguments
	 */
	private static void queryAPI(YelpAPI yelpApi, YelpAPICLI yelpApiCli) {
	  String searchResponseJSON =
	      yelpApi.searchForBusinessesByLocation(yelpApiCli.term, yelpApiCli.location);
	  
	  JSONParser parser = new JSONParser();
	  JSONObject response = null;
	  try {
	    response = (JSONObject) parser.parse(searchResponseJSON);
	  } catch (ParseException pe) {
	    System.out.println("Error: could not parse JSON response:");
	    System.out.println(searchResponseJSON);
	    System.exit(1);
	  }

	  JSONArray businesses = (JSONArray) response.get("businesses");
	  
	  /*
	   * Parses the JSON to retrieve the name, rating, URL, phone, and address
	   * and populates the arraylists
	   */
	  for(int i = 0; i < SEARCH_LIMIT - 1; i++) {
		  JSONObject currentBusinessJSON = (JSONObject) businesses.get(0);
		  String currentName = currentBusinessJSON.get("name").toString();
		  Double currentRating = (Double) currentBusinessJSON.get("rating");
		  String currentURL = currentBusinessJSON.get("url").toString();
		  String currentPhone = currentBusinessJSON.get("phone").toString();
		  JSONObject currentLocationJSON = (JSONObject) currentBusinessJSON.get("location");
		  JSONArray currentLocationArr = (JSONArray) currentLocationJSON.get("display_address");
		  String currentLocationBack = currentLocationArr.get(1).toString();
		  String currentLocation = currentLocationArr.get(0).toString().concat(currentLocationBack);
		  
		  //Creates new Restaurant object and puts in parsed information
		  Restaurant currentRestaurant = new Restaurant();
		  currentRestaurant.setName(currentName);
		  currentRestaurant.setRating(currentRating);
		  currentRestaurant.setURL(currentURL);
		  currentRestaurant.setNumber(currentPhone);
		  currentRestaurant.setAddress(currentLocation);
		  
		  //Adds the new Restaurant object to the list
		  restaurants.add(currentRestaurant);
	  }

	}

	/**
	 * Command-line interface for the sample Yelp API runner.
	 */
	private static class YelpAPICLI {
	  @Parameter(names = {"-q", "--term"}, description = "Search Query Term")
	  public String term = DEFAULT_TERM;

	  @Parameter(names = {"-l", "--location"}, description = "Location to be Queried")
	  public String location = DEFAULT_LOCATION;
	}
}
