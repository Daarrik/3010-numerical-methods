import java.util.*;
import java.io.*;

public class Interpolation {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter a file: ");
        Scanner file = new Scanner("");
        try {
            file = new Scanner(new File(kb.next()));
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file.");
            System.exit(1);
        }

        ArrayList<Fraction> input = new ArrayList<Fraction>();
        while(file.hasNext()) {
            String nextInput = file.next();
            if(nextInput.indexOf("/") > -1) {
                int fracIndex = nextInput.indexOf("/");
                input.add(new Fraction(Integer.parseInt(nextInput.substring(0, fracIndex)), Integer.parseInt(nextInput.substring(fracIndex+1))));
            } else if(nextInput.indexOf(".") > -1) {
                int exp = nextInput.substring(nextInput.indexOf(".") + 1).length();
                double numerator = Double.parseDouble(nextInput);
                double denominator = 1;
                for(int i = 0; i < exp; i++) {
                    numerator *= 10;
                    denominator *= 10;
                }
                input.add(new Fraction((int)numerator, (int)denominator));
            } else {
                input.add(new Fraction(Integer.parseInt(nextInput)));
            }
        }

        if(input.size() > 50) {
            System.out.println("Too many points.");
            System.exit(1);
        }

         // Initialize x and f(x) input
        Fraction[] x = new Fraction[input.size()/2];
        Fraction[] fx = new Fraction[input.size()/2];
        for (int i = 0; i < input.size()/2; i++)
			x[i] = input.get(i);
		for (int i = input.size()/2; i < input.size(); i++)
            fx[i - input.size()/2] = input.get(i);
            
        // Create value table
        Fraction[][] table = new Fraction[input.size()-1][input.size()/2+1];
        for(int i = 0; i < table.length; i+=2)
            table[i][0] = x[i/2];
        for(int i = 0; i < table.length; i+=2)
            table[i][1] = fx[i/2];
        
        for(int j = 2; j < table[0].length; j++) {
              for(int i = j-1; i < table.length-1; i+=2) {
                  if(i <= table.length-j) {
                    Fraction f1 = table[i + 1][j - 1];
                    Fraction f0 = table[i - 1][j - 1];
                    Fraction x1 = table[j + i - 1][0];
                    Fraction x0 = table[i - j + 1][0];
                    Fraction numerator = f1.sub(f0);
                    Fraction denominator = x1.sub(x0);
                    table[i][j] = numerator.div(denominator);
                }
            }
        }

        // Create labels for table
        String[] tableLabel = new String[input.size()/2+1];
        tableLabel[0] = "x";
        for(int i = 1; i < tableLabel.length; i++) {
            tableLabel[i] = "f[";
            if(i > 4) {
                tableLabel[i] += i-1;
            } else {
                for(int j = 1; j < i; j++)
                    tableLabel[i] += ",";
            }
            tableLabel[i] += "]";
        }

        // ----- DIVIDED DIFFERENCE TABLE ----- \\
        // Print out table labels
        for(int i = 0; i < tableLabel.length; i++)
            System.out.printf("%10s", tableLabel[i]);

        System.out.println();

        // Print out table values
        for(int i = 0; i < table.length; i++) {
			for(int j = 0; j < table[i].length; j++) {
				if (table[i][j] == null) {
					System.out.print("          ");
				} else {
					System.out.printf("%10s", table[i][j]);
				}
			}
			System.out.println();
        }
        // ----- DIVIDED DIFFERENCE TABLE ----- \\

        System.out.println();

        // ----- NEWTON'S FORM ----- \\
        System.out.println("Newton's Form:");

        String newtForm = "";
        newtForm += String.format("%s", table[0][1]);
        for(int i = 1, j = 2; j < table[i].length; i++, j++) {
            if(table[i][j].getNum() < 0) {
                Fraction tempCoef = new Fraction(Math.abs(table[i][j].getNum()), table[i][j].getDen());
                newtForm += String.format(" - %s", tempCoef);
            } else {
                newtForm += String.format(" + %s", table[i][j]);
            }
            for(int k = 0; k < i*2; k += 2) {
                if(table[k][0].getNum() == 0) {
                    newtForm += "x";
                } else {
                    if(table[k][0].getNum() < 0) {
                        Fraction tempFrac = new Fraction(Math.abs(table[k][0].getNum()), table[k][0].getDen());
                        newtForm += String.format("(x+%s)", tempFrac);
                    } else {
                        newtForm += String.format("(x-%s)", table[k][0]);
                    }
                }
            }
        }
        System.out.println(newtForm);
        // ----- NEWTON'S FORM ----- \\

        System.out.println();

        // ----- LAGRANGE FORM ----- \\
        System.out.println("Lagrange's Form:");

        String lagForm = "";
        for(int i = 0; i < table.length; i+=2) {
            Fraction coef = table[i][1];
            String term = "";
            for(int k = 0; k < table.length; k +=2) {
                if(k != i) {
                    Fraction denominator = table[i][0].sub(table[k][0]);
                    coef = coef.div(denominator);
                    if(table[k][0].getNum() == 0) {
                        term += "x";
                    } else {
                        if(table[k][0].getNum() < 0) {
                            Fraction tempFrac = new Fraction(Math.abs(table[k][0].getNum()), table[k][0].getDen());
                            term += String.format("(x+%s)", tempFrac);
                        } else {
                            term += String.format("(x-%s)", table[k][0]);
                        }
                    }
                }
            }
            if(i == 0) {
                lagForm += coef.toString() + term;
            } else {
                if(coef.getNum() < 0) {
                    Fraction tempCoef = new Fraction(Math.abs(coef.getNum()), coef.getDen());
                    lagForm += String.format(" - %s", tempCoef) + term;
                } else {
                    lagForm += String.format(" + %s", coef) + term;
                }
            }
        }

        System.out.println(lagForm);
        // ----- LAGRANGE FORM ----- \\

        System.out.println();

        // ----- SIMPLIFIED FORM ----- \\
        System.out.println("Simplified Form:");

        System.out.println("Could not figure this out.");
        // ----- SIMPLIFIED FORM ----- \\

    }
}