package issues;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import model.WebPage;

//@XmlSeeAlso({Error.class, LikelyProblem.class, PotentialProblem.class})
@XmlType (propOrder = {"type","description","guideline","webpages"})
public abstract class Issue 
{
	protected String type;
	//protected String cause;
	protected String guideline;
	protected String description;
	//@XmlElement
	protected List<WebPage> webpages;
	
	public void setWebpages(List<WebPage> webpages) {
		this.webpages = webpages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public void addWebpage(WebPage w)
	{
		this.webpages.add(w);
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/*
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}*/
	public String getGuideline() {
		return guideline;
	}
	public void setGuideline(String guideline) {
		this.guideline = guideline;
	}
	public List<WebPage> getWebpages() {
		return webpages;
	}
	public void setWebsites(List<WebPage> websites) {
		this.webpages = websites;
	} 
}
