package model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebParser 
{
	private String url;
	private String guideLine;
	private String recommendation;
	private String errorMsg;
	
	public WebParser(String url) throws IOException
	{
		this.url = url;
		Document doc = Jsoup.connect(url).timeout(0).get();
		getDescriptionFromDoc(doc);
	}
	
	public String getGuideLine() {
		return guideLine;
	}

	public void setGuideLine(String guideLine) {
		this.guideLine = guideLine;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	private String getDescriptionFromDoc(Document doc)
	{
		String toReturn = "";
		
		//Elements h2 = doc.select("ul");
		//Elements li = h2.get(1).select("li");
		
		Elements lists = doc.select("li");
		//System.out.println(lists);
		
		Element li = null;
		for(Element e:lists)
		{
			if(e.toString().contains("WCAG 2.0 (Level AA"))
			{
				li = e;
				break;
			}
		}
		
		//System.out.println(li);
		
		Element guideline = li.select("span").get(0);
		
		String[] temp = guideline.text().split(":");
		this.recommendation = temp[1].trim();
		
		Element e = li.select("span").get(1);
		this.guideLine = e.text().trim();
		
		//System.out.println(recommendation);
		//System.out.println(guideLine);
		
		this.getDes(doc.toString());
		
		return toReturn;
	}
	private void getDes(String html)
	{
		String[] lines = html.split("\n");
		for(int i = 0; i < lines.length; i++)
		{
			if(lines[i].contains("Short Description"))
			{
				String toParse = lines[i+1];
				errorMsg = toParse.substring(toParse.indexOf(">") + 1,toParse.lastIndexOf("</"));
				errorMsg = Jsoup.parse(errorMsg).text();
			//	System.out.println(errorMsg);
			}
		}
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
