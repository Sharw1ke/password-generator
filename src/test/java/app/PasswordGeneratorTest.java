package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void generatesCorrectLength() {
        PasswordGenerator gen = new PasswordGenerator();
        GenerationConfig cfg = new GenerationConfig(10_000, ComplexityLevel.HIGH);
        String p = gen.generate(cfg);
        assertEquals(10_000, p.length());
    }

    @Test
    void validatorRejectsTooSmallLength() {
        Validator v = new Validator();
        GenerationConfig cfg = new GenerationConfig(9_999, ComplexityLevel.LOW);
        assertThrows(IllegalArgumentException.class, () -> v.validateConfig(cfg));
    }

    @Test
    void lowComplexityUsesOnlyLowercaseLetters() {
        PasswordGenerator gen = new PasswordGenerator();
        GenerationConfig cfg = new GenerationConfig(10_000, ComplexityLevel.LOW);
        String p = gen.generate(cfg);

        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            assertTrue(c >= 'a' && c <= 'z');
        }
    }
}