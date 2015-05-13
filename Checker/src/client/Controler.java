package client;

import issues.Error;
import issues.Issue;
import issues.LikelyProblem;
import issues.PotentialProblem;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
 * The main controller of the web accessibility checker
 * The main controller handles all client requests, delegates to the model to handle business logic and finally 
 * responds to the client. 
 * 
 */
@WebServlet("/Controler")
public class Controler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controler() 
    {
        super();        
    }
    /**
     * Initialize the server, instantiate all model and xml components to optimize server performance during runtime
     */
    public void init() throws ServletException
    {
        this.getServletContext().setAttribute("crawler", new Crawler());
        this.getServletContext().setAttribute("wsclient", new WSClient());
        this.getServletContext().setAttribute("xmlCreator", new XMLCreator());
        this.getServletContext().setAttribute("validator", new InputValidator());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputValidator validate = (InputValidator) this.getServletContext().getAttribute("validator");
		
		/*
		 * List of target JSP pages the controller may send clients to
		 */
		String jspTarget = "";
		String initialPage = "/start.jspx";
		String resultsPage = "/Results.jspx";
		
		/*
		 * Examine the parameters in the request, if submit is set, the user must have pressed the submit button; validate their inputs
		 * If no submit then assume fresh visit and send user to the landing page
		 */
		if(request.getParameter("submit") == null)
		{
			jspTarget = initialPage;
		}else
		{
			try{
				
				/*
				 * Extract parameters expected from the form
				 */
				String url = request.getParameter("url");
				String depth = request.getParameter("depth");
				String isPrefix = request.getParameter("isPrefix");
				
				/*
				 * Set extracted parameters into the request scope to allow for "back" and "redo" functionality
				 */
				request.setAttribute("url", url);
				request.setAttribute("depth", depth);
				request.setAttribute("isPrefix", isPrefix);

				/*
				 * Validate the URL and depth supplied by the user
				 */
				validate.validateURL(url);
				validate.validateDepth(depth);
				
				/*
				 * Input validation has succeeded, cast parameters to their correct types for processing by the model
				 */
				int d = Integer.parseInt(depth);
				boolean p = (request.getParameter("isPrefix") == null) ? false : true;

				Crawler c = (Crawler) this.getServletContext().getAttribute("crawler");
				WSClient ws = (WSClient) this.getServletContext().getAttribute("wsclient");
				
				/*
				 * Pass parameters to the web crawler to retrieve the set of links to evaluate
				 */
				String[] toEvaluate = c.retrieveLinks(url, p, d);
				
				/*
				 * For each webpage found; evaluate the webpage and place it into the results. 
				 * Results are formatted into a map where keys are a unique integer ID and values and an Issue object
				 */
				Map<Integer,Issue> issues = ws.getIssues(toEvaluate);

				/*
				 * Wrap the map of result for xml marshalling as well as results.jspx printing
				 * In addition to wrapping, calculate the statistics of the errors found (Number of each type of issue) 
				 */
				errorWrapper ew = new errorWrapper();
				ew.setMap(issues);
				ew.calcStats();
				
				/*
				 * Pass error wrapper object to model for JAXB marshalling to xml
				 */
				String xmlFileFolder = "/Results";
				String xmlFileLocation = this.getServletContext().getRealPath(xmlFileFolder); 
				String fileName = request.getSession().getId() + ".xml";
				
				//Location of XML File
				String location = xmlFileLocation + File.separator + fileName;
				
				XMLCreator xc = (XMLCreator) this.getServletContext().getAttribute("xmlCreator");
				xc.createXML(location, ew);
				
				Map<Integer,Error> errors = new TreeMap<Integer,Error>();
				Map<Integer,PotentialProblem> pp = new TreeMap<Integer,PotentialProblem>();
				Map<Integer,LikelyProblem> lp = new TreeMap<Integer,LikelyProblem>();
				
				Set<Integer> keys = issues.keySet();
				for(int key:keys)
				{
					Issue i = issues.get(key);
					if(i instanceof issues.Error)
					{
						errors.put(key, (Error) i);
					}else if(i instanceof issues.PotentialProblem)
					{
						pp.put(key, (PotentialProblem) i);
					}else if(i instanceof issues.LikelyProblem)
					{
						lp.put(key, (LikelyProblem) i);
					}
				}
				
				
				/*
				 * Add the error wrapper to the request scope to allow results.jspx to parse it
				 */
				request.setAttribute("results", ew);
				request.setAttribute("errors", errors);
				request.setAttribute("potential", pp);
				request.setAttribute("likely", lp);
				
				/*
				 * Set path to download xml containing results
				 */
				request.setAttribute("xmlPath", fileName);
				
				/*
				 * Set the results.jspx page as the page to forward the user to
				 */
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
		/*
		 * Forward user to the set page
		 */
		request.getRequestDispatcher(jspTarget).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
