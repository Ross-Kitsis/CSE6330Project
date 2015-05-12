package issues;

import java.util.List;
import javax.xml.bind.annotation.XmlType;

import model.WebPage;

/**
 * An abstract class representing an issue encountered on a wepage
 * This class defined the common elements of an issue. Specialization is then done by 
 * classes extending this class.
 *
 */
@XmlType (propOrder = {"type","description","guideline","webpages"})
public abstract class Issue 
{
	/*
	 * Defines the 4 common elements of an issue. 
	 * Issues are defined by a type {Error, Potential Problem, Likely Problem}, the guideline they violate, a desciption of the issue 
	 * and a list of webpages affected by the issue. 
	 */
	protected String type;
	protected String guideline;
	protected String description;
	protected List<WebPage> webpages;
	
	/**
	 * Sets the list of webpages
	 */
	public void setWebpages(List<WebPage> webpages) {
		this.webpages = webpages;
	}
	/**
	 * Gets the description of the issue (Required for XML marshalling)
	 * @return A string containing the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description of an issue
	 * @param description The description to assign to the issue
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Adds a webpage object to the list of webpages
	 * @param w The webpage object to add
	 */
	public void addWebpage(WebPage w)
	{
		this.webpages.add(w);
	}
	/**
	 * Returns the type of the issue (Required for XML marshalling)
	 * @return A string object containing the type of the issue
	 */
	public String getType() {
		return type;
	}
	/**
	 * Sets the type of the issue 
	 * @param type A string containing the type of the object
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Returns the guideline of the issue (Required for XML marshalling)
	 * @return
	 */
	public String getGuideline() {
		return guideline;
	}
	/**
	 * Sets the guideline 
	 * @param guideline A string representing the guideline
	 */
	public void setGuideline(String guideline) {
		this.guideline = guideline;
	}
	/**
	 * Returns a list of webpage objects contained by the issue
	 * @return A List of webpage objects
	 */
	public List<WebPage> getWebpages() {
		return webpages;
	}
}
