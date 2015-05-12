package model;

import java.net.MalformedURLException;

import org.apache.commons.validator.routines.UrlValidator;

public class InputValidator 
{
	public InputValidator()
	{
		
	}
	/**
	 * Validates a url profided by the client
	 * @param url The url to validate
	 * @return True if the url is valid; otherwise false
	 */
	public void validateURL(String url) throws MalformedURLException
	{
		UrlValidator validator = new UrlValidator();
		if(!validator.isValid(url))
		{
			MalformedURLException e = new MalformedURLException("Invalid URL: Please enter a valid URL in the form of http/https//url.domain/...");
			throw e;
		}
	}
	/**
	 * Validates the depth entered by a user is an integer. The check ensures that the value is an integer and is equal to or greater than 0
	 * @param depth The depth entered by the user
	 * @return True if depth is a valid integer; false otherwise
	 */
	public void validateDepth(String depth) throws NumberFormatException
	{
		int d;
		try
		{
			d = Integer.parseInt(depth);
		}catch (NumberFormatException e)
		{
			throw new NumberFormatException("Depth entered must be an integer value");
		}
		
		if(d < 0)
		{
			throw new NumberFormatException("Depth must be 0 or greater");
		}

	}
}
