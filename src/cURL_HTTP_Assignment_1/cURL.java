package cURL_HTTP_Assignment_1;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class cURL {
	
	public static void main(String[] args) throws UnknownHostException, IOException  {
		
		HTTP_Interface myClient=new HTTP_Client();
		
		HashMap <String, String> headers=new HashMap<String, String>();
		
		if (!(Utility.validateCommand(args))) {
			System.out.println("Invalid Command Parameters");
			System.exit(0);
		}
		
		String method=Utility.parseMethod(args);
		
		//is verbose output requested?
		boolean verbose=Utility.isVerbose(Arrays.toString(args));
		
				
		//parse host to create a socket
		int port=80;
		String host=Utility.parseHost(args[args.length-1]);
		Socket socket= new Socket (host,port);
		myClient.setSocket(socket);
		
		String query=null;
		
		
		//if command is valid then process request
		if (!(Utility.validateCommand(args))) {
			System.out.println("invalid command parameters");
		}
		else {
			//prepare headers and call the service
			query = prepareHeaders(headers,args);
			serviceRequest(myClient, method, verbose, socket, headers, query, Arrays.toString(args));
		}
	}
	

	
	
	private static String prepareHeaders(HashMap<String, String> headers, String[] args) {

		String query=null;
		Utility.parseH(headers, Arrays.toString(args).replaceAll(",", ""));
		query = Utility.parseQuery(args[args.length-1]);
		return query;
	}

	private static void serviceRequest(HTTP_Interface client, String method, boolean verbose, Socket socket, HashMap<String, String> headers, String query, String arguments) {
		
		if (method.equalsIgnoreCase("get")) {
			processGet(client, headers, query);
			}
		else {
			processPost(client, headers, query, arguments);
			
			}
		try {
			client.receiveResponse(verbose);
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
	
	private static void processPost(HTTP_Interface client, HashMap<String, String> headers, String query, String arguments) {
		
			String data=null;
			String filePath=null;
			ArrayList <String> dataArray=null;
			
			if (Utility.isInlineData(arguments)) {
				
				data=Utility.parseD(arguments.replaceAll(",", ""));
			}
			if (Utility.isFilePresent(arguments.toString().replaceAll(",", ""))) {
				filePath=Utility.parseFilePath(arguments.toString().replaceAll(",", ""));
				dataArray=Utility.parseFileData(filePath);
			}
			
			if(data!=null) {
				headers.put("data", data);
				
			}
			
			if (dataArray!=null) {
				try {
					client.post(headers, query,dataArray);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			
			else {
				try {
					client.post(headers, query);
				} catch (IOException e) {
						e.printStackTrace();
				}
			}
	}

	private static void processGet(HTTP_Interface client, HashMap<String, String> headers, String query) {
	try {
		client.get(headers, query);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	}

}
