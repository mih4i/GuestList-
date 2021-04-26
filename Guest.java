package project_1;

import java.io.Serializable;

public class Guest implements Serializable{
	private String first_name;
	private String last_name;
	private String name;
	private String email;
	private String phone_number;
	
	private static final long serialVersionUID = 1L;
	
	//Constructori
	public Guest(String first_name, String last_name, String email, String phone_number) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.phone_number = phone_number;
		this.name = this.first_name + " " + this.last_name;
	}

	//Metode
	public boolean intersects(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null) {
			return false;
		}
		
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		
		Guest newGuest = (Guest)obj;
		if(this.name.equals(newGuest.name) || this.email.equals(newGuest.email)
				|| this.phone_number.equals(newGuest.phone_number)) {
			return true;
		}
		return false;
	}
	
	
	public String getFirstName() {
		return this.first_name;
	}
	
	public String getLastName() {
		return this.last_name;
	}
	
	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPhoneNumber() {
		return this.phone_number;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setName(String name) {
		this.name = name;
		int indexOfSpace = this.name.indexOf(' ');
		this.first_name = this.name.substring(0, indexOfSpace);
		this.first_name = this.name.substring(indexOfSpace+1, this.name.length());
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
}
