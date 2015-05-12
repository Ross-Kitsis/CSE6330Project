package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;

import issues.Issue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLCreator 
{
	public XMLCreator()
	{
		
	}
	
	public void createXML(String location, errorWrapper ew) throws JAXBException, IOException
	{
		//JAXBContext jc =JAXBContext.newInstance(errorWrapper.class);
		JAXBContext jc =JAXBContext.newInstance(errorWrapper.class, issues.LikelyProblem.class, issues.Error.class, issues.PotentialProblem.class, model.WebPage.class);
		
		System.out.println("Location: " + location);
		
		OutputStream os = null; 
		
		try{
		//Create XML file
		File f = new File(location);
		f.createNewFile();
		
		os = new FileOutputStream(f);
		
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
