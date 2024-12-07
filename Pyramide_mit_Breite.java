// Pyramide_mit_Breite.java
// Erstellt von Moritz und Lennart
// letzte Änderung 2024-12-07, 16:42 Uhr MEZ

/*
	In diesem Programm soll Kara ein gleichschenkliges Dreieck mit beliebiger Grundseite
	legen. Die Länge dieser Seite soll vom Benutzer per Eingabefeld eingegeben werden.
*/

import javakara.JavaKaraProgram;
// import java.util.Scanner;

/* BEFEHLE:  kara.
 *   move()  turnRight()  turnLeft()
 *   putLeaf()  removeLeaf()
 *
 * SENSOREN: kara.
 *   treeFront()  treeLeft()  treeRight()
 *   mushroomFront()  onLeaf()
 */

public class Pyramide_mit_Breite extends JavaKaraProgram {
	
	int base_length = 0; // Länge der Grundseite
	boolean direction = false; // Laufrichtung von Kara; false = rechts, true = links

	public void number_input() { // Eingabe der Länge der Grundseite
		base_length = tools.intInput("Bitte gib die Länge der Grundseite ein: "); // integer-Eingabe
		this.check_input(); // Aufruf der Methode zur Überprüfung der Eingabe
	}
	
	//Methode zum Bau des Dreiecks
	public void build_triangle() { 
		if (direction == false) { // Laufrichtung rechts
			for (int i = 0; i < base_length; i++) {
				if (!kara.onLeaf()) {
					kara.putLeaf();
				}
				if (!kara.treeFront()){
					kara.move();
				}
			}
			base_length = base_length - 2;
			if (base_length > 0) {
				kara.turnRight();
				kara.turnRight();
				kara.move();
				kara.move();
				kara.turnRight();
				kara.move();
				kara.turnLeft();
			}
			direction = true;
		} else {
			if (direction == true) { // Laufrichtung links
				for (int i = 0; i < base_length; i++) {
					if (!kara.onLeaf()) {
						kara.putLeaf();
					}
					if (!kara.treeFront()) {
						kara.move();
					}
				}
				base_length = base_length - 2; // Dekrementierung der Seitenlänge um 2
				if (base_length > 0) {
					kara.turnRight();
					kara.turnRight();
					kara.move();
					kara.move();
					kara.turnLeft();
					kara.move();
					kara.turnRight();
				}
				direction = false;
			}
		}
	}
	
	public void check_input() {
		while (base_length % 2 == 0 || base_length <= 0) {
			if (base_length % 2 == 0) {
				tools.showMessage("Die Zahl muss ungerade sein!");
			} else {
				tools.showMessage("Die Zahl muss größer als 0 sein!");
			}
			base_length = tools.intInput("Bitte gib die Länge der Grundseite ein: ");
		}
	}
	
	public void myProgram(){
		 world.clearAll();
		
		//Variablen für die Weltgröße
		int size_x = world.getSizeX(); // Weltbreite
		int size_y = world.getSizeY(); // Welthöhe
		
		this.number_input();
				
		int triangle_height = (base_length-1) / 2; // Berechnung der Pyramidenhöhe
		
		if (base_length > size_x || triangle_height > size_y) {
			int new_size_x = Math.max(size_x, base_length);
			int new_size_y = Math.max(size_y, triangle_height);
			world.setSize(new_size_x, new_size_y+1);  // Setze die Weltgröße nur einmal
			size_x = world.getSizeX();
			size_y = world.getSizeY();
		}
		
		kara.setPosition(0, size_y-1);
		
		while (base_length > 0) {
			this.build_triangle();
		}
	}
}