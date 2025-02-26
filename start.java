package src;
public class start {
    variables var = new variables();

    public static void testButton() {
        if (variables.debugMode) {
            System.out.println("Debug-Modus ist aktiviert.");
        } else {
            System.out.println("Debug-Modus ist deaktiviert.");
        }
    }
}