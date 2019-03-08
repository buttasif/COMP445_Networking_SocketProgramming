package cURL_HTTP_Assignment_1;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public interface HTTP_Interface {
	
	
	
	void get(HashMap <String, String> headers, String query) throws IOException;
	
	void post(HashMap <String, String> headers, String data, ArrayList <String> fileDataArray)throws IOException;
	
	public void receiveResponse(boolean verbose) throws IOException;
	
	public void setSocket(Socket socket2);

	void post(HashMap<String, String> headers, String query) throws IOException;

}
