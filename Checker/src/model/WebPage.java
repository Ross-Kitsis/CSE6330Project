package model;

import javax.xml.bind.annotation.XmlType;

@XmlType (propOrder={"url","lineNum","colNum","errorSourceCode"})
public class WebPage
{
	
	private String url;
	private int lineNum;
	private int colNum;
	private String errorSourceCode;
	
	public WebPage(String url, int lineNum, int colNum, String errorSourceCode) 
	{
		this.url = url;
		this.lineNum = lineNum;
		this.colNum = colNum;
		this.errorSourceCode = errorSourceCode;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	public String getErrorSourceCode() {
		return errorSourceCode;
	}
	public void setErrorSourceCode(String errorSourceCode) {
		this.errorSourceCode = errorSourceCode;
	}
	
}
