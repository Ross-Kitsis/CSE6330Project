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
 * The wrapper class encapsulates the map of issues and calculates basic statistics on the information obtained. The wrapper is used
 * by JAXB for XML marshalling
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
	
	/**
	 * Default constructor needed for JAXB
	 */
	public errorWrapper()
	{
		
	}
	/**
	 * Calculates the number of errors, potential problems and likely problems in the map contained in this object
	 */
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
	/**
	 * Returns the number of errors
	 * @return The number of errors in the contained map
	 */
	public int getNumError() {
		return numError;
	}
	/**
	 * Sets the number of errors
	 * @param numError The number of errors
	 */
	public void setNumError(int numError) {
		this.numError = numError;
	}
	/**
	 * Returns the number of potential problems
	 * @return The number of potential problems in the map of issues
	 */
	public int getNumPotentialProblem() {
		return numPotentialProblem;
	}
	/**
	 * Sets the number of potential problems
	 * @param numPotentialProblem The number of potential problems
	 */
	public void setNumPotentialProblem(int numPotentialProblem) {
		this.numPotentialProblem = numPotentialProblem;
	}
	/**
	 * Returns the number of likely problems
	 * @return The number of likely problems
	 */
	public int getNumLikelyProblem() {
		return numLikelyProblem;
	}
	/**
	 * Sets the number of likely problems
	 * @param numLikelyProblems The number of likely problems to assign
	 */
	public void setNumLikelyProblem(int numLikelyProblem) {
		this.numLikelyProblem = numLikelyProblem;
	}
	/**
	 * Returns the map of issues
	 * @return The map of issues contained in the wrapper
	 */
	public Map<Integer,Issue> getMap()
	{
		return this.map;
	}
	/**
	 * Sets the map of issues
	 * @param map The map of issues
	 */
	public void setMap(Map<Integer,Issue> map)
	{
		this.map = map;
	}
}
