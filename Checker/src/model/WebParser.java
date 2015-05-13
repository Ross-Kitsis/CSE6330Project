package model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * This class parses the webpage provided by AChecker providing further detail on errors found
 * The webpage is only provided in HTML format; thus this class parses the HTML provided.
 * This class extracts the guideline, recommendation and error message of the issue
 *
 */
public class WebParser 
{
	private String url;
	private String guideLine;
	private String recommendation;
	private String errorMsg;
	/**
	 * Constructs a new WebParser object which connects and gets the webpage to parse 
	 * @param url The url of the webpage to parse
	 * @throws IOException
	 */
	public WebParser(String url) throws IOException
	{
		this.url = url;
		Document doc = Jsoup.connect(url).timeout(0).get();
		getDescriptionFromDoc(doc);
	}
	/**
	 * Returns the guideline from the parsed document
	 * @return The stored guideline
	 */
	public String getGuideLine() {
		return guideLine;
	}
	/**
	 * Sets the guideline
	 * @param guideLine A string containing the guideline
	 */
	public void setGuideLine(String guideLine) {
		this.guideLine = guideLine;
	}
	/**
	 * Returns the recommendation parsed from the webpage
	 * @return The stored recommendation
	 */
	public String getRecommendation() {
		return recommendation;
	}
	/**
	 * Sets the recommendation 
	 * @param recommendation The recommendation to set
	 */
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	/*
	 * Parses the document to retrieve the description of the error
	 * Also sets the recommendation as it is contained in the same area
	 */
	private String getDescriptionFromDoc(Document doc)
	{
		String toReturn = "";
		
		Elements lists = doc.select("li");
		
		//Find the WCAG 2.0 AA parameters
		Element li = null;
		for(Element e:lists)
		{
			if(e.toString().contains("WCAG 2.0 (Level AA"))
			{
				li = e;
				break;
			}
		}
		
		
		Element guideline = li.select("span").get(0);
		
		//Set the recommendation
		String[] temp = guideline.text().split(":");
		this.recommendation = temp[1].trim();
		
		//Set the guideline
		Element e = li.select("span").get(1);
		this.guideLine = e.text().trim();
		
		this.getDes(doc.toString());
		
		return toReturn;
	}
	/*
	 * Gets the description of the error and stores it
	 */
	private void getDes(String html)
	{
		String[] lines = html.split("\n");
		for(int i = 0; i < lines.length; i++)
		{
			//Parse document until the description is found
			if(lines[i].contains("Short Description"))
			{
				String toParse = lines[i+1];
				errorMsg = toParse.substring(toParse.indexOf(">") + 1,toParse.lastIndexOf("</"));
				errorMsg = Jsoup.parse(errorMsg).text();
			}
		}
	}
	/**
	 * Returns the error message found in the parsed document
	 * @return The error message
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * Sets the error message
	 * @param errorMsg The error message
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
