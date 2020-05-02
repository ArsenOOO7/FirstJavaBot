package bot.java;

public class MainClass {
	
	
	public static void main(String[] args){
	    
		try {
			
			Bot bot = new Bot();
			bot.tick();
			
		}catch(Exception e) {
			
			System.out.println(e.getMessage());
			
		}
	}
}