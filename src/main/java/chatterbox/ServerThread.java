package chatterbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerThread implements Runnable {
	private Socket client;               // Socket connect to client
	
	public ServerThread(Socket client) {
		this.client = client;
	}
	public static void handle(Socket client) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));  
        PrintStream out = new PrintStream(client.getOutputStream());  
        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));  
        
        boolean flag = true;  
        while(flag){  
            System.out.print("输入信息：");  
            String str = input.readLine();  
            out.println(str);  
            if("bye".equals(str)){  
                flag = false;  
            }
            String echo = buf.readLine();  
            System.out.println(echo);  
               
        }  
        input.close();
		
	}

	@Override
	public void run() {
		try {
			handle(client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
