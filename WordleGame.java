import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class WordleGame {

    // Liste der Wörter für das Spiel (initial leer, wird aus Datei geladen)
    private static List<String> WORD_LIST = new ArrayList<>();

    // Das geheime Wort (wird intern in Großbuchstaben verwendet)
    private String secretWord;

    // Konstanten für die Anzahl der Reihen und Spalten im Spielfeld
    private static final int ROWS = 6;
    private static final int COLUMNS = 5;

    // Aktuelle Reihe, in der der Spieler sich befindet
    private int currentRow = 0;

    // Grid für das Spielfeld
    private final JTextField[][] grid = new JTextField[ROWS][COLUMNS];

    // Eingabefeld für die Benutzereingabe
    private final JTextField inputField = new JTextField();

    // JFrame für die grafische Benutzeroberfläche
    private JFrame frame;

    // Zeitpunkt, zu dem das Spiel gestartet wurde
    private Instant startTime;

    // Pfad zur Datei mit den Wörtern
    private static final String filePath = "Wortliste.txt"; // Passe den Pfad entsprechend an

    // Konstruktor für das Spiel
    public WordleGame() {
        loadWordsFromFile();  // Laden der Wörter aus der Datei
        initializeGame();
    }

    // Initialisierung des Spiels
    private void initializeGame() {
        // Zufälliges Wort aus der Wortliste auswählen und in Großbuchstaben umwandeln
        if (WORD_LIST.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Die geladene Wortliste enthält keine 5-Buchstaben-Wörter. Bitte gib ein gültiges Wort ein.");
            String userWord = promptForValidWord();
            secretWord = userWord.toUpperCase();
        } else {
            secretWord = WORD_LIST.get(new Random().nextInt(WORD_LIST.size())).toUpperCase();
        }
        currentRow = 0;

        // Erstellen und Einrichten des Hauptfensters, falls es noch nicht existiert
        if (frame == null) {
            frame = new JFrame("Wordle Spiel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 700);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Grid-Panel für das Spielfeld
            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(ROWS, COLUMNS, 10, 10));

            // Initialisieren des Grids
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    grid[i][j] = new JTextField();
                    grid[i][j].setEditable(false);
                    grid[i][j].setHorizontalAlignment(JTextField.CENTER);
                    grid[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                    grid[i][j].setFocusable(false);
                    grid[i][j].setBackground(Color.WHITE);
                    grid[i][j].setText("");
                    gridPanel.add(grid[i][j]);
                }
            }

            // Panel für die Benutzereingabe
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BorderLayout(10, 10));

            inputField.setFont(new Font("Arial", Font.PLAIN, 18));
            inputPanel.add(inputField, BorderLayout.CENTER);

            JButton submitButton = new JButton("Eingabe");
            inputPanel.add(submitButton, BorderLayout.EAST);

            mainPanel.add(gridPanel, BorderLayout.CENTER);
            mainPanel.add(inputPanel, BorderLayout.SOUTH);

            frame.add(mainPanel);

            // ActionListener für den Submit-Button hinzufügen
            submitButton.addActionListener(e -> handleSubmit());

            // KeyListener für die Eingabetaste hinzufügen
            inputField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        handleSubmit();
                    }
                }
            });
        } else {
            // Zurücksetzen des Grids und des Eingabefeldes für ein neues Spiel
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    grid[i][j].setText("");
                    grid[i][j].setBackground(Color.WHITE);
                }
            }
            inputField.setText("");
        }

        // Fenster sichtbar machen
        frame.setVisible(true);
        startTime = Instant.now(); // Startzeit des Spiels setzen
    }

    // Behandlung der Eingabe des Benutzers
    private void handleSubmit() {
        String guess = inputField.getText().toUpperCase(); // Benutzereingabe in Großbuchstaben umwandeln
        if (guess.length() != COLUMNS) { // Prüfen, ob die Eingabe die richtige Länge hat
            JOptionPane.showMessageDialog(frame, "Bitte gib ein Wort mit fünf Buchstaben ein.");
            return;
        }
        processGuess(guess); // Eingabe verarbeiten
        inputField.setText(""); // Eingabefeld zurücksetzen
    }

    // Verarbeitung des benutzergegebenen Wortes
    private void processGuess(String guess) {
        if (currentRow >= ROWS) { // Prüfen, ob die maximale Anzahl an Versuchen erreicht wurde
            endGame("Das Spiel ist beendet!"); // Spiel beenden, falls alle Versuche aufgebraucht sind
            return;
        }

        // Durchlaufen aller Buchstaben in der Eingabe
        for (int i = 0; i < COLUMNS; i++) {
            char guessChar = guess.charAt(i); // Einzelnen Buchstaben aus der Eingabe holen
            JTextField cell = grid[currentRow][i]; // Zelle im Grid holen
            cell.setText(String.valueOf(guessChar)); // Buchstaben in die Zelle setzen

            // Farbe der Zelle setzen: Grün für richtig, Gelb für richtig aber falsche Position, Rot für falsch
            if (secretWord.charAt(i) == guessChar) {
                cell.setBackground(Color.GREEN);
            } else if (secretWord.contains(String.valueOf(guessChar))) {
                cell.setBackground(Color.YELLOW);
            } else {
                cell.setBackground(Color.RED);
            }
        }

        // Prüfen, ob das Wort richtig geraten wurde
        if (guess.equals(secretWord)) {
            endGame("Herzlichen Glückwunsch! Du hast das Wort erraten."); // Spiel beenden mit Gewinnmeldung
            return;
        }

        currentRow++; // Nächste Reihe

        // Prüfen, ob alle Versuche aufgebraucht sind
        if (currentRow == ROWS) {
            endGame("Das Spiel ist beendet! Das gesuchte Wort war: " + formatWord(secretWord)); // Spiel beenden mit Verlustmeldung
        }
    }

    // Beenden des Spiels und Anzeige einer Meldung
    private void endGame(String message) {
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime); // Berechnung der Spieldauer
        long seconds = duration.getSeconds(); // Umwandlung der Dauer in Sekunden

        int response = JOptionPane.showConfirmDialog(frame, message + "\nBenötigte Zeit: " + seconds + " Sekunden.\nMöchtest du erneut spielen?", "Spielende", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            initializeGame(); // Neues Spiel starten
        } else {
            System.exit(0); // Anwendung beenden
        }
    }

    // Methode zur Formatierung eines Wortes (nur erster Buchstabe groß)
    private String formatWord(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    // Methode zum Laden der Wörter aus einer Datei
    private void loadWordsFromFile() {
        try {
            // Datei in String umwandeln
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Alle Sonderzeichen ersetzen
            content = content.replace("ö", "oe")
                             .replace("Ö", "Oe")
                             .replace("ä", "ae")
                             .replace("Ä", "Ae")
                             .replace("ü", "ue")
                             .replace("Ü", "Ue")
                             .replace("ß", "ss");

            // Alle Wörter extrahieren
            String[] words = content.split("\\W+");

            // Liste für die 5-Buchstaben-Wörter
            List<String> fiveLetterWords = new ArrayList<>();

            // Überprüfen, ob jedes Wort 5 Buchstaben hat und es der Liste hinzufügen
            for (String word : words) {
                // Umwandlung in Kleinbuchstaben
                word = word.toLowerCase();

                // Wenn das Wort 5 Buchstaben hat, füge es zur Liste hinzu
                if (word.length() == 5) {
                    fiveLetterWords.add(word);
                }
            }

            // Wenn keine 5-Buchstaben-Wörter gefunden wurden, wird die Liste manuell gefüllt
            if (fiveLetterWords.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Die geladene Wortliste enthält keine 5-Buchstaben-Wörter.");
            } else {
                WORD_LIST = fiveLetterWords;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Benutzer auffordern, ein gültiges Wort einzugeben
    private String promptForValidWord() {
        String userWord = "";
        while (userWord.length() != 5) {
            userWord = JOptionPane.showInputDialog(frame, "Bitte gib ein gültiges 5-Buchstaben-Wort ein. Dieses Wort wird dann genutzt:");
            if (userWord != null && userWord.length() == 5) {
                break;
            } else {
                JOptionPane.showMessageDialog(frame, "Das Wort muss 5 Buchstaben lang sein.");
            }
        }
        return userWord;
    }

    // Hauptmethode zum Starten der Anwendung
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordleGame::new); // Starten des Spiels auf dem Ereignis-Dispatch-Thread
    }
}
