package issues;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import model.WebPage;

@XmlRootElement
public class PotentialProblem extends Issue
{
	
	protected String recommendation;
	
	/*
	 * Required for xml binding
	 */
	public PotentialProblem()
	{
		
	}
	
	public PotentialProblem(String type, String recommendation, String guideline, String description)
	{
		this.type = type;
		//this.cause = cause;
		this.recommendation = recommendation;
		this.guideline = guideline;
		this.description = description;
		this.webpages = new ArrayList<WebPage>();
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	
}
