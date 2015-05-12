package client;

import issues.Issue;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import xml.XMLCreator;
import xml.errorWrapper;
import model.Crawler;
import model.InputValidator;
import model.WSClient;

/**
 * Servlet implementation class Controler
 */
@WebServlet("/Controler")
public class Controler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controler() {
        super();        
    }
    
    public void init() throws ServletException
    {
        this.getServletContext().setAttribute("crawler", new Crawler());
        this.getServletContext().setAttribute("wsclient", new WSClient());
        this.getServletContext().setAttribute("xmlCreator", new XMLCreator());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		InputValidator validate = new InputValidator();
		
		String jspTarget = "";
		String initialPage = "/start.jspx";
		String resultsPage = "/Results.jspx";
		
		if(request.getParameter("submit") == null)
		{
			jspTarget = initialPage;
		}else
		{
			try{
				String url = request.getParameter("url");
				String depth = request.getParameter("depth");
				String isPrefix = request.getParameter("isPrefix");
				
				
				request.setAttribute("url", url);
				request.setAttribute("depth", depth);
				request.setAttribute("isPrefix", isPrefix);

				validate.validateURL(url);
				validate.validateDepth(depth);
				
				//Inputs validated

				int d = Integer.parseInt(depth);
				boolean p = (request.getParameter("isPrefix") == null) ? false : true;

				Crawler c = (Crawler) this.getServletContext().getAttribute("crawler");
				WSClient ws = (WSClient) this.getServletContext().getAttribute("wsclient");
				
				//Get the links to evaluate
				String[] toEvaluate = c.retrieveLinks(url, p, d);
				
				//Evaluate Links
				Map<Integer,Issue> issues = ws.getIssues(toEvaluate);

				//Convert the map to XML for download if required
				errorWrapper ew = new errorWrapper();
				ew.setMap(issues);
				ew.calcStats();
				
				//Get XML File attributes.
				String xmlFileFolder = "/Results";
				String xmlFileLocation = this.getServletContext().getRealPath(xmlFileFolder); 
				String fileName = request.getSession().getId() + ".xml";
				
				//Location of XML File
				String location = xmlFileLocation + File.separator + fileName;
				
				XMLCreator xc = (XMLCreator) this.getServletContext().getAttribute("xmlCreator");
				xc.createXML(location, ew);
				
				//System.out.println("File Location: " + xmlFileLocation);
				//System.out.println("File Name " + fileName);
				//System.out.println("Url: " + url + " depth: " + depth + " isPrefix: " + isPrefix);
				
				request.setAttribute("results", ew);
				
				jspTarget = resultsPage;
			}catch(MalformedURLException e)
			{
				jspTarget = initialPage;
				request.setAttribute("error", e.getMessage());
			}catch(NumberFormatException e)
			{
				jspTarget = initialPage;
				request.setAttribute("error", e.getMessage());
			}catch(UnsupportedEncodingException e)
			{
				jspTarget = initialPage;
				request.setAttribute("error", e.getMessage());
			}catch (JAXBException e) {
				jspTarget = initialPage;
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
			}catch(IOException e)
			{
				jspTarget = initialPage;
				e.printStackTrace();
				request.setAttribute("error", e.getMessage());
			}
			catch(Exception e)
			{
				jspTarget = initialPage;
				request.setAttribute("error", "Unknown error");
				e.printStackTrace();
			}

		}
		
		request.getRequestDispatcher(jspTarget).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
