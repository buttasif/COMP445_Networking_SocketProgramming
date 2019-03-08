package cURL_HTTP_Assignment_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

public class HTTP_Client implements HTTP_Interface {

 	private Socket socket;
	private BufferedReader br;
	
	private PrintWriter writer;
	
	public HTTP_Client () {
		socket=null;
	}
	
	public HTTP_Client (Socket socket) {
		this.socket=socket;
	}
	
	
	@Override
	public void get(HashMap<String, String> headers, String query) throws IOException {
		setupOutputStream();
		writer.println("GET " + query + " HTTP/1.1");
		
		String line=null;
		for (String key : headers.keySet()) {
			line =headers.get(key);
			writer.println (headers.get(key));
			}
		writer.flush();
		writer.println("Connection: close");
		writer.println();
	}

	@Override//
	public void post(HashMap <String, String> headers, String query, ArrayList <String> fileDataArray) throws IOException {
		
		setupOutputStream();
		String data=headers.get("data");
		writer.println("POST " + query + " HTTP/1.0");
		if ( data !=null) {
			writer.println("Content-Length: "+data.length());	
		}
		for (String key : headers.keySet()) {
			
			if (!(key.equalsIgnoreCase("data")))
			writer.println(headers.get(key));
		}
		writer.println();
		if (data!=null) { 
			writer.println(headers.get("data"));
		}
		for (String line: fileDataArray) {
			writer.println(line);
		}
		writer.println();
		writer.flush();
		
	}
	
	public void receiveResponse(boolean verbose) throws IOException {
		String emptyLine="\n";
		String line=null;
		setInputStream();
		line=br.readLine();
		
		if (verbose) {
			while (line!=null) {
				System.out.println(line);
				line=br.readLine();
			}
		}
		else {
			while(!(line.isEmpty())) {
				line=br.readLine();
			}
			while (line!=null) {
				System.out.println(line);
				line=br.readLine();
			}
		}
		
		br.close();
		socket.close();		
	}
	
	public void setSocket(Socket socket2) {
		this.socket=socket2;
		
	}
	
	private void setInputStream() throws IOException {
		br=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	private void setupOutputStream() throws IOException {
		
		writer= new PrintWriter(socket.getOutputStream(), true);
		
	}

	@Override
	public void post(HashMap<String, String> headers, String query) throws IOException {

		setupOutputStream();
		String data=headers.get("data");
		writer.println("POST " + query + " HTTP/1.0");
		if ( data !=null) {
			writer.println("Content-Length: "+data.length());	
		}
		for (String key : headers.keySet()) {
			
			if (!(key.equalsIgnoreCase("data")))
			writer.println(headers.get(key));
		}
		writer.println();
		if (data!=null) { 
			writer.println(headers.get("data"));
		}
		
		writer.println();
		writer.flush();
		
	}
}
