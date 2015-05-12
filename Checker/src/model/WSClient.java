package model;
import issues.Issue;
import issues.LikelyProblem;
import issues.PotentialProblem;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

//Potential Problems, Likely problems, Errors

public class WSClient {

	private final String id = "9a102ceff72bd0fa09feb2ae900a748951fbb7fc";
	private final String output = "rest";
	private final String guide = "WCAG2-AA";
	private final String checker = "http://achecker.ca/checkacc.php";
	private URLEncoder encoder;
	private final int timeout = 0;
	private String potentialProblem = "Potential Problem";
	private String error = "Error";
	private String knownProblem = "Likely Problem";
	
	
	public WSClient()
	{
	}
	
	public Map<Integer,Issue> getIssues(String[] url) throws UnsupportedEncodingException
	{
		Map<Integer,Issue> toReturn = new LinkedHashMap<Integer,Issue>();
		
		String toSend;
		String eUri;
		
		for(String uri:url)
		{
			try 
			{
				eUri = URLEncoder.encode(uri, "UTF-8");
				
				toSend = checker + "?uri="+eUri+"&id="+id+"&output="+output+"&guide="+guide;
				
				Document doc = Jsoup.connect(toSend).timeout(timeout).get();
				Document xmlDoc = Jsoup.parse(doc.toString(),"",Parser.xmlParser());
				
				Elements resultset = xmlDoc.select("resultSet");
				
				//Check if there was an error
				Elements errors = xmlDoc.select("errors");
				System.out.println("Num Errors " + errors.size());
				if(errors.size() > 0)
				{
					//Some sort of error occured
					//To Fill in later
				}else
				{
					Elements results = resultset.select("result");
					for(Element result:results)
					{
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
						
						/*
						
						System.out.println("type: " + type);
						System.out.println("linNum: " + lineNum);
						System.out.println("colNum: " + colNum);
						System.out.println("errMsg: " + errMsg);
						System.out.println("cause " + cause);
						System.out.println("id: " + id);
						
						*/
						
						//Get supplementary page for more error description
						String supPage = errMsg.substring(errMsg.indexOf("http"),errMsg.indexOf("\" ")).trim();
						//System.out.println("Sup Page: " + supPage);
						
						errMsg = Jsoup.parse(errMsg).text();
						//System.out.println("New error msg " + errMsg);
						
						//System.out.println();
						
						WebPage wp = new WebPage(uri,lineNum,colNum,cause);
						
						
						if(toReturn.containsKey(id))
						{
							Issue i = toReturn.get(id);
							i.addWebpage(wp);
						}else
						{
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
								
								recommendation = parse.getRecommendation();
								guideline = parse.getGuideLine();
								description = parse.getErrorMsg();
								
								Issue i = new issues.Error(type, repair, guideline, description);
								i.addWebpage(wp);
								toReturn.put(id, i);
								
							}else if(type.equals(knownProblem))
							{
								//Result is a known problem
								recommendation = parse.getRecommendation();
								guideline = parse.getGuideLine();
								description = parse.getErrorMsg();
								
								/*
								System.out.println("Type: " + type);
								System.out.println("Causing code " + cause);
								System.out.println("Recommendation " + recommendation);
								System.out.println("Guideline " + guideline);
								System.out.println("Issue Description " + description);
								System.out.println();
								*/
								
								Issue i = new LikelyProblem(type,recommendation, guideline, description);
								i.addWebpage(wp);
								toReturn.put(id,i);
							}
						}
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				throw new UnsupportedEncodingException("URL encoding not support, please contact the developer");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(toReturn.size());
		
		return toReturn;
	}
}
