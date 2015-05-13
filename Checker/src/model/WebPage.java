package model;

import javax.xml.bind.annotation.XmlType;
/**
 * A webpage object holding the details of an error on a webpage
 * Each webpage object holds the url, the line number of the error, the column number of the error
 * and the source code generating the error
 *
 */
@XmlType (propOrder={"url","lineNum","colNum","errorSourceCode"})
public class WebPage
{
	private String url;
	private int lineNum;
	private int colNum;
	private String errorSourceCode;
	
	/**
	 * Constructor creating a webpage object initialized with the url, line number, column number and error source code
	 * @param url
	 * @param lineNum
	 * @param colNum
	 * @param errorSourceCode
	 */
	public WebPage(String url, int lineNum, int colNum, String errorSourceCode) 
	{
		this.url = url;
		this.lineNum = lineNum;
		this.colNum = colNum;
		this.errorSourceCode = errorSourceCode;
	}
	/**
	 * Returns the a string with the URL
	 * @return A string with the URL
	 */
	public String getUrl()
	{
		return url;
	}
	/**
	 * Sets the URL of the webpage
	 * @param url The string to set to set the url to
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * Returns the line number of the error
	 * @return An integer with the line number of the error
	 */
	public int getLineNum() {
		return lineNum;
	}
	/**
	 * Sets the line number of the error
	 * @param lineNum The line number
	 */
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	/**
	 * Returns the column number of the error
	 * @return The column number
	 */
	public int getColNum() {
		return colNum;
	}
	/**
	 * Sets the column number of the error
	 * @param colNum The column number
	 */
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	/**
	 * Returns the error source code
	 * @return The error source code
	 */
	public String getErrorSourceCode() {
		return errorSourceCode;
	}
	/**
	 * Sets the error code
	 * @param errorSourceCode The error code to set
	 */
	public void setErrorSourceCode(String errorSourceCode) {
		this.errorSourceCode = errorSourceCode;
	}
	
}
