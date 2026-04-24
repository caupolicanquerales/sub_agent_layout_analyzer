package com.capo.sub_agent_layout_analyzer.response;

public class BasicTemplateResponse {
	
	private String id;
	private String htmlString;
	private String cssString;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHtmlString() {
		return htmlString;
	}
	public void setHtmlString(String htmlString) {
		this.htmlString = htmlString;
	}
	public String getCssString() {
		return cssString;
	}
	public void setCssString(String cssString) {
		this.cssString = cssString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
