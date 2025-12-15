package app;

public class GenerationConfig {
    private final int length;
    private final ComplexityLevel level;

    public GenerationConfig(int length, ComplexityLevel level) {
        this.length = length;
        this.level = level;
    }

    public int getLength() {
        return length;
    }

    public ComplexityLevel getLevel() {
        return level;
    }
}