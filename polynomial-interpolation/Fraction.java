
public class Fraction {

    private int numerator;
    private int denominator;

    Fraction(int num, int den) {
        if(den == 0) {
            throw new IllegalArgumentException("Denominator can't be zero.");
        }
        numerator = num;
        denominator = den;
        reduceFraction();
        fixSigns();
    }

    Fraction(int num) {
        this(num, 1);
    }

    public void setNum(int num) {
        numerator = num;
        fixSigns();
    }

    public void setDen(int den) {
        denominator = den;
        fixSigns();
    }

    public int getNum() {
        return numerator;
    }

    public int getDen() {
        return denominator;
    }
    public Fraction add(Fraction frac) {
        int num = numerator * frac.denominator + denominator * frac.numerator;
        int den = denominator * frac.denominator;
        Fraction fraction = new Fraction(num, den);
        fraction.reduceFraction();
        fixSigns();
        return fraction;
    }

    public Fraction sub(Fraction frac) {
        int num = numerator * frac.denominator - denominator * frac.numerator;
        int den = denominator * frac.denominator;
        Fraction fraction = new Fraction(num, den);
        fraction.reduceFraction();
        fixSigns();
        return fraction;
    }

    public Fraction mult(Fraction frac) {
        Fraction fraction =  new Fraction(numerator * frac.numerator, denominator * frac.denominator);
        fraction.reduceFraction();
        fixSigns();
        return fraction;
    }

    public Fraction div(Fraction frac) {
        Fraction fraction = new Fraction(numerator * frac.denominator, denominator * frac.numerator);
        fraction.reduceFraction();
        fixSigns();
        return fraction;
    }

    public void reduceFraction() {  
        int newDenom = gcd(numerator, denominator);  
        setNum(numerator/newDenom);
        setDen(denominator/newDenom);
        fixSigns();
    }  
  
    public int gcd(int num, int den) {  
        if (den == 0)  
            return num;
        return gcd(den, num % den);    
    }

    public void fixSigns() {
        if(numerator < 0 && denominator < 0) {
            numerator = Math.abs(numerator);
            denominator = Math.abs(denominator);
        } else if(numerator > 0 && denominator < 0) {
            numerator *= -1;
            denominator = Math.abs(denominator);
        }
    }

    @Override
    public String toString() {
        if(denominator == 1)
            return String.format("%d", numerator);
        else
            return String.format("%d/%d", numerator, denominator);
    }
}