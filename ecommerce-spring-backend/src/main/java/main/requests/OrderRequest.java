package main.requests;

import jakarta.validation.constraints.Pattern;
import main.entity.Adress;

public class OrderRequest {

	
    @Pattern(regexp = "^[0-9]+$", message = "Only numbers are allowed")
	private String mobileNumber;
	private Adress adres;
	private String firstName;
	private String lastName;
	private int id;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Adress getAdres() {
		return adres;
	}
	public void setAdres(Adress adres) {
		this.adres = adres;
	}
	
	public OrderRequest() {

	}
	
	
	
	
}
