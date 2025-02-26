package src;

import java.util.ArrayList;
import java.util.List;

public class variables {
    public String filePath = ""; // Pfad für die Wortliste
    public String wordlist = ""; // Wortliste
    public List<String> fiveLetterWords = new ArrayList<>(); // Liste für die 5-Buchstaben-Wörter
    public String secretWord = ""; // Geheimes Wort
    public String userInput = ""; // Benutzereingabe
    public boolean gameWon = false; // Spiel gewonnen
    public int attempts = 0; // Versuche
    public int maxAttempts = 6; // Maximale Versuche
    public static boolean debugMode = false; // Debug-Modus
    public static boolean disableDuplicateLetters = false; // Doppelte Buchstaben deaktivieren
    public int gameType = 1; // Spielmodus; 1 = normale Schwierigkeit (6 Versuche, das Wort zu erraten), 2 = erhöhte Schwierigkeit (4 Versuche, das Wort zu erraten, 3 = In Planung
}