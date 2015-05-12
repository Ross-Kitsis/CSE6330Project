package tags;

import issues.*;
import issues.Error;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import model.WebPage;

import org.apache.commons.lang3.StringEscapeUtils;

public class issueFormatterTable extends SimpleTagSupport
{
	private Map<Integer,Issue> issue;
	private Map<Integer,Error> errors = new TreeMap<Integer,Error>();
	private Map<Integer,PotentialProblem> pp = new TreeMap<Integer,PotentialProblem>();
	private Map<Integer,LikelyProblem> lp = new TreeMap<Integer,LikelyProblem>();
	
	
	public void doTag() throws IOException
	{
		JspWriter os = this.getJspContext().getOut();
		
		/*
		 * Sort the issues in their respective map to allow table formatting
		 */
		Set<Integer> keys = issue.keySet();
		for(int key:keys)
		{
			Issue i = issue.get(key);
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
		
		os.write("\n");
		os.write("<tr class=\"issue\">");
		os.write("\n");
		os.write("<td colspan=\"3\">Errors</td>");
		os.write("\n");
		os.write("<td>"+errors.size()+"</td>");
		os.write("\n");
		os.write("</tr>");
		
		Set<Integer> es = errors.keySet();
		for(int i:es)
		{
			Error e = errors.get(i);
			String type = escapeHTML(e.getType());
			String description = escapeHTML(e.getDescription());
			String guideline = escapeHTML(e.getGuideline());
			String repair = escapeHTML(e.getRepair());
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Issue ID</td>");
			os.write("\n");
			os.write("<td>"+i+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Guideline</td>");
			os.write("\n");
			os.write("<td>"+guideline+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Description</td>");
			os.write("\n");
			os.write("<td>"+description+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Repair</td>");
			os.write("\n");
			os.write("<td>"+repair+"</td>");
			os.write("</tr>");
			

			
			List<WebPage> wp = e.getWebpages();
			
			os.write("\n");
			os.write("<tr class=\"websites\">");
			os.write("\n");
			os.write("<td>Affected Webpages</td>");
			os.write("\n");
			os.write("<td>"+wp.size()+"</td>");
			os.write("\n");
			os.write("<\tr>");
			
			Iterator<WebPage> it = wp.iterator();
			while(it.hasNext())
			{
				WebPage w = it.next();
				
				String url = escapeHTML(w.getUrl());
				String lineNum = escapeHTML(Integer.toString(w.getLineNum()));
				String colNum = escapeHTML(Integer.toString(w.getColNum()));
				String errorSrcCode = escapeHTML(w.getErrorSourceCode());
				
				if(it.hasNext())
				{
					os.write("\n");
					os.write("<tr>");
					os.write("\n");
					os.write("<td>URL</td>");
					os.write("\n");
					os.write("<td>"+url+"</td>");
					os.write("\n");
					os.write("</tr>");
				}else
				{
					os.write("\n");
					os.write("<tr class=\"endwebsites\">");
					os.write("\n");
					os.write("<td>URL</td>");
					os.write("\n");
					os.write("<td>"+url+"</td>");
					os.write("\n");
					os.write("</tr>");
				}
			}
			
			
		}
		
		os.write("\n");
		os.write("<tr class=\"issue\">");
		os.write("\n");
		os.write("<td colspan=\"3\">Potential Problems</td>");
		os.write("\n");
		os.write("<td>"+pp.size()+"</td>");
		os.write("\n");
		os.write("</tr>");
		
		Set<Integer> pprobs = pp.keySet();
		for(int i:pprobs)
		{
			PotentialProblem p = pp.get(i);
			String type = escapeHTML(p.getType());
			String description = escapeHTML(p.getDescription());
			String guideline = escapeHTML(p.getGuideline());
			String rec = escapeHTML(p.getRecommendation());
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Issue ID</td>");
			os.write("\n");
			os.write("<td>"+i+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Guideline</td>");
			os.write("\n");
			os.write("<td>"+guideline+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Description</td>");
			os.write("\n");
			os.write("<td>"+description+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Recommendation</td>");
			os.write("\n");
			os.write("<td>"+lp.size()+"</td>");
			os.write("\n");
			os.write("<td>"+rec+"</td>");
			os.write("</tr>");
		}
		
		os.write("\n");
		os.write("<tr class=\"issue\">");
		os.write("\n");
		os.write("<td colspan=\"3\">Likely Problems</td>");
		os.write("\n");
		os.write("<td>"+lp.size()+"</td>");
		os.write("\n");
		os.write("</tr>");
		
		Set<Integer> lps = lp.keySet();
		for(int i:lps)
		{
			LikelyProblem l = lp.get(i);
			String type = escapeHTML(l.getType());
			String description = escapeHTML(l.getDescription());
			String guideline = escapeHTML(l.getGuideline());
			String rec = escapeHTML(l.getRecommendation());
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Issue ID</td>");
			os.write("\n");
			os.write("<td>"+i+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Guideline</td>");
			os.write("\n");
			os.write("<td>"+guideline+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Description</td>");
			os.write("\n");
			os.write("<td>"+description+"</td>");
			os.write("</tr>");
			
			os.write("\n");
			os.write("<tr>");
			os.write("\n");
			os.write("<td></td>");
			os.write("\n");
			os.write("<td>Recommendation</td>");
			os.write("\n");
			os.write("<td>"+rec+"</td>");
			os.write("</tr>");
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
}
