package phishing;

/**
 * Author: Rayne Aurit
 * Date: 2025-03-01
 * 
 * Object to represent an email with fields username, domain, body, and if it is a phishing email
 */

public class Email {
	private String username;
	private String domain;
	private String body;
	private boolean isPhishy;
	
	public Email(String username, String domain, String body, boolean isPhishy) {
		this.username = username;
		this.domain = domain;
		this.body = body;
		this.isPhishy = isPhishy;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public String getBody() {
		return body;
	}
	
	public boolean getIsPhishy() {
		return isPhishy;
	}
	
	public String toString() {
		return "From: " + this.username + "@" + this.domain + "\nBody: " + this.body;
	}
}
