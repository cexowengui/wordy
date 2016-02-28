package chatterbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TcpClient {
	public final static int PORT = 1918;
	public final static String SERVER = "127.0.0.1";
	public final static int TIMEOUT = 130000;
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		Socket socket = new Socket(SERVER, PORT);
		socket.setSoTimeout(TIMEOUT);
		System.out.println("Connected to:" + socket);
		
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));  
        PrintWriter output = new PrintWriter(socket.getOutputStream(),true);  
        BufferedReader buf =  new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        
        boolean flag = true; 
		while(flag){
			System.out.print("输入信息：");  
            String str = input.readLine();  
            output.println(str); 
            if("bye".equals(str)){  
                flag = false;  
            }else{  
                try{  
                    String echo = buf.readLine();  
                    System.out.println(echo);  
                }catch(SocketTimeoutException e){  
                    System.out.println("Time out, No response");  
                }  
            }  	
		}
		
		input.close();
		output.close();
		if (null != socket) {
			socket.close();
		}    
	}
}
