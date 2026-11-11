import java.math.BigInteger;

/**
 * MODELO EXTENDIDO: hereda todo de CalcModel (reset, setValue, multiplyBy, getValue)
 * y agrega UN operador nuevo: la suma (addBy).
 *
 * No se modifica CalcModel: solo se extiende (principio abierto/cerrado).
 */
public class CalcModelPlus extends CalcModel {

    /** Suma el operando al total. Acepta enteros, decimales y fracciones. */
    public void addBy(String operand) {
        Fraction current = getTotalFraction();
        Fraction toAdd = Fraction.parse(operand); // lanza NumberFormatException si es inválido
        Fraction result = add(current, toAdd);
        setTotalFraction(result);
    }

    /** a/b + c/d = (ad + cb) / bd */
    private static Fraction add(Fraction a, Fraction b) {
        BigInteger num = a.num.multiply(b.den).add(b.num.multiply(a.den));
        BigInteger den = a.den.multiply(b.den);
        return new Fraction(num, den);
    }
}
