package app;

import java.security.SecureRandom;

public class PasswordGenerator {
    private final SecureRandom random = new SecureRandom();

    public String generate(GenerationConfig config) {
        int n = config.getLength();
        String alphabet = buildAlphabet(config.getLevel());

        if (alphabet.isEmpty()) {
            throw new IllegalArgumentException("Набор символов пуст.");
        }

        StringBuilder sb = new StringBuilder(n);
        int bound = alphabet.length();

        for (int i = 0; i < n; i++) {
            int idx = random.nextInt(bound);
            sb.append(alphabet.charAt(idx));
        }

        return sb.toString();
    }

    private String buildAlphabet(ComplexityLevel level) {
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{};:,.?/\\|";

        return switch (level) {
            case LOW -> lower;
            case MEDIUM -> lower + upper + digits;
            case HIGH -> lower + upper + digits + special;
        };
    }
}