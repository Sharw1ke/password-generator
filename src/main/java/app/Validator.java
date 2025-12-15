package app;

public class Validator {
    private static final int MIN_LEN = 10_000;
    private static final int MAX_LEN = 1_000_000;

    public void validateConfig(GenerationConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Конфигурация не задана.");
        }
        validateLength(config.getLength());
        validateComplexity(config.getLevel());
    }

    public void validateLength(int n) {
        if (n < MIN_LEN || n > MAX_LEN) {
            throw new IllegalArgumentException(
                    "Длина должна быть в диапазоне " + MIN_LEN + ".." + MAX_LEN + "."
            );
        }
    }

    public void validateComplexity(ComplexityLevel level) {
        if (level == null) {
            throw new IllegalArgumentException("Уровень сложности не задан.");
        }
    }
}