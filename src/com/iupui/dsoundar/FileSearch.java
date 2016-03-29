package com.iupui.dsoundar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileSearch
 */

public class FileSearch extends HttpServlet {
	String line = null;
	BufferedReader buffer = null;
	BufferedWriter bwrite = null;
	FileWriter fw = null;
	boolean found = false;
	FileReader fr = null;
	ArrayList<String> alist = new ArrayList<String>();
	String path = "C:/Users/ajsiv/Documents/Predictive/TextFiles";
	String pdfPath = "C:\\Users\\ajsiv\\Documents\\Predictive\\PDFfiles\\";
	boolean stop = false;
	StringBuilder resp = new StringBuilder();
	private static final long serialVersionUID = 10902304810394802L;

    /**
     * Default constructor. 
     */
    public FileSearch() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("GET method not supported");
		resp.delete(0, resp.length());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		resp.delete(0, resp.length());
		request.setAttribute("searchResult", searchresult(request.getParameter("keyword")));
		dispatcher.forward(request,  response);
	}
	
	protected void initializeList(){
		alist.add("<table class=\"table\">");
		alist.add("<thead><tr><th>Document Name</th><th>Download Link</th></tr></thead>");
		alist.add("<tbody>");
	}
	protected String htmlpage(){
		String x = "";
		return x;
	}
	protected String stripExtension (String str) {
	        // Handle null case specially.
	        if (str == null) return null;
	        // Get position of last '.'.
	        int pos = str.lastIndexOf(".");
	        // If there wasn't any '.' just return the string as is.
	        if (pos == -1) return str;
	        // Otherwise return the string, up to the dot.
	        return str.substring(0, pos);
	    }
	protected String searchresult(String key) throws IOException{
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		initializeList();
				for (int i = 0; i < listOfFiles.length; i++) {
				      if (listOfFiles[i].isFile()) {
				    	  fr = new FileReader(listOfFiles[i]);
				    	  buffer = new BufferedReader(fr);
				    	 
				    	  while ((line = buffer.readLine())!= null){
				    		  if(line.contains(key)){
				    			  found=true;
				    		  }
				    	  }
				    	  if (found){
				    		  alist.add("<tr><td>"+ stripExtension(listOfFiles[i].getName()) + "</td><td><a href='" + pdfPath + stripExtension(listOfFiles[i].getName())  + ".pdf' download>Download</a></td></tr>");
				    	  }
				    	  found = false;
				      }
				    }
				alist.add("</tbody></table>");
				if (alist.size() > 0){
					for(String files:alist){
				    	resp.append(files);
				    };
				}else{
					resp.append("No Documents found matching the keyword");
				}
		return resp.toString();
	}

}
