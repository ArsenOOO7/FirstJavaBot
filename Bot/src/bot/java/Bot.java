package bot.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;


import bot.java.internet.Internet;

public class Bot {
	
	public String access_token = "";
	public Integer group_id = 174995252;
	
	public Internet internet;
	public Messages messages;
	public Levenshtein levenshtein;
	
	public JSONObject paramsJson;
	
	ArrayList<String> words = new ArrayList<String>();
	
	
	public Bot() throws Exception{
		
		 internet = new Internet();
		 messages = new Messages(this);
		 levenshtein = new Levenshtein(this);
		  
	}
	
	
	
	public void getLongPoll() throws Exception{
		
		String url = "https://api.vk.com/method/groups.getLongPollServer?";
		HashMap<Integer, String> params = new HashMap<Integer, String>();
		params.put(0, "v=5.80");
		params.put(1, "access_token=" + access_token);
		params.put(2, "group_id=" + group_id);
		
		JSONObject object = new JSONObject(internet.getURL(url + Internet.QueryBuilder(params)));
		paramsJson = object.getJSONObject("response");
		
		
	}
	
	
	public void sendMessage(int peer_id, int user_id, String text) throws Exception{
		
		String url = "https://api.vk.com/method/messages.send?";
		
		internet.sendPost(url, "v=5.80&access_token=" + access_token + "&peer_id=" + peer_id + "&message=" + text);
		
	}
	
	
	public String fileToString(String filename) throws IOException
	
	{
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    StringBuilder builder = new StringBuilder();
	    String line;

	    // For every line in the file, append it to the string builder
	    while((line = reader.readLine()) != null)
	    {
	        builder.append(line);
	    }

	    reader.close();
	    return builder.toString();
	}
	
	
	
	public void tick() throws Exception{
		
		System.out.println("Начинаем работать...");
		getLongPoll();
		System.out.println("Получили необходимое...");
		
		int ts = (int)paramsJson.get("ts");
		String server = (String) paramsJson.get("server");
		String key = (String) paramsJson.get("key");
		while(true) {
			
			JSONObject response = new JSONObject(internet.getURL(server + "?" + "act=a_check&key=" + key + "&ts=" + ts + "&wait=20"));
			
			if(response.has("failed")) {
				
				switch(response.get("failed").toString()){
					
					case "1":
						
						paramsJson.remove("ts");
						paramsJson.put("ts", response.get("ts"));
					
					break;
					
					case "2":
					case "3":
						
						getLongPoll();
						
					break;
					
				}	
			}
			
			ts = (int) response.getInt("ts");
			
			if(!response.has("updates"))
				continue;
			
			response.getJSONArray("updates").forEach(update -> {
				try {
					
					
					JSONObject obj = (JSONObject) update;
					JSONObject object = obj.getJSONObject("object");
					
					int from_id = 0;
					int peer_id = 0;
					String text = "";
					
					if(object.has("from_id"))
						from_id = object.getInt("from_id");
					
					if(object.has("peer_id"))
						peer_id = object.getInt("peer_id");
					
					if(object.has("text"))
						text = object.getString("text");
				
					if(from_id > 0 && from_id > 0)
						messages.checkMessage(from_id, peer_id, text);
					
				} catch (Exception e) {
					
					System.out.println(e.getMessage());
					
				}
				
			});
			
		}
		
	}

}
