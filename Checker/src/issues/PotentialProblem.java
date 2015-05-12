package issues;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

import model.WebPage;

/**
 * The error class represents an issue which has been identified as an error.
 * The error class extends the Issue class and builds of the functionality defined in the Issue class
 *
 */
@XmlRootElement
public class PotentialProblem extends Issue
{
	
	protected String recommendation;
	
	/**
	 * Default constructor required for xml binding
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
