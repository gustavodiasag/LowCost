package model;

public class Offer {
	
	private int companyId, serviceId;
	
	public Offer() { companyId = -1; serviceId = -1; }
	
	public Offer(int companyId, int serviceId) {
		
		setCompanyId(companyId);
		setServiceId(serviceId);
	}
	
	public void setCompanyId(int companyId) { this.companyId = companyId; }
	public void setServiceId(int serviceId) { this.serviceId = serviceId; }
	
	public int getCompanyId() { return this.companyId; }
	public int getServiceId() { return this.serviceId; }
	
	@Override
	public String toString() {
		
		return "Company ID: " + companyId + ", Service ID: " + serviceId;
	}
}
