package model;

public class Service {
	
	private int id;
	private String description;
	private float price;
	
	public Service() {
		
		id = -1;
		description = "";
		price = -1;
	}
	
	public Service (String description, float price) {
		
		setDescription(description);
		setPrice(price);
	}
	
	public void setId(int id) { this.id = id; }
	public void setDescription(String description) { this.description = description; }
	public void setPrice(float price) { this.price = price; }
	
	public int getId() { return this.id; }
	public String getDescription() { return this.description; }
	public float getPrice() { return this.price; }
	
	@Override
	public String toString() {
		
		return "Description: " + description + ", Price: " + price;
	}
}
