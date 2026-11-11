import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * MODELO: guarda el total y sabe multiplicar.
 * Internamente usa fracciones exactas (BigInteger), así soporta
 * enteros largos, decimales largos y fracciones como 3/4 sin perder precisión.
 */
public class CalcModel {
    private static final String INITIAL_VALUE = "1";

    private Fraction m_total;

    public CalcModel() {
        reset();
    }

    public void reset() {
        m_total = Fraction.parse(INITIAL_VALUE);
    }

    public void setValue(String value) {
        m_total = Fraction.parse(value);
    }

    /** Multiplica el total por enteros, decimales o fracciones. Lanza NumberFormatException si es inválido. */
    public void multiplyBy(String operand) {
        m_total = m_total.multiply(Fraction.parse(operand));
    }

    public String getValue() {
        return m_total.toString();
    }

    /**
     * Permite que una clase hija realice operaciones usando el valor exacto,
     * sin tener que convertir el resultado mostrado nuevamente a texto.
     */
    protected Fraction getTotalFraction() {
        return m_total;
    }

    /** Actualiza el total desde una operación de una clase hija. */
    protected void setTotalFraction(Fraction value) {
        m_total = value;
    }
}

/**
 * Fracción exacta con BigInteger (siempre reducida, denominador > 0).
 * Permite multiplicar enteros, decimales y fracciones sin perder precisión.
 */
final class Fraction {
    private static final int DECIMALS = 30;

    final BigInteger num;
    final BigInteger den;

    Fraction(BigInteger n, BigInteger d) {
        if (d.signum() == 0) {
            throw new NumberFormatException("Division by zero");
        }
        if (d.signum() < 0) {
            n = n.negate();
            d = d.negate();
        }
        BigInteger g = n.gcd(d);
        if (g.signum() != 0 && !g.equals(BigInteger.ONE)) {
            n = n.divide(g);
            d = d.divide(g);
        }
        this.num = n;
        this.den = d;
    }

    /** Acepta: 25, -3.75, 1e5, 3/4, -2/5, 1.5/0.25 */
    static Fraction parse(String text) {
        String s = text.trim().replace(" ", "");
        if (s.isEmpty()) {
            throw new NumberFormatException("Empty input");
        }
        int slash = s.indexOf('/');
        if (slash >= 0) {
            Fraction a = parseDecimal(s.substring(0, slash));
            Fraction b = parseDecimal(s.substring(slash + 1));
            return a.divide(b);
        }
        return parseDecimal(s);
    }

    private static Fraction parseDecimal(String s) {
        BigDecimal bd = new BigDecimal(s); // lanza NumberFormatException si es inválido
        BigInteger unscaled = bd.unscaledValue();
        int scale = bd.scale();
        if (scale >= 0) {
            return new Fraction(unscaled, BigInteger.TEN.pow(scale));
        }
        return new Fraction(unscaled.multiply(BigInteger.TEN.pow(-scale)), BigInteger.ONE);
    }

    Fraction multiply(Fraction o) {
        return new Fraction(num.multiply(o.num), den.multiply(o.den));
    }

    Fraction divide(Fraction o) {
        return new Fraction(num.multiply(o.den), den.multiply(o.num));
    }

    boolean isInteger() {
        return den.equals(BigInteger.ONE);
    }

    /** true si el decimal termina (el denominador solo tiene factores 2 y 5). */
    boolean isTerminating() {
        BigInteger d = den;
        BigInteger two = BigInteger.valueOf(2);
        BigInteger five = BigInteger.valueOf(5);
        while (d.mod(two).signum() == 0) d = d.divide(two);
        while (d.mod(five).signum() == 0) d = d.divide(five);
        return d.equals(BigInteger.ONE);
    }

    String toDecimalString() {
        BigDecimal n = new BigDecimal(num);
        BigDecimal d = new BigDecimal(den);
        if (isTerminating()) {
            return n.divide(d).stripTrailingZeros().toPlainString();
        }
        return n.divide(d, DECIMALS, RoundingMode.HALF_UP).toPlainString();
    }

    /** Entero: "12". Fracción: "3/4 = 0.75" o "1/3 ≈ 0.333...". */
    @Override
    public String toString() {
        if (isInteger()) {
            return num.toString();
        }
        return num + "/" + den + (isTerminating() ? " = " : " ≈ ") + toDecimalString();
    }
}
