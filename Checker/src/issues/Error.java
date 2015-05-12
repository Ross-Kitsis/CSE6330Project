package issues;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import model.WebPage;

/**
 * The error class represents an issue which has been identified as an error.
 * The error class extends the Issue class and builds of the functionality defined in the Issue class
 *
 */
@XmlRootElement
public class Error extends Issue
{
	
	private String repair;
	
	/**
	 * Default constructor for an Error object (Required for XML marshalling)
	 */
	public Error()
	{
		
	}
	/**
	 * Constructs a new Error object
	 * @param type The type of issue (Error in current cases)
	 * @param repair The suggested repair strategy
	 * @param guideline The guideline violated
	 * @param description The description of the error
	 */
	public Error(String type, String repair, String guideline, String description)
	{
		this.type = type;
		this.repair = repair;
		this.guideline = guideline;
		this.description = description;
		this.webpages = new ArrayList<WebPage>();
	}
	/**
	 * Returns the stored repair suggestion for the issue
	 * @return The suggested repair
	 */
	public String getRepair() {
		return repair;
	}
	/**
	 * Sets the suggested repair action
	 * @param repair The repair action
	 */
	public void setRepair(String repair) {
		this.repair = repair;
	}
	
}
