package project_1;

import java.io.Serializable;
import java.util.ArrayList;

public class GuestList implements Serializable{
	private final int numarLocuri;
	private ArrayList<Guest> guestList;
	private ArrayList<Guest> waitList;
	
	private static final long serialVersionUID = 1L;

	//constructori
	public GuestList(int numarLocuri) {
		this.numarLocuri = numarLocuri;
		this.guestList = new ArrayList<Guest>(this.numarLocuri);
		this.waitList = new ArrayList<Guest>(this.numarLocuri*2);
	}

	//metoda de adaugare participant
	public int add(Guest newGuest) {
		//verifica mai intai daca exista in guestList
		for(int i = 0; !guestList.isEmpty() && i < guestList.size(); i++) {
			if(guestList.get(i).intersects(newGuest)) {
				System.out.println("Sunteti deja inscris la eveniment.");
				return -1;
			}
		}
		
		//apoi verifica daca este in waitList
		for(int i = 0; !waitList.isEmpty() && i < waitList.size(); i++) {
			if(waitList.get(i).intersects(newGuest)) {
				System.out.println("Sunteti deja inscris pe lista de asteptare. "
						+ "La numarul de ordine " + i + ".");
				return i;
			}
		}
		
		if(this.numarLocuri > this.guestList.size()) {
			this.guestList.add(newGuest);
			System.out.println("Felicitari! Persoana a fost inscrisa cu succes la eveniment.");
			return 0;
		}else {
			waitList.add(newGuest);
			System.out.println("Felicitari! Ati fost inscris pe lista de asteptare.\nLa numarul de ordine " 
			+ waitList.size());
			return waitList.size();
		}
	}
		
	//verifica
	public String check(String criteriu, ArrayList<Guest> lista) {
		//itereaza prin lista
		if(!lista.isEmpty()) {
			for(int i = 0; i < lista.size(); i++) {
				if(lista.get(i).getName().equals(criteriu)) {
					return "Name";
				}else if(lista.get(i).getEmail().equals(criteriu)) {
					return "Email";
				}else if(lista.get(i).getPhoneNumber().equals(criteriu)) {
					return "Phone Number";
				}
			}
		}
		return "Empty";
	}
	
	//remove person
	public boolean remove(String removePerson) {
		//verificare in functie de nume, email sau numar de telefon
		
		//verificare in functie de nume
		ArrayList<Guest> lista;
		String field = check(removePerson, this.guestList);
		if(!field.equals("Empty")) {
			lista = this.guestList;
		}else {
			field = check(removePerson, this.waitList);
			if(field.equals("Empty")) {
				System.out.println("Eroare: persoana nu este inscrisa pe nici-o lista.");
				return false;
			}
			lista = this.waitList;
		}
		
		if(lista == guestList) {
			//verificare lista de asteptare != null
			if(!this.waitList.isEmpty()) {
				this.guestList.remove(getIndex(removePerson, lista));
				this.guestList.add(this.waitList.get(0));
				this.waitList.remove(0);
			}else {
				this.guestList.remove(getIndex(removePerson, lista));
			}
			System.out.println("Persoana a fost stearsa cu succes din lista de participanti.");
			return true;
		}
		else {
			if(field.equals("Name")) {
				lista.remove(getIndex(removePerson, lista));
			}else if (field.equals("Email")){
				lista.remove(getIndex(removePerson, lista));
			}else if(field.equals("Phone Number")) {
				lista.remove(getIndex(removePerson, lista));
			}
			System.out.println("Persoana a fost stearsa cu succes din lista de asteptare.");
			return true;
		}
	}
	
	//returneaza indexul obiectului care are 
	//numele sau emailul sau nr de telefon identic cu parametrul dat
	private int getIndex(String criteriu, ArrayList<Guest> lista) {
		for(int i = 0; !lista.isEmpty() && i < lista.size(); i++) {
			if(lista.get(i).getName().equals(criteriu)) {
				return i;
			}else if(lista.get(i).getEmail().equals(criteriu)) {
				return i;
			}else if(lista.get(i).getPhoneNumber().equals(criteriu)) {
				return i;
			}
		}
		
		return -1;
	}
	
	//metoda actualizeaza un camp al guest, selectat de utilizator, cu o informatie noua
	public void update(String persoana, int campDeActualizat, String informatieNoua) {
		int indexGuestList = getIndex(persoana, this.guestList);
		int indexWaitList = getIndex(persoana, this.waitList);
		if(indexGuestList >= 0) {
			switch(campDeActualizat) {
				case 1:
					guestList.get(indexGuestList).setName(informatieNoua);
					System.out.println("Actualizarea numelui s-a efectuat cu succes.");
					break;
				case 2:
					guestList.get(indexGuestList).setEmail(informatieNoua);
					System.out.println("Actualizarea email-ului s-a efectuat cu succes.");
					break;
				case 3:
					guestList.get(indexGuestList).setPhone_number(informatieNoua);
					System.out.println("Actualizarea numarului de telefon s-a efectuat cu succes.");
					break;
				default:
					System.out.println("Criteriile de cautare nu corespund "
							+ "cu informatiile din baza de date");
					break;
			}
		}else if(indexWaitList >= 0){
			switch(campDeActualizat) {
				case 1:
					waitList.get(indexWaitList).setName(informatieNoua);
					System.out.println("Actualizarea numelui s-a efectuat cu succes.");
					break;
				case 2:
					waitList.get(indexWaitList).setEmail(informatieNoua);
					System.out.println("Actualizarea email-ului s-a efectuat cu succes.");
					break;
				case 3:
					waitList.get(indexWaitList).setPhone_number(informatieNoua);
					System.out.println("Actualizarea numarului de telefon s-a efectuat cu succes.");
					break;
				default:
					System.out.println("Criteriile de cautare nu corespund "
							+ "cu informatiile din baza de date");
					break;
			}
		}else {
			System.out.println("Criteriile de cautare nu corespund "
					+ "cu informatiile din baza de date");
		}
	}
	
	//searches for the given keyword and return an array with all the 
	//objects meeting the criteria in whichever field
	public ArrayList<Guest> search(String searchWord){
		ArrayList<Guest> result = new ArrayList<Guest>();
		//searches substring in guestList
		for(int i = 0; !this.guestList.isEmpty() && i < this.guestList.size(); i++) {
			Guest guest = this.guestList.get(i);
			if(lookup(guest.getName(),searchWord)){
				result.add(guest);
			}else if(lookup(guest.getEmail(),searchWord)){
				result.add(guest);
			}else if(lookup(guest.getPhoneNumber(),searchWord)){
				result.add(guest);
			}
		}
		
		//searches substring in waitList
		for(int i = 0; !this.waitList.isEmpty() && i < this.waitList.size(); i++) {
			Guest guest = this.waitList.get(i);
			if(lookup(guest.getName(),searchWord)){
				result.add(guest);
			}else if(lookup(guest.getEmail(),searchWord)){
				result.add(guest);
			}else if(lookup(guest.getPhoneNumber(),searchWord)){
				result.add(guest);
			}
		}
		return result;
	}
	
	//searches for the word in the given field
	private boolean lookup(String guestField, String searchWord) {
		int wordLen = searchWord.length();
		for(int i = 0; i+wordLen <= guestField.length(); i++) {
			if(guestField.substring(i, i+wordLen).equals(searchWord)) {
				return true;
			}
		}
		return false;
	}
	
	public int getNumarLocuri() {
		return this.numarLocuri;
	}
	
	//lista cu participanti
	public ArrayList<Guest> guests() {
		return this.guestList;
	}
	
	//lista de asteptare
	public ArrayList<Guest> waitlist() {
		return this.waitList;
	}
	
	//numarul de locuri disponibile la eveniment
	public int available() {
		return this.numarLocuri - this.guestList.size();
	}
	
	//numarul de participanti
	public int guests_no() {
		return this.guestList.size();
	}
	
	//numarul de persoane de pe lista de asteptare
	public int waitlist_no() {
		return this.waitList.size();
	}
	
	//numarul de persoane de pe lista de asteptare
	public int subscribe_no() {
		return this.guestList.size() + this.waitList.size();
	}

}
