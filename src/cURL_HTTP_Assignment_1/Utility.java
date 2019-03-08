package cURL_HTTP_Assignment_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	public static boolean validateCommand(String[] args) {
		
		String argument=Arrays.toString(args);
		boolean inlineData=isInlineData(argument);
		boolean isFile=isFilePresent(argument);
		
		
		if (!(args[0].equalsIgnoreCase("httpc"))) {
			return false;
		}
		else if (!(args[1].equalsIgnoreCase("get") || args[1].equalsIgnoreCase("post")))  {
			return false;
		}
		else if (inlineData&&isFile) {
			return false;
		}
		else if (parseMethod(args).equalsIgnoreCase("get")&&(inlineData||isFile)) {
			return false;
		}
		else {
			return true;
		}
		
	}

	public static boolean isVerbose(String verbose) {
		Pattern p=Pattern.compile(" -v");
		Matcher m=p.matcher(verbose);
		if (m.find()) {
			return true;
		}	
		else {
			return false;
		}
	}
	
	public static boolean isInlineData(String data) {
		Pattern p=Pattern.compile(" -d");
		Matcher m=p.matcher(data);
		if (m.find()) {
			return true;
		}	
		else {
			return false;
		}
	}

	
	public static boolean isFilePresent(String verbose) {
		Pattern p=Pattern.compile(" -f");
		Matcher m=p.matcher(verbose);
		if (m.find()) {
			return true;
		}	
		else {
			return false;
		}
	}
	
	public static String parseMethod(String [] args) {
		if (args[1].equals("post")) {
			return "Post";
		}
		else {
			return "Get";
		}
	}

	public static String parseHost(String url) {

		String temp=null;
		String [] urlArrayWWW=url.split("http.*www.");
		String [] urlArray=url.split("http://");
		
		if (urlArrayWWW.length>1) {
			temp=urlArrayWWW[1];
		
		for (int i=0;i<temp.length();i++) {
			if((temp.charAt(i))=='/') {
				temp=temp.substring(0, i);
				break;
				}
			}
		
		}
		else {
		temp=urlArray[1];
		
		for (int i=0;i<temp.length();i++) {
			if((temp.charAt(i))=='/') {
				temp=temp.substring(0, i);
				break;
				}
			}
		}
		return temp;
	}
	
	
	
	public static String parseQuery(String s) {
		
		String [] urlArray=s.split("http.*//");
		String temp=urlArray[1];
		
		for (int i=0;i<temp.length();i++) {
			
			if((temp.charAt(i))=='/') {
				temp=temp.substring(i+1, temp.length());
				temp="/"+temp;
				break;
				}
			}
		return temp;
		}

	public static void parseH(HashMap <String, String> headers, String s) {
		Pattern p=Pattern.compile("-h\\s.*?\\s");
		Matcher m=p.matcher(s);	
		int i=1;
		String answer;
		while (m.find()) {
			answer=m.group(0);
			answer=answer.substring(3, answer.length()-1);
			headers.put("header"+i, answer);
			i++;
		}
		headers.put("host", "Host: "+parseHost(s));
	}

	public static String parseD(String s) {

		Pattern p=Pattern.compile("-d\\s.*?}"); 
		Matcher m=p.matcher(s);	
		String answer=null;
			
		if (m.find()) {
			answer=m.group(0);
		}
		answer=answer.substring(3);
		return answer;
		
	}

	

	public static String parseFilePath(String path) {
		String answer=null;
		int index=0; 
		Pattern filePattern=Pattern.compile("-f .*http://");
		Matcher fp=filePattern.matcher(path);
		if (fp.find()) {
			answer=fp.group(0);
		}
		if (answer!=null){
			int g=answer.indexOf(" http://");
			answer= answer.substring(3,g);
		}
		return answer;
	}

	public static ArrayList <String> parseFileData(String filePath) {
		ArrayList <String> dataArray=new ArrayList ();
		String temp=null;
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			temp=br.readLine();
			while (temp!=null) {
				dataArray.add(temp);
				temp=br.readLine();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataArray;
	}
}
