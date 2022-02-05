public class FractionDriver {
    public static void main(String[] args) {
        Fraction fraction = new Fraction(3, 2);
        System.out.println(fraction);
        System.out.println(fraction.mult(new Fraction(3, 2)));
        System.out.println(fraction.add(new Fraction(4, 6)));
        System.out.println(fraction.sub(new Fraction(4, 6)));

    }
}