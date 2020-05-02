package bot.java;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

class Levenshtein{
	
	public ArrayList<String> words = new ArrayList<String>();
	public Map<Integer, String> repeat = new HashMap<>();
	public static int repeatInt = 1;
		
	public Levenshtein(Bot bot) throws Exception{
		
		 JSONArray file = new JSONArray(bot.fileToString(System.getProperty("user.dir") + "/messages.json"));
		 file.forEach(word -> {
			 
			 try {
				words.add(new String(word.toString().getBytes(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 });
		
	}
	
	
	public String getWords(String need) {
		
		int shortest = -1;
		String closest = "";
		
		for(int i = 0; i < words.size(); ++i) {
			
			String word = words.get(i);
			if(word.equals(need)) {
				
				shortest = 0;
				closest = word;
				break;
				
			}
			
			int levenshtein = getDistance(need.toLowerCase(), word.toLowerCase());
			
			if(levenshtein <= shortest || shortest < 0) {
					
//				for(Map.Entry<Integer, String> entry : repeat.entrySet())
//					System.out.println(entry.getKey() + " - " + entry.getValue());
				if(repeat.containsValue(word))
					continue;
				
				shortest = levenshtein;
				closest = word;
				
			}
			
		}
		
		if(repeatInt == 15) {
			
			repeatInt = 1;
			
		}
		
		repeat.put(repeatInt, closest);
		++repeatInt;
		
		return closest;
		
	}
	
	
	public int getDistance(String string_1, String string_2){
		
		char[] str1 = string_1.toCharArray();
		char[] str2 = string_2.toCharArray();
		return dist(str1, str2);
		
	}
	
	
	
	public int dist( char[] s1, char[] s2 ) {

	    // memoize only previous line of distance matrix     
	    int[] prev = new int[ s2.length + 1 ];

	    for( int j = 0; j < s2.length + 1; j++ ) {
	        prev[ j ] = j;
	    }

	    for( int i = 1; i < s1.length + 1; i++ ) {

	        // calculate current line of distance matrix     
	        int[] curr = new int[ s2.length + 1 ];
	        curr[0] = i;

	        for( int j = 1; j < s2.length + 1; j++ ) {
	            int d1 = prev[ j ] + 1;
	            int d2 = curr[ j - 1 ] + 1;
	            int d3 = prev[ j - 1 ];
	            if ( s1[ i - 1 ] != s2[ j - 1 ] ) {
	                d3 += 1;
	            }
	            curr[ j ] = Math.min( Math.min( d1, d2 ), d3 );
	        }

	        // define current line of distance matrix as previous     
	        prev = curr;
	    }
	    return prev[ s2.length ];
	}
	
}
