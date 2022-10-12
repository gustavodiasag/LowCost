package model;

public class Company {
	
	private int id;
	private String name;
	
	public Company() { id = -1; name = ""; }
	
	public Company(String name) { setName(name); }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	
	public int getId() { return this.id; }
	public String getName() { return this.name; }
	
	@Override
	public String toString() { return "Name: " + name; }
}
