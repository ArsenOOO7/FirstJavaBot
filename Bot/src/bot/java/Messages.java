package bot.java;

import java.util.HashMap;


public class Messages {
	
	public Bot bot;
	public HashMap<Integer, Boolean> peers = new HashMap<Integer, Boolean>();
	
	
	public Messages(Bot botClass) {
		
		bot = botClass;
		
	}
	
	
	public void checkMessage(int user_id, int peer_id, String text) throws Exception {
		
		if(peer_id == user_id)
			return;
		
		String[] message = text.split(" ");
		
		switch(message[0].toLowerCase()) {
		
			case "дурка":
				
				if(user_id != 227927111)
					return;
				
				if(peers.containsKey(peer_id)) {
					
					bot.sendMessage(peer_id, user_id, "Вы выключили режим 'дурка'");
					peers.remove(peer_id);
					
				}else{
					
					bot.sendMessage(peer_id, user_id, "Вы включили режим 'дурка'");
					peers.put(peer_id, true);
					
				}
				
			break;
			
			default:
				
				//if(user_id != 227927111)
					//return;
				
				if(peers.containsKey(peer_id))
					bot.sendMessage(peer_id, user_id, bot.levenshtein.getWords(text));
				
				
			break;
		
		}
		
	}
	
	
}
