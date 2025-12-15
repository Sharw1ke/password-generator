package app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleUI {

    private final Validator validator;
    private final PasswordGenerator generator;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Validator validator, PasswordGenerator generator) {
        this.validator = validator;
        this.generator = generator;
    }

    public void run() {
        System.out.println("Password generator (10 000 .. 1 000 000 characters)");
        System.out.println("Complexity levels: LOW, MEDIUM, HIGH");

        try {
            System.out.print("Enter length N: ");
            int length = Integer.parseInt(scanner.nextLine());
            validator.validateLength(length);

            System.out.print("Enter complexity level (LOW/MEDIUM/HIGH): ");
            ComplexityLevel level = ComplexityLevel.valueOf(
                    scanner.nextLine().trim().toUpperCase()
            );

            GenerationConfig config = new GenerationConfig(length, level);

            long start = System.nanoTime();
            String password = generator.generate(config);
            long end = System.nanoTime();

            double ms = (end - start) / 1_000_000.0;

            System.out.println("Done. Password length: " + password.length());
            System.out.println("First 60 characters:");
            System.out.println(password.substring(0, Math.min(60, password.length())));
            System.out.println("Last 60 characters:");
            System.out.println(password.substring(
                    Math.max(0, password.length() - 60)));

            System.out.printf("Generation time: %.2f ms%n", ms);

            System.out.print("Save password to file? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.print("File name (e.g., password.txt): ");
                String fileName = scanner.nextLine();

                saveToFile(fileName, password);
                System.out.println("Password saved to file: " + fileName);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void saveToFile(String fileName, String content) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }
}
