package xml;

import issues.Issue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * The wrapper class encapsulates the map of issues and 
 *
 */
@XmlRootElement(name="collectedData")
@XmlType (propOrder = {"numError","numPotentialProblem","numLikelyProblem","map"})
@XmlAccessorType(XmlAccessType.FIELD)
public class errorWrapper 
{
	@XmlElement(name="Issues")
	private Map<Integer,Issue> map = new LinkedHashMap<Integer,Issue>();
	@XmlElement(name="Num_Distinct_Errors")
	private int numError = 0;
	@XmlElement(name="Num_Distinct_Potential_Problems")
	private int numPotentialProblem = 0;
	@XmlElement(name="Num_Distinct_Likely_Problems")
	private int numLikelyProblem = 0;
	
	/*
	 * Default constructor needed for JAXB
	 */
	public errorWrapper()
	{
		
	}
	
	public void calcStats()
	{
		Set<Integer> keys = map.keySet();
		for(int i:keys)
		{
			if(map.get(i) instanceof issues.Error)
			{
				this.numError++;
			}else if(map.get(i) instanceof issues.LikelyProblem)
			{
				this.numLikelyProblem++;
			}else if(map.get(i) instanceof issues.PotentialProblem)
			{
				this.numPotentialProblem++;
			}
		}
	}
	
	public int getNumError() {
		return numError;
	}

	public void setNumError(int numError) {
		this.numError = numError;
	}

	public int getNumPotentialProblem() {
		return numPotentialProblem;
	}

	public void setNumPotentialProblem(int numPotentialProblem) {
		this.numPotentialProblem = numPotentialProblem;
	}

	public int getNumLikelyProblem() {
		return numLikelyProblem;
	}

	public void setNumLikelyProblem(int numLikelyProbelm) {
		this.numLikelyProblem = numLikelyProbelm;
	}

	public Map<Integer,Issue> getMap()
	{
		return this.map;
	}
	
	public void setMap(Map<Integer,Issue> map)
	{
		this.map = map;
	}
}
