// Pyramide_mit_Breite.java
// Erstellt von Moritz und Lennart
// letzte Änderung 2024-12-07, 15:00 Uhr MEZ

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
	
	// Variablen für die Weltgröße
	//int size_x = world.getSizeX(); // Weltbreite
	//int size_y = world.getSizeY(); // Welthöhe

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
	
	/* public void adjust_world_size() {
		world.clearAll();
		if (base_length > size_x) {
			world.setSize (base_length, ((base_length-1) / 2));
			size_x = world.getSizeX();
		}
		if (((base_length-1) / 2) > size_y) {
			world.setSize (base_length, ((base_length-1) / 2));
			size_y = world.getSizeY();
		}
		kara.setPosition (0, 0);
	}
	*/
	
	public void myProgram(){
		this.number_input();
		
		while (base_length > 0) {
			this.build_triangle();
		}
	}
}