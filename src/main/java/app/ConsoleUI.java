package app;

import java.util.Locale;
import java.util.Scanner;

public class ConsoleUI {
    private final Validator validator;
    private final PasswordGenerator generator;

    public ConsoleUI(Validator validator, PasswordGenerator generator) {
        this.validator = validator;
        this.generator = generator;
    }

    public void run() {
        System.out.println("Генератор паролей (10 000 .. 1 000 000 символов)");
        System.out.println("Уровни сложности: LOW, MEDIUM, HIGH");

        try (Scanner sc = new Scanner(System.in)) {
            sc.useLocale(Locale.US);

            GenerationConfig config = readConfig(sc);
            validator.validateConfig(config);

            System.out.println("Генерация... (это может занять время при больших значениях)");

            long t0 = System.nanoTime();
            String password = generator.generate(config);
            long t1 = System.nanoTime();

            double ms = (t1 - t0) / 1_000_000.0;
            System.out.printf("Время генерации: %.2f мс%n", ms);

            printResult(password);

            maybeSaveToFile(sc, password);
        } catch (IllegalArgumentException ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Непредвиденная ошибка: " + ex.getMessage());
        }
    }

    private GenerationConfig readConfig(Scanner sc) {
        System.out.print("Введите длину N: ");
        String nRaw = sc.nextLine().trim();

        int n;
        try {
            n = Integer.parseInt(nRaw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Длина должна быть целым числом.");
        }

        System.out.print("Введите уровень сложности (LOW/MEDIUM/HIGH): ");
        String levelRaw = sc.nextLine().trim().toUpperCase(Locale.ROOT);

        ComplexityLevel level;
        try {
            level = ComplexityLevel.valueOf(levelRaw);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неизвестный уровень сложности. Используйте LOW, MEDIUM или HIGH.");
        }

        return new GenerationConfig(n, level);
    }

    private void printResult(String password) {
        // Важно: печатать 1 000 000 символов в консоль неудобно.
        // Поэтому печатаем длину и первые/последние 60 символов.
        int n = password.length();
        System.out.println("Готово. Длина пароля: " + n);

        if (n <= 200) {
            System.out.println("Пароль:\n" + password);
            return;
        }

        String head = password.substring(0, 60);
        String tail = password.substring(n - 60);
        System.out.println("Первые 60 символов:\n" + head);
        System.out.println("Последние 60 символов:\n" + tail);
    }
    private void maybeSaveToFile(Scanner sc, String password) {
        System.out.print("Сохранить пароль в файл? (y/n): ");
        String ans = sc.nextLine().trim().toLowerCase();

        if (!ans.equals("y") && !ans.equals("yes")) {
            return;
        }

        System.out.print("Имя файла (например, password.txt): ");
        String filename = sc.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "password.txt";
        }

        try {
            java.nio.file.Files.writeString(java.nio.file.Path.of(filename), password);
            System.out.println("Сохранено в файл: " + java.nio.file.Path.of(filename).toAbsolutePath());
        } catch (Exception e) {
            System.out.println("Не удалось сохранить файл: " + e.getMessage());
        }
    }}
