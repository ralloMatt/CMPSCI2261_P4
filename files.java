import java.util.*;
import java.io.*;
import javax.xml.bind.DatatypeConverter;


public class files {

	public static void main(String[] args) throws IOException {
		// Getting file name
		System.out.println("Welcome to file handeling!");
		System.out.println("What is the name of the file you want to play with?");
		System.out.println("The file should be located in the src folder.");
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		
			
		try {
			// opening file
			java.io.File file = new java.io.File("src/" + name);
			FileInputStream input = new FileInputStream(file);
			// getting the byte array of the file
			byte bytes[] = new byte[(int)file.length()]; 
			
			input.read(bytes); // reading the bytes to be put in the byte array
			String hexstring = ""; // initializing string
			
			for(byte b : bytes){ // converting bytes to a hex string
				hexstring = hexstring + String.format("%02X", b);
			}
			
			
			System.out.println(hexstring); // printing the hex string
			
			
			System.out.println("What amount of bytes would you like to see data on?");
			System.out.println("Please chose numbers 1 - 3");
			
			int choice = scan.nextInt(); // asking user what he would like to see
			
			Map<String, Integer> Map = new LinkedHashMap<>(); // Creating map 
			String[] array = hexstring.split("(?<=\\G..)"); // Splitting the hex string to get a string array every two characters
			
			if(choice == 1) {
				Map = new_map(array, Map); // creating map
				display_map(Map); // displaying map
			}
			
			if(choice == 2) {
				String[] array2 = two_bytes(array); // changing string array to what the user wants to get information on
				Map = new_map(array2, Map);
				display_map(Map);
			}
			
			
			if (choice == 3) {
				String[] array3 = three_bytes(array); // changing string array to what the user wants to get information on
				Map = new_map(array3, Map);
				display_map(Map);	
			}
			
			// asking user if they want to modify the file
			System.out.println("Would you like to modify the file by replacing some byte sequence with another?");
			System.out.println("1 for YES, 0 for NO");
			int value = scan.nextInt();
			
			if(value == 1){ // if they want to change it
				
				System.out.println("What is the length of byte you would like to modify (1 - 3)?");
				int modify = scan.nextInt();
				
				String[] new_array = array.clone(); // cloning the original hex array to change
				int byte_length = 2; // number of bytes or characters in each string in the array
				
				if(modify == 2) { 
					new_array = two_bytes(array); // converting array to what the user desires
					byte_length = 4;
				}

				if(modify == 3) {
					new_array = three_bytes(array); // converting array to what the user desires
					byte_length = 6;
				}
				
				System.out.println("What is the original hexidecimal sequence you would like to change?"); // asking for what byte sequence they would like to change
				System.out.println("Please use all capital letters!");
				int truth = 0; // truth used to determine if user entered hex sequence right
				String old_hex = scan.next(); // scanning for hex to change
				
				for(int i = 0; i<new_array.length; i++) { // seeing if the hex entered is part of the original sequence
					
					if(old_hex.equals(new_array[i])) {
						truth = 1;
					}	
				}
				
				while(truth == 0) { // if the hex isn't in the original sequence keep asking until get desired input
					System.out.println("Incorrect hexidecimal sequence entered!");
					System.out.println("Please enter again: ");
					old_hex = scan.next();
					
					for(int i = 0; i<new_array.length; i++) {
						
						if(old_hex.equals(new_array[i])) {
							truth = 1;
						}	
					}
					
				}
				
				System.out.println("What is the new byte sequence you would like to replace " + old_hex + " with?"); // asking for new sequence
				String new_hex = scan.next();
				
				while(byte_length != new_hex.length()) { // making sure it's the same length
					System.out.println("Incorrect number of bytes entered!");
					System.out.println("Please try again: ");
					new_hex = scan.next();
				}
				
				new_hex = new_hex.toUpperCase(); // making sure it's capitalized
				
				System.out.println("You said you want to replace the sequence " + old_hex + " with " + new_hex + ".");
				
			
				// Creating new hex_string and replacing all old hex sequences with the new sequence the user has supplied
				String new_hexstring = hexstring;
				
				new_hexstring = new_hexstring.replaceAll(old_hex, new_hex);
						
				
				// priting old and new to show user
				System.out.println("Original Hex String: " + hexstring);
				System.out.println("New Hex String: " + new_hexstring);
				
				String file_name = file.getName(); // getting name of the file
				String name2 = file_name.substring(0, file_name.lastIndexOf('.')); // getting rid of extention so I can give it new one
				
				
				// System.out.println("Name of file without extention : " + name2 );
				
				java.io.File file2 = new java.io.File("src/" + name2 + ".mwh"); // giving file new extension
				
				FileOutputStream output = new FileOutputStream(file2);
				
				byte bytes2[] = toByteArray(new_hexstring); // converting hex string to byte array
				output.write(bytes2);
				
				input.close();
				output.close();
				
				System.out.println("A .mwh file has been created. Congratulations!");
				
				
			}
			
			else // if they do not want to modify the file
				System.out.println("END");
				

			
			
		
				
			
		}
		catch(FileNotFoundException e){ // if the file cannot be found
			System.out.println("File not found");
			scan.nextLine();
			
		}
		catch(Exception e) {
			System.out.println("You entered something wrong!!!!");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		scan.close(); // closing scanner
	}
	
	
	
	
	public static Map<String, Integer> new_map(String[] array, Map<String, Integer> Map) { // Function to create the map of hex combos
		
		for(int i = 0; i < array.length; i++) { // looping through string array
			
			if(!Map.containsKey(array[i])) // If the map does not have the string then put in the map
				Map.put(array[i], 1);
			else { // If the map already has the string then add one to the counter
				int value = Map.get(array[i]);
				value++;
				Map.put(array[i], value);
				
			}
			
		}
		
		return Map;
		
	}
	
	
	public static void display_map(Map<String, Integer> Map) { // function to display the map
		
		Set<Map.Entry<String, Integer>> entrySet = Map.entrySet(); // using set to display
		
		for (Map.Entry<String, Integer> entry: entrySet) { // looping to print values
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		
		
	}
	
	public static String[] two_bytes(String[] array) { // used convert string array into byte pairs
		
		ArrayList<String> list = new ArrayList<String>(); // creating a arraylist for easier access
		for(int i = 0; i < array.length - 1; i ++){ // looping through array and adding 4 characters to linked list
			list.add(array[i] + array[i+1]);
		}
		
		
		String[] array2 = new String[list.size()]; // creating a new string array
		
		for(int j = 0; j < array2.length; j++) { // filling string array with the values from array list
			array2[j] = list.get(j).toString();
		}
		
		return array2; // sending back new string array
		
		
	}
	
	public static String[] three_bytes(String[] array) { // same as function for two_bytes
		
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length - 2; i ++){
			list.add(array[i] + array[i+1] + array[i+2]);
		}
		
		
		String[] array3 = new String[list.size()];
		
		for(int j = 0; j < array3.length; j++) {
			array3[j] = list.get(j).toString();
		}
		
		return array3;
	
	}

	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
	
}
