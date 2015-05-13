package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
/**
 * The XMLCreator creates the XML uses JAXB to marshal the data in the errorWrapper class into an XML file.
 * The XML file is then written to disk in the passed location. 
 */
public class XMLCreator 
{
	/**
	 * Constructs an XMLCreator
	 */
	public XMLCreator()
	{
		
	}
	/**
	 * Creates the XML file at the passed location with the data in the errorWrapper
	 * @param location The location where to create the XML file
	 * @param ew The error wrapper containing the data to write to the XML file
	 * @throws JAXBException Thrown if an error occurs during marshaling
	 * @throws IOException Thrown if an error occurs while writing the XML file to disk
	 */
	public void createXML(String location, errorWrapper ew) throws JAXBException, IOException
	{
		JAXBContext jc =JAXBContext.newInstance(errorWrapper.class, issues.LikelyProblem.class, issues.Error.class, issues.PotentialProblem.class, model.WebPage.class);
		
		System.out.println("Location: " + location);
		
		OutputStream os = null; 
		
		try{
		//Create XML file
		File f = new File(location);
		f.createNewFile();
		
		os = new FileOutputStream(f);
		
		//Marshal the data to XML file
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(ew, os);
		
		os.close();
		
		}catch(JAXBException e)
		{
			throw new JAXBException("Could not create XML");
		}catch(IOException e)
		{
			throw new IOException("Filesystem error; could not create new XML file");
		}
	}
}
