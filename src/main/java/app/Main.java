package app;

public class Main {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI(new Validator(), new PasswordGenerator());
        ui.run();
    }
}