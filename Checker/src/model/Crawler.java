package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/**
 * Web crawler to collect all links starting for a given URL 
 *
 */
public class Crawler
{
	/**
	 * Creates a web crawler
	 */
	public Crawler()
	{
		
	}
	/**
	 * 
	 * @param url The starting URL
	 * @param isPrefix If true only links starting with the url will be returned
	 * @param depth The level of links to follow
	 * @return A set of strings containing all links including the base url up to the specified depth
	 */
	public String[] retrieveLinks(String url, boolean isPrefix, int depth)
	{
		Set<String> links = new HashSet<String>();
		Map<Integer, Set<String>> toSearch = new LinkedHashMap<Integer,Set<String>>();
		
		//Initialize depth 0 (Initial link)
		Set<String> init = new HashSet<String>();
		init.add(url);
		toSearch.put(0, init);
		
		//Initialize links set to the passed URL
		links.add(url);
		
		if(depth == 0) //If depth is 0 return the passed URL for testing
		{
			links.addAll(toSearch.get(0));
		}else //Depth greater than 0; collect links
		{
			if(isPrefix)
			{
				for(int i = 0; i < depth; i++)
				{
					Set<String> search = toSearch.get(i);
					if(search != null)
					{
						for(String s: search)
						{
							Set<String> temp = retrievePrefixLinks(s);
							Set<String> next= toSearch.get(i+1);
							if(next == null)
							{
								next = new HashSet<String>(temp);
								
								toSearch.put(i+1,next);
							}else
							{
								next.addAll(temp);
							}
							
							//Remove links already traversed from the next list
							next.removeAll(links);
							links.addAll(next);
							//System.out.println(next);
 						}
					}
				}
			}else
			{
				for(int i = 0; i < depth; i++)
				{
					Set<String> search = toSearch.get(i);
					if(search != null)
					{
						for(String s:search)
						{
							Set<String> temp = retrieveNonPrefixLinks(s);
							Set<String>next = toSearch.get(i+1);
							if(next == null)
							{
								next = new HashSet<String>(temp);
								
								
								toSearch.put(i+1, next);
							}else
							{
								next.addAll(temp);
							}
							//Remove links already traversed from the next list
							next.removeAll(links);
							links.addAll(next);
						}
					}
				}
			}
		}
		
		//System.out.println(links);
		
		return links.toArray(new String[links.size()]);
		//return links;
	}
	/**
	 * Returns the list of links matching the prefix of the URL
	 * @param url The starting web page and prefix for all links 
	 * @return A set of strings containing all links found matching the specified prefix
	 */
	private Set<String> retrievePrefixLinks(String url)
	{
		Set<String> foundLinks = new HashSet<String>();
		
		Set<String> allLinks = getAllLinks(url);
		
		//Traverse links found; ensure they match the prefix
		for(String s:allLinks)
		{
			if(s.startsWith(url))
			{
				foundLinks.add(s);
			}
		}	
		
		return foundLinks;
	}
	/**
	 * Return a set of links present in the passed web page, all links must be in the TLD 
	 * @param url The web page to search
	 * @return A set of string containing all links found as long as they are part of the TLD
	 */
	private Set<String> retrieveNonPrefixLinks(String url)
	{
		Set<String> foundLinks = new HashSet<String>();
		String TLD = getTLD(url);
		
		Set<String> allLinks = getAllLinks(url);
		
		//Traverse all links found; check if they are in the TLD
		for(String s: allLinks)
		{
			if(s.contains(TLD))
			{
				foundLinks.add(s);
			//	System.out.println(s);
			}
		}
		
		return foundLinks;
	}
	/**
	 * Finds and returns all links on the web page at the provided URL
	 * @param url The URL to search
	 * @return A set containing all links on the passed web page URL. Links to documents and images are not included. 
	 */
	private Set<String> getAllLinks(String url)
	{
		Set<String> allLinks = new HashSet<String>();
		
		Document doc;
		try 
		{
			doc=Jsoup.connect(url).timeout(0).userAgent("Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/36.0").get();
			
			//System.out.println(doc);
			
			//Get links in the document
			Elements links = doc.select("a[href]");
			for(Element e:links)
			{	
				String link = e.attr("abs:href");
				//System.out.println(link);
				
				//Filter for pdfs,images,documents
				if(!link.contains(".jpg") && !link.contains(".png") && !link.contains(".pdf") && !link.contains(".doc") && !link.contains(".docx") && !link.contains(".ppt"))
				{
					allLinks.add(link);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allLinks;
	}
	/**
	 * Returns the top level domain (TLD) of a url
	 * @param url The url to extract the TLD from
	 * @return A String containing the TLD (Eg. www.abc.efg.ca would return efg.ca)
	 */
	private String getTLD(String url)
	{
		String TLD = null;
		
		try 
		{
			URL domain =  new URL(url);
			String host = domain.getHost();
			
			String[] urlComponents = host.split("\\.");
			
			//Find the top level domain
			TLD = urlComponents[urlComponents.length-2] + "." + urlComponents[urlComponents.length-1];
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TLD;
	}
}
