package project_1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Gestiune {
	private static final Scanner input = new Scanner(System.in);
	private static GuestList guestList;
	
	static {
		try {
			if(deSerialize() != null) {
				guestList = deSerialize();
				System.out.println("Sesiunea a fost reluata de la ultima salvare a datelor.");
			}
		}catch(IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Intoduceti numarul de invitati.");
			guestList = new GuestList(input.nextInt());
		}
	}
	
	public static void help() {
		System.out.println(
			"0.help         - Afiseaza aceasta lista de comenzi\r\n" + 
			"1.add          - Adauga o noua persoana (inscriere)\r\n" + 
			"2.check        - Verifica daca o persoana este inscrisa la eveniment\r\n" + 
			"3.remove       - Sterge o persoana existenta din lista\r\n" + 
			"4.update       - Actualizeaza detaliile unei persoane\r\n" + 
			"5.guests       - Lista de persoane care participa la eveniment\r\n" + 
			"6.waitlist     - Persoanele din lista de asteptare\r\n" + 
			"7.available    - Numarul de locuri libere\r\n" + 
			"8.guests_no    - Numarul de persoane care participa la eveniment\r\n" + 
			"9.waitlist_no  - Numarul de persoane din lista de asteptare\r\n" + 
			"10.subscribe_no - Numarul total de persoane inscrise\r\n" + 
			"11.search       - Cauta toti invitatii conform sirului de caractere introdus\r\n" + 
			"12.reset        - Reseteaza (sterge) toate date introduse anterior\r\n" +
			"13.save & quit  - Salveaza si inchide aplicatia\r\n");
	}
	
	public static void add() {
		while(true) {
			System.out.println("Introduceti datele participantului :");
			System.out.println("Nume:");
			input.nextLine();
			
			try {
				String nume = input.nextLine();
				while(!verifyName(nume)) {
					System.out.println("Nume invalid, reintroduceti numele:");
					try {
						nume = input.nextLine();
					}catch(InputMismatchException e) {
						input.nextLine();
						System.out.println("Tipul de date introdus nu este corect. Introduceti un sir de caractere.");
					}
				}
				System.out.println("Prenume:");
				String prenume = input.nextLine();
				while(!verifyName(prenume)) {
					System.out.println("Prenume invalid, reintroduceti prenumele:");
					try {
						prenume = input.nextLine();
					}catch(InputMismatchException e) {
						input.nextLine();
						System.out.println("Tipul de date introdus nu este corect. Introduceti un sir de caractere.");
					}
				}
				System.out.println("Email:");
				String email = input.nextLine();
				while(!verifyEmail(email)) {
					System.out.println("Email invalid, reintroduceti email-ul:");
					try {
						email = input.nextLine();
					}catch(InputMismatchException e) {
						input.nextLine();
						System.out.println("Tipul de date introdus nu este corect. Introduceti un sir de caractere.");
					}
				}
				System.out.println("Numar de telefon:");
				String phone_no = input.nextLine();
				while(!verifyPhoneNumber(phone_no)) {
					System.out.println("Numar de telefon invalid, reintroduceti numarul de telefon:");
					try {
						phone_no = input.nextLine();
					}catch(InputMismatchException e) {
						input.nextLine();
						System.out.println("Tipul de date introdus nu este corect. Introduceti un sir de caractere.");
					}
				}
				
				//input.nextLine();
				Guest newGuest = new Guest(nume, prenume, email,
						phone_no);
				guestList.add(newGuest);
				break;
			}catch(InputMismatchException e) {
				System.out.println("Tipul de date introdus nu este corect. Introduceti un sir de caractere.");
			}
		}
	}
	
	public static boolean check(){
		while(true) {
			System.out.println("Introduceti numele, email-ul sau numarul de telefon al persoanei pe care o verificati.");
			input.nextLine();
			
			try {
				String criteriuCautare = input.nextLine();
				
				if(guestList.check(criteriuCautare, guestList.guests()) != "Empty") {
					System.out.println("Persoana a fost gasita in lista de participanti.");
					return true;
				}
				
				if(guestList.check(criteriuCautare, guestList.waitlist()) != "Empty") {
					System.out.println("Persoana a fost gasita in lista de asteptare.");
					return true;
				}
	
				System.out.println("Ceva nu a functionat.");
				return false;
			}catch(InputMismatchException e) {
				System.out.println("Tipul de date introdus nu este corect. Introduceti un sir de caractere.");
			}
		}
	}
	
	public static void remove() {
		
		while(true) {
			System.out.println("Introduceti numele, email-ul sau "
					+ "nr. de telefon al persoanei ce urmeaza a fi stearsa"
					+ " din lista de participanti.");
			input.nextLine();
			
			try {
				guestList.remove(input.nextLine());
				break;
			}catch(InputMismatchException e) {
				System.out.println("Tipul de date introdus nu este corect. Introduceti un sir de caractere.");
			}
		}
	}
	
	
	public static void update() {
		while(true) {
			System.out.println("Introduceti numele, email-ul sau "
					+ "nr. de telefon ale persoanei ale carei date "
					+ "doriti sa le actualizati.");
			input.nextLine();
			
			try {
				String persoana = input.nextLine();

				System.out.println("Introduceti codul pentru campul pe care doriti sa-l actualizati:"
						+ "\n1 pentru nume"
						+ "\n2 pentru email"
						+ "\n3 pentru numar de telefon");
				

				int campDeActualizat = input.nextInt();
				
				System.out.println("Introduceti informatia noua.");
				input.nextLine();
				
				String informatieNoua = input.nextLine();
				if(campDeActualizat == 1) {
					while(!verifyName(informatieNoua)) {
						System.out.println("Nume invalid, reintroduceti numele:");
						try {
							informatieNoua = input.nextLine();
						}catch(InputMismatchException e) {
							input.nextLine();
							System.out.println("Tipul de date introdus nu este corect.");
						}
					}
				}else if(campDeActualizat == 2) {
					while(!verifyEmail(informatieNoua)) {
						System.out.println("Email invalid, reintroduceti email-ul:");
						try {
							informatieNoua = input.nextLine();
						}catch(InputMismatchException e) {
							input.nextLine();
							System.out.println("Tipul de date introdus nu este corect.");
						}
					}
				}else if(campDeActualizat == 3) {
					while(!verifyPhoneNumber(informatieNoua)) {
						System.out.println("Numar de telefon invalid, reintroduceti numarul de telefon:");
						try {
							informatieNoua = input.nextLine();
						}catch(InputMismatchException e) {
							input.nextLine();
							System.out.println("Tipul de date introdus nu este corect.");
						}
					}
				}
				guestList.update(persoana, campDeActualizat, informatieNoua);
				break;
			}catch(InputMismatchException e){
				System.out.println("Tipul de date introdus nu este corect.");
			}
		}
	}
	
	
	public static void guests() {
		List<Guest> guests = guestList.guests();
		System.out.println("-->Lista de participanti.");
		for(Guest guest : guests) {
			System.out.println(guest.getName() + " " + guest.getEmail() + " "
					+ guest.getPhoneNumber());
		}
	}
	
	public static void waitlist() {
		List<Guest> waitList = guestList.waitlist();

		System.out.println("-->Lista de asteptare.");
		for(Guest guest : waitList) {
			System.out.println(guest.getName() + " " + guest.getEmail() + " "
					+ guest.getPhoneNumber());
		}
	}
	
	
	public static void available() {
		System.out.println("Numarul de locuri nedistribuit este " + guestList.available());
	}
	
	public static void guests_no() {
		System.out.println("Numarul de participanti la eveniment este " + guestList.guests_no());
	}
	
	public static void waitlist_no() {
		System.out.println("Numarul de persoane pe lista de asteptare este " + guestList.waitlist_no());
	}
	
	public static void subscribe_no() {
		System.out.println("Numarul total de persoane inscrise pe listele evenimentului este " 
	+ guestList.subscribe_no());
	}
	
	
	public static void search() {
		while(true) {
			System.out.println("Introduceti keyword-ul dupa care se va face cautarea.");
			input.nextLine();
			
			try {
				String searchString= input.nextLine();
				List<Guest> resultArr = guestList.search(searchString);
				System.out.println("Urmatoarea lista incorporeaza substring-ul '" + searchString +"' pe unul din campuri.");
				for(Guest iterator : resultArr) {
					System.out.println(iterator.getName() + " " + iterator.getEmail() 
								+ " " + iterator.getPhoneNumber());
				}
				break;
			}catch(InputMismatchException e) {
				System.out.println("Tipul de date introdus nu este corect.");
			}
		}
	}
	
	public static void serialize() throws IOException{
		try(ObjectOutputStream binaryFileOut = new ObjectOutputStream(
		      	new BufferedOutputStream(new FileOutputStream("D:\\Programming\\Java Eclipse\\Guests\\GuestList.dat")))) {
			binaryFileOut.writeObject(guestList);
		}
	}
	
	public static GuestList deSerialize() throws FileNotFoundException, IOException {
		
		try(ObjectInputStream binaryFileIn = new ObjectInputStream(
		        new BufferedInputStream(new FileInputStream("D:\\Programming\\Java Eclipse\\Guests\\GuestList.dat")))) {
				return (GuestList) binaryFileIn.readObject();
		    } catch (ClassNotFoundException e) {
		      System.out.println("A class not found exception: " + e.getMessage());
		    }
		return null;
	}
	
	public static void reset(){
		
		try {
			Files.deleteIfExists(Paths.get("D:\\Programming\\Java Eclipse\\Guests\\GuestList.dat"));
		}catch(NoSuchFileException e) {
			System.out.println("Fisierul nu exista.");
		}catch(IOException e) {
			System.out.println("Input gresit."); 
		}
		System.out.println("Actuala sesiune a fost resetata.");
		
		System.out.println("Intoduceti numarul de invitati.");
		guestList = new GuestList(input.nextInt());
	}
	
	public static void quit() {
		System.out.println("Aplicatia se va inchide.");
		System.exit(0);
	}
	
	private static boolean verifyName(String name) {
		if(name.length() < 1 ) {
			return false;
		}
		
		for (char c : name.toLowerCase().toCharArray()) {
			if(c < 'a' || c > 'z') {
				if(c != ' ') {
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean verifyEmail(String email) {
		if(email.length() < 1 ) {
			return false;
		}
		
		if(!email.contains("@") || !email.contains(".")) {
			return false;
		}
		
		if(email.indexOf('@') < 1 || email.lastIndexOf('.') < email.indexOf('@')) {
			return false;
		}
		
		return true;
	}
	
	private static boolean verifyPhoneNumber(String phone_no) {
		if(phone_no.length() != 10 || !phone_no.substring(0, 2).equals("07")) {
			return false;
		}
		
		for(char c : phone_no.toCharArray()) {
			if(c < '0' || c > '9') {
				return false;
			}
		}
		
		return true;
	}

	public static void main(String[] args) throws IOException{

		System.out.println("Introduceti codul uneia dintre comenzile de mai jos.");
		int choice = 0;
		while(true) {
			switch(choice) {
				case 0:
					help();
					break;
				case 1:
					add();
					break;
				case 2:
					check();
					break;
				case 3:
					remove();
					break;
				case 4:
					update();
					break;
				case 5:
					guests();
					break;
				case 6:
					waitlist();
					break;
				case 7:
					available();
					break;
				case 8:
					guests_no();
					break;
				case 9:
					waitlist_no();
					break;
				case 10:
					subscribe_no();
					break;
				case 11:
					search();
					break;
				case 12:
					reset();
					break;
				case 13:
					serialize();
					quit();
					break;
				default:
					System.out.println("1Oops! Ati introdus un cod gresit.\nIntroduceti un numar de la 0 la 13.");
					break;
			}
			System.out.println("Introduceti urmatoarea comanda.");
			try {
				choice = input.nextInt();
			}catch(InputMismatchException e) {
				input.nextLine();
				System.out.println("Oops! Ati introdus un cod gresit.\nIntroduceti un numar de la 0 la 13.");
				choice = 0;
			}
		}
	}
}
