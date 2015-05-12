package issues;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

import model.WebPage;

/**
 * The Likely Problem class represents an issue identified as a likely problem.
 * The class extends the issue class and specializes to hold the fields in a likely problems
 *
 */
@XmlRootElement
public class LikelyProblem extends Issue
{
	
	protected String recommendation;
	
	/**
	 * Default constructor required for XML binding
	 */
	public LikelyProblem()
	{
		
	}
	/**
	 * Creates a new LikelyProblem object with the passed fields
	 * 
	 * @param type The type of issue (Likely Problem)
	 * @param recommendation The recommendation to correct the issue
	 * @param guideline The guideline violated
	 * @param description A description of the issue
	 */
	public LikelyProblem(String type, String recommendation, String guideline, String description)
	{
		this.type = type;
		this.recommendation = recommendation;
		this.guideline = guideline;
		this.description = description;
		this.webpages = new ArrayList<WebPage>();
	}
	/**
	 * Returns the recommendation
	 * @return A string containing the recommendation
	 */
	public String getRecommendation() {
		return recommendation;
	}
	/**
	 * Sets the recommendation
	 * @param recommendation A string containing the recommendation
	 */
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
}
