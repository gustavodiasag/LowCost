package model;

import java.time.LocalDateTime;

public class Comment {
	
	private String content;
	private LocalDateTime submission;
	private int serviceId, forumId, userId, companyId;
	private float sentiment;
	
	public Comment() {
		
		content = "";
		submission = null;
		serviceId = -1;
		forumId = -1;
		userId = -1;
		sentiment = -1;
		companyId = -1;
	}
	
	public Comment(String content, LocalDateTime submission, int serviceId, int forumId,
				   int userId, float sentiment, int companyId) {
		
		setContent(content);
		setSubmission(submission);
		setServiceId(serviceId);
		setForumId(forumId);
		setUserId(userId);
		setSentiment(sentiment);
		setCompanyId(companyId);
	}
	
	public void setContent(String content) { this.content = content; }
	public void setSubmission(LocalDateTime submission) { this.submission = submission; }
	public void setServiceId(int serviceId) { this.serviceId = serviceId; }
	public void setForumId(int forumId) { this.forumId = forumId; }
	public void setUserId(int userId) { this.userId = userId; }
	public void setSentiment(float sentiment) { this.sentiment = sentiment; }
	public void setCompanyId(int companyId) { this.companyId = companyId; }
	
	public String getContent() { return this.content; }
	public LocalDateTime getSubmission() { return this.submission; }
	public int getServiceId() { return this.serviceId; }
	public int getForumId() { return this.forumId; }
	public int getUserId() { return this.userId; }
	public float getSentiment() { return this.sentiment; }
	public int getCompanyId() { return this.companyId; }
	
	@Override
	public String toString() {
		
		return "Content: " + content + ", Submisison: " + submission;
	}
}	
