package model;

public class Forum {
	
	private int id, comments, userId;
	private String title;
	
	public Forum() {
		
		id = -1;
		comments = 0;
		userId = -1;
		title = "";
	}
	
	public Forum(int comments, int userId, String title) {
		
		setComments(comments);
		setUserId(userId);
		setTitle(title);
	}
	
	public void setId(int id) { this.id = id; }
	public void setComments(int comments) { this.comments = comments; }
	public void setUserId(int userId) { this.userId = userId; }
	public void setTitle(String title) { this.title = title; }
	
	public int getId() { return this.id; }
	public int getComments() { return this.comments; }
	public int getUserId() { return this.userId; }
	public String getTitle() { return this.title; }
	
	@Override
	public String toString() {
		
		return "Title: " + title + ", Comments: " + comments + ", User ID: " + userId; 
	}
}
