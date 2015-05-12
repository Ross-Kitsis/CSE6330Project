package issues;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import model.WebPage;

@XmlRootElement
//@XmlType (propOrder = {"type","description","guideline","repair","webpages"})
public class Error extends Issue
{
	
	private String repair;
	
	/*
	 * Required for XML binding
	 */
	public Error()
	{
		
	}
	
	public Error(String type, String repair, String guideline, String description)
	{
		this.type = type;
		//this.cause = cause;
		this.repair = repair;
		this.guideline = guideline;
		this.description = description;
		this.webpages = new ArrayList<WebPage>();
	}

	public String getRepair() {
		return repair;
	}

	public void setRepair(String repair) {
		this.repair = repair;
	}
	
}
