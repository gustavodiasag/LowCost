package model;

public class User {

	private int id, contributions;
	private String name, login, password, email;
	
	public User() {
		
		id = -1;
		name = "";
		login = "";
		password = "";
		email = "";
		contributions = 0;
	}
	
	public User (int id, String name, String login, String password, String  email, int contributions) {
		
		setId(id);
		setName(name);
		setLogin(login);
		setPassword(password);
		setEmail(email);
		setContributions(contributions);
	}
	
	public User (String name, String login, String password, String  email, int contributions) {
		
		setName(name);
		setLogin(login);
		setPassword(password);
		setEmail(email);
		setContributions(contributions);
	}
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setLogin(String login) { this.login = login; }
	public void setPassword(String password) { this.password = password; }
	public void setEmail(String email) { this.email = email; }
	public void setContributions(int contributions) { this.contributions = contributions; }
	
	public int getId() { return this.id; }
	public String getName() { return this.name; }
	public String getLogin() { return this.login; }
	public String getPassword() { return this.password; }
	public String getEmail() { return this.email; }
	public int getContributions() { return this.contributions; }

	@Override
	public String toString() {
		
		return "Name: " + name + ", Login: " + login + ", email: " + email + ", Contributions: "
			   + contributions;
	}
}
