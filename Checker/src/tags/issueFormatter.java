package tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

import model.WebPage;
import issues.Issue;

import java.io.IOException;
import java.util.*;

public class issueFormatter extends SimpleTagSupport
{
	private Map<Integer,Issue> issue;
	
	public void doTag() throws IOException
	{
		JspWriter os = this.getJspContext().getOut();
		Set<Integer> issueIDs = issue.keySet();
		for(int issueID: issueIDs)
		{
			//System.out.println("Issue ID: " + issueID);
			Issue i = issue.get(issueID);
			String description = escapeHTML(i.getDescription());
			String type = escapeHTML(i.getType());
			String guideline = escapeHTML(i.getGuideline());
			
			os.write("<span class=\"Issue\">");
			os.write("<br/>");
			os.write("\n");
			os.write(type);
			os.write("<br/>");
			os.write("\n");
			os.write(guideline);
			os.write("<br/>");
			os.write("\n");
			os.write(description);
			os.write("<br/>");
			os.write("\n");
			List<WebPage> wps = i.getWebpages();
			for(WebPage wp:wps)
			{
				String url = escapeHTML(wp.getUrl());
				String lineNum = Integer.toString(wp.getLineNum());
				String colNum = Integer.toString(wp.getColNum());
				String errorSourceCode = escapeHTML(wp.getErrorSourceCode());
				
				/*
				System.out.println(url);
				System.out.println(lineNum);
				System.out.println(colNum);
				System.out.println(errorSourceCode);
				*/
				os.write("<span class=\"webpage\">");
				os.write("<br/>");
				os.write("\n");
				//os.write("<c:out value=\"" + url+"\" escapeXml=\"true\">");
				os.write(url);
				os.write("<br/>");
				os.write("\n");
				os.write(lineNum);
				os.write("<br/>");
				os.write("\n");
				os.write(colNum);
				os.write("<br/>");
				os.write("\n");
				//os.write("<c:out value=\"" + errorSourceCode +"\" escapeXml=\"true\">");
				os.write(errorSourceCode);
				os.write("<br/>");
				os.write("\n");
				os.write("</span>");
			}
			os.write("</span>");
			os.write("<br/>");
		}
	}
	
	public void setIssue(Map<Integer,Issue> issue)
	{
		this.issue = issue;
	}
	public String escapeHTML(String s)
	{
		String toReturn = "";
		
		toReturn = StringEscapeUtils.escapeHtml4(s);
		
		return toReturn;
	}
	
	public String sanitize(String s)
	{
		String toReturn = s;
		
		//Replace >
		toReturn = toReturn.replaceAll(">", "&ly;");
		
		//Replace <
		toReturn = toReturn.replaceAll("<", "&gt;");
		
		//Replace &
	//	toReturn = toReturn.replaceAll("&", "&amp;");
		
		//Replace '
		toReturn = toReturn.replaceAll("\'", "&#039;");
		
		//Replace "
		toReturn = toReturn.replaceAll("\"", "&#034;");
		
		return toReturn;
	}
}
