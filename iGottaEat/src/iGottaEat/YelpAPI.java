package iGottaEat;

import java.util.ArrayList;

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

public class YelpAPI {
	private static final String API_HOST = "api.yelp.com";
	private static final int SEARCH_LIMIT = 10;
	private static final String SEARCH_PATH = "/v2/search";

	OAuthService service;
	Token accessToken;

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
	private String searchForBusinessesByLocation(String location) {
		OAuthRequest request = createOAuthRequest(SEARCH_PATH);
		request.addQuerystringParameter("term", "food");
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
	public static ArrayList<Restaurant> queryAPI(YelpAPI yelpApi, String location) {
		ArrayList<Restaurant> parsedRestaurants = new ArrayList<Restaurant>();
		String searchResponseJSON =
				yelpApi.searchForBusinessesByLocation(location);

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
		 * and populates the ArrayList parsedRestaurants
		 */
		for(int i = 0; i < SEARCH_LIMIT - 1; i++) {
			JSONObject currentBusinessJSON = (JSONObject) businesses.get(i);
			String currentName = currentBusinessJSON.get("name").toString();
			Double currentRating = (Double) currentBusinessJSON.get("rating");
			String currentURL = currentBusinessJSON.get("url").toString();
			String currentPhone = currentBusinessJSON.get("phone").toString();
			JSONObject currentLocationJSON = (JSONObject) currentBusinessJSON.get("location");
			JSONArray currentLocationArr = (JSONArray) currentLocationJSON.get("display_address");
			String currentLocationBack = currentLocationArr.get(1).toString();
			String currentLocation = currentLocationArr.get(0).toString().concat(currentLocationBack);

			Restaurant currentRestaurant = new Restaurant();
			currentRestaurant.setName(currentName);
			currentRestaurant.setRating(currentRating);
			currentRestaurant.setURL(currentURL);
			currentRestaurant.setNumber(currentPhone);
			currentRestaurant.setAddress(currentLocation);

			//Adds the new Restaurant object to the list
			parsedRestaurants.add(currentRestaurant);
		}

		return parsedRestaurants;
	}
}
