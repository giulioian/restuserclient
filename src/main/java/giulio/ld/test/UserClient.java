package giulio.ld.test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.launchdarkly.sdk.server.*;
import com.launchdarkly.sdk.*;
import com.launchdarkly.logging.*;



public class UserClient {
	private static final String REST_URI 
    = "https://reqres.in/api/users";
	private Client client = ClientBuilder.newClient();
	
	// Set SDK_KEY to your LaunchDarkly SDK key.
	static final String SDK_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	// Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate.
	static final String FEATURE_FLAG_KEY = "show-user-list";
	
	private static void showMessage(String s) {
		System.out.println("*** " + s);
		System.out.println();
	  }
	
	/**
	 * Used to query the rest endpoint and to retrieve all the users data for a given page
	 * @param page	The page that is requested
	 * @return	A list of User objects
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public List<User> getUsersPerPage(int page) throws JsonParseException, JsonMappingException, IOException {
		WebTarget webTarget = client.target(REST_URI+"?page="+page);            
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		String res = "";
		if (response.getStatus() != 200) {
	         System.out.println("Failed with HTTP Error code: " + response.getStatus());
	         String error= response.getEntity().toString();
	         System.out.println("Error: "+error);
	         System.exit(1);
	    }    
		res = response.readEntity(String.class);
		JsonObject jsonObj = Json.createReader(new StringReader(res)).readObject();
		String jsonData = jsonObj.getJsonArray("data").toString();
		return new ObjectMapper().readValue(
				jsonData, new TypeReference<List<User>>(){});
	}
	public static void main(String[] args) throws IOException {
		
		if (SDK_KEY.equals("")) {
		  showMessage("Please edit UserClient.java to set SDK_KEY to your LaunchDarkly SDK key first");
		  System.exit(1);
		}

		// Using Logs.basic() allows us to get simple console logging without configuring SLF4J.
		LDConfig config = new LDConfig.Builder()
		  .logging(Components.logging(Logs.basic()))
		  .build();

		LDClient client = new LDClient(SDK_KEY, config);

		if (client.isInitialized()) {
		  showMessage("SDK successfully initialized!");
		} else {
		  showMessage("SDK failed to initialize");
		  System.exit(1);
		}
		
		// Set up the user properties. This user should appear on your LaunchDarkly users dashboard
		// soon after you run the demo.
		LDUser user = new LDUser.Builder("example-user-key")
								.name("Sandy")
								.email("sandy@gmail.com")
								.build();

		boolean flagValue = client.boolVariation(FEATURE_FLAG_KEY, user, false);

		showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + flagValue + " for this user");

		// Here we ensure that the SDK shuts down cleanly and has a chance to deliver analytics
		// events to LaunchDarkly before the program exits. If analytics events are not delivered,
		// the user properties and flag usage statistics will not appear on your dashboard. In a
		// normal long-running application, the SDK would continue running and events would be
		// delivered automatically in the background.
		client.close();
		
		UserClient uc = new UserClient();
		List<User> lu = null;
		try {
			lu = uc.getUsersPerPage(1);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if the feature flag for this user is true she will see the list of users, otherwise she'll just see the total users count
		StringBuilder msg = new StringBuilder("Total number of users = "+String.valueOf(lu.size()));
		if (flagValue == true){
			Iterator<User> it = lu.iterator();
			while(it.hasNext())
			{
				User us = (User)it.next();
				msg.append("\n User first name = "+us.getFirst_name()+", last name = "+us.getLast_name());
			}
		}
		System.out.println(msg.toString());
	}
}
