import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class tcss543 {

	
	
	public static void main(String args[]) throws Exception
	{
		PushRelabel pushRelabel = new PushRelabel();
		
		BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                
                String[] splited = line.split("\\s+");
                
                int offset = splited.length - 3;
                
                String first = splited[offset + 0] ;
                String second = splited[offset + 1];
                int capacity = Integer.parseInt(splited[offset + 2]);  
                
                pushRelabel.addEdge(first, second, capacity);
            }
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            if (in != null) {
                in.close();
            }
        }
		
//		pushRelabel.addEdge("s", "1", 13);
//		pushRelabel.addEdge("s", "2", 10);
//		pushRelabel.addEdge("2", "1", 3);
//		pushRelabel.addEdge("1", "t", 7);
//		pushRelabel.addEdge("3", "4", 10);
//		pushRelabel.addEdge("4", "t", 5);
//		pushRelabel.addEdge("1", "3", 6);
		
//		pushRelabel.addEdge("s", "1", 3);
//		pushRelabel.addEdge("1", "2", 1);
//		pushRelabel.addEdge("2", "t", 2);
		
		
//		pushRelabel.addEdge("s", "1", 16);
//		pushRelabel.addEdge("s", "2", 13);
//		pushRelabel.addEdge("1", "2", 10);
//		pushRelabel.addEdge("2", "1", 4);
//		pushRelabel.addEdge("1", "3", 12);
//        pushRelabel.addEdge("2", "4", 14);
//        pushRelabel.addEdge("3", "2", 9);
//        pushRelabel.addEdge("3", "t", 20);
//        pushRelabel.addEdge("4", "3", 7);
//        pushRelabel.addEdge("4", "t", 4);
		
		System.out.println(pushRelabel.getMaxFlow());
	}
	
}
