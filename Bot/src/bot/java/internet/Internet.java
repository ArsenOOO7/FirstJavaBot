package bot.java.internet;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;

public class Internet {

	public String getURL(String url) throws Exception{
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		return (String)response.body();
		
	}
	
	
	
	public static String QueryBuilder(HashMap<Integer, String> data) {
		
		String post = "";
		for(int i = 0; i < data.size(); ++i){
			
			if(i > 0)
				post += "&" + data.get(i);
			else
				post += data.get(i);
			
		}
		
		return post;
		
	}
	
	
	
	public void sendPost(String url, String data) throws Exception{
		
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).POST(BodyPublishers.ofString(data)).build();
		
		client.send(request, BodyHandlers.discarding());
		
	}
	
}
