package model;
import issues.Issue;
import issues.LikelyProblem;
import issues.PotentialProblem;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 * The WSClient (Web Service Client) interfaces with AChecker, sends the webpages to evaluate, receives the results from AChecker,
 * and stores them in a data structure for further parsing. 
 *
 */
public class WSClient {

	private final String id = "9a102ceff72bd0fa09feb2ae900a748951fbb7fc"; //Unique ID required to access AChecker web API
	private final String output = "rest"; //Output type (rest or html)
	private final String guide = "WCAG2-AA"; //Evaluation guide (Currently set to WCAG 2.0 AA)
	private final String checker = "http://achecker.ca/checkacc.php"; //Url of AChecker
	private final int timeout = 0; //Timeout value before abandoning request (0 is no timeout)
	//Types of problems encountered
	private String potentialProblem = "Potential Problem";
	private String error = "Error";
	private String knownProblem = "Likely Problem";
	
	/**
	 * Constructs a web service client object with default values
	 */
	public WSClient()
	{
	}
	/**
	 * Sends each url provided to achecker, parses the results and places them into a map
	 * @param url An array of strings containing the URLs to send to AChecker for evaluation
	 * @return A map containing the parsed issues. Keys of the map are unique identifies for each issue; values are a child object of the Issue
	 * class based on the type of issue (Error, Potential Problem or Likely Problem)
	 * @throws UnsupportedEncodingException
	 */
	public Map<Integer,Issue> getIssues(String[] url) throws UnsupportedEncodingException
	{
		Map<Integer,Issue> toReturn = new LinkedHashMap<Integer,Issue>();
		
		String toSend;
		String eUri;
		
		for(String uri:url)
		{
			try 
			{
				//Encode the url (Remove special characters in the url) to all AChecker to correctly read it
				eUri = URLEncoder.encode(uri, "UTF-8");
				
				//Build the query string to send to AChecker
				toSend = checker + "?uri="+eUri+"&id="+id+"&output="+output+"&guide="+guide;
				
				//Send the query to AChecker
				Document doc = Jsoup.connect(toSend).timeout(timeout).get();
				
				//Prepare the rest document returned for parsing
				Document xmlDoc = Jsoup.parse(doc.toString(),"",Parser.xmlParser());
				
				//Retrieve the results
				Elements resultset = xmlDoc.select("resultSet");
				
				//Check if there was an error
				Elements errors = xmlDoc.select("errors");
				System.out.println("Num Errors " + errors.size());
				if(errors.size() > 0)
				{
					//Achecker encountered an error, move onto the next webpage
				}else
				{
					//Parse each result
					Elements results = resultset.select("result");
					for(Element result:results)
					{
						//Collect values to store in the data structure
						String type = result.select("resultType").get(0).text().trim(); //Type of error
						int lineNum = Integer.parseInt(result.select("lineNum").get(0).text().trim()); //line number
						int colNum = Integer.parseInt(result.select("columnNum").get(0).text().trim()); //Col number
						String errMsg = result.select("errorMsg").get(0).text().trim(); //Error msg
						String cause = result.select("errorSourceCode").get(0).text().trim(); //Cause
						
						/*
						 * Sanitize cause to remove any tags or HTML
						 */
						cause = StringEscapeUtils.escapeHtml4(cause);
						
						//Extract ID from errMsg to get ID
						int id = Integer.parseInt(errMsg.substring(errMsg.indexOf("?id") + 4,errMsg.indexOf("\" ")));
						
						//Get supplementary page for more error description
						String supPage = errMsg.substring(errMsg.indexOf("http"),errMsg.indexOf("\" ")).trim();
						
						//Extract the error message
						errMsg = Jsoup.parse(errMsg).text();
						
						//Create a new webpage object to store webpage specific details
						WebPage wp = new WebPage(uri,lineNum,colNum,cause);
						
						//Place extracte4d values in the map
						if(toReturn.containsKey(id))
						{
							//Issue already encountered, add webpage to the list of webpages effected by the issue
							Issue i = toReturn.get(id);
							i.addWebpage(wp);
						}else
						{
							//Issue has not been encountered before, create a new issue object and add the webpage to its
							String recommendation = "";
							String guideline = "";
							String description = "";
							
							WebParser parse = new WebParser(supPage);
							
							recommendation = parse.getRecommendation();
							guideline = parse.getGuideLine();
							description = parse.getErrorMsg();
							
							if(type.equals(potentialProblem))
							{
								//Result is a potential problem		
								Issue i = new PotentialProblem(type,recommendation, guideline, description);
								i.addWebpage(wp);
								toReturn.put(id,i);
							}else if(type.equals(error))
							{
								//Result is an error
								String repair = Jsoup.parse(result.select("repair").get(0).text().trim()).text(); //Type of error
								
								/*
								recommendation = parse.getRecommendation();
								guideline = parse.getGuideLine();
								description = parse.getErrorMsg();
								*/
								
								Issue i = new issues.Error(type, repair, guideline, description);
								i.addWebpage(wp);
								toReturn.put(id, i);
								
							}else if(type.equals(knownProblem))
							{
								//Result is a known problem
								/*
								recommendation = parse.getRecommendation();
								guideline = parse.getGuideLine();
								description = parse.getErrorMsg();*/
								
								Issue i = new LikelyProblem(type,recommendation, guideline, description);
								i.addWebpage(wp);
								toReturn.put(id,i);
							}
						}
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new UnsupportedEncodingException("URL encoding not support, please contact the developer");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
		return toReturn;
	}
}
