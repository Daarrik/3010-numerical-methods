
public abstract class RootMethods {
	
	// Methods for getting value of y at x defined by Test class
	public abstract double functAt(double x);
	public abstract double derivAt(double x);
	
	public void bisection(double a, double b, int nMax, double stopError) {
		System.out.println("BISECTION:");
		System.out.printf("%3s%10s%10s%10s%11s%11s%11s%11s\n", "n", "a_n", "b_n", "c_n", "f(a)", "f(b)", "f(c)", "error");	
		
		double fA = functAt(a);
		double fB = functAt(b);
		
		if(fA * fB > 0) {
			System.out.print("a: " + a + "\n"
							+ "b: " + b + "\n"
							+ "f(a): " + fA + "\n"
							+ "f(b): " + fB + "\n"
							+ "Function has the same signs at a and b.\n\n");
			return;
		}
		
		double prevC = 0;
		
		for(int i = 0; i < nMax; i++) {
			double c = (a + b) / 2;
			double fC = functAt(c);
			double error = Math.abs((c-prevC)/c);
			
			if(i == 0) {
				System.out.printf("%3d%10.3f%10.3f%10.3f%11.3f%11.3f%11.3f%11s\n", i, a, b, c, fA, fB, fC, "-----");
			} else {
				System.out.printf("%3d%10.3f%10.3f%10.3f%11.3f%11.3f%11.3f%11.3f\n", i, a, b, c, fA, fB, fC, error);
			}
			
			if(Math.abs(error) < stopError) {
				System.out.print("Stop error threshold reached.\n\n");
				return;
			}
			
			if(fA * fC < 0) { 
				b = c;
				fB = fC;
			} else {
				a = c;
				fA = fC;
			}
			
			prevC = c;
		}
		
		System.out.print("Max iterations reached.\n\n");
	}

	public void falsePos(double a, double b, int nMax, double stopError) {
		System.out.println("FALSE POSITION:");
		System.out.printf("%3s%10s%10s%10s%11s%11s%11s%11s\n", "n", "a_n", "b_n", "c_n", "f(a)", "f(b)", "f(c)", "error");	
		
		double fA = functAt(a);
		double fB = functAt(b);
		
		if(fA * fB > 0) {
			System.out.print("a: " + a + "\n"
							+ "b: " + b + "\n"
							+ "f(a): " + fA + "\n"
							+ "f(b): " + fB + "\n"
							+ "Function has the same signs at a and b.\n\n");
			return;
		}
		
		double prevC = 0;
		
		for(int i = 0; i < nMax; i++) {
			double c = (a*fB-b*fA) / (fB-fA);
			double fC = functAt(c);
			double error = Math.abs((c-prevC)/c);
			
			if(i == 0) {
				System.out.printf("%3d%10.3f%10.3f%10.3f%11.3f%11.3f%11.3f%11s\n", i, a, b, c, fA, fB, fC, "-----");
			} else {
				System.out.printf("%3d%10.3f%10.3f%10.3f%11.3f%11.3f%11.3f%11.3f\n", i, a, b, c, fA, fB, fC, error);
			}
			
			if(Math.abs(error) < stopError) {
				System.out.print("Stop error threshold reached.\n\n");
				return;
			}
			
			if(fA * fC < 0) { 
				b = c;
				fB = fC;
			} else {
				a = c;
				fA = fC;
			}
			
			prevC = c;
		}
		
		System.out.print("Max iterations reached.\n\n");
	}

	public void newtRaph(double x, int nMax, double stopError) {
		System.out.println("NEWTON RAPHSON:");
		System.out.printf("%3s%10s%12s%13s%15s%14s%12s\n", "n", "x_n", "x_n+1", "f(x_n)", "f(x_n+1)", "f'(x_n)", "error");
		
		double prevError = 100;
		
		for(int i = 0; i < nMax; i++) {
			double fX = functAt(x);
			double fP = derivAt(x);
			double x1 = x - (fX/fP);
			double fX1 = functAt(x1);
			double error = Math.abs((x1-x)/x1);
			
			System.out.printf("%3d%10.3f%12.3f%13.3f%15.3f%14.3f%12.3f\n", i, x, x1, fX, fX1, fP, error);
			
			if(Math.abs(error) < stopError) {
				System.out.print("Stop error threshold reached.\n\n");
				return;
			}
			
			if(error > prevError+10) {
				System.out.print("Divergent solution. Try a different starting point.\n\n");
				return;
			}
			
			x = x1;
			prevError = error;
		}
		
		System.out.print("Max iterations reached.\n\n");
	}
	
	public void secant(double x0, double x, int nMax, double stopError) {
		System.out.println("SECANT:");
		System.out.printf("%3s%12s%10s%12s%17s%12s\n", "n", "x_n-1", "x_n", "x_n+1", "|f(x_n+1)|", "error");
		
		double prevError = 100;
		
		for(int i = 0; i < nMax; i++) {
			double fX0 = functAt(x0);
			double fX = functAt(x);
			double x1 = x - ((x-x0)/(fX-fX0)) * fX;
			double fX1 = Math.abs(functAt(x1));
			double error = Math.abs((x1-x)/x1);
			
			System.out.printf("%3d%12.3f%10.3f%12.3f%17.3f%12.3f\n", i, x0, x, x1, fX1, error);
			
			if(Math.abs(error) < stopError) {
				System.out.print("Stop error threshold reached.\n\n");
				return;
			}
			
			if(error > prevError+10) {
				System.out.print("Divergent solution. Try different starting points.\n\n");
				return;
			}
			
			x0 = x;
			x = x1;
			prevError = error;
		}
		
		System.out.print("Max iterations reached.\n\n");
	}

	public void modSecant(double x, double delta, int nMax, double stopError) {
		System.out.println("MODIFIED SECANT:");
		System.out.printf("%3s%10s%12s%17s%12s\n", "n", "x_n", "x_n+1", "|f(x_n+1)|", "error");
		
		double prevError = 100;

		for(int i = 0; i < nMax; i++) {
			double fX = functAt(x);
			double x1 = x - fX * ((delta*x)/(functAt(x+(delta*x))-fX));
			double fX1 = Math.abs(functAt(x1));
			double error = Math.abs((x1-x)/x1);
			
			System.out.printf("%3d%10.3f%12.3f%17.3f%12.3f\n", i, x, x1, fX1, error);
			
			if(Math.abs(error) < stopError) {
				System.out.print("Stop error threshold reached.\n\n");
				return;
			}
			
			if(error > prevError+10) {
				System.out.print("Divergent solution. Try a different starting point.\n\n");
				return;
			}
			
			x = x1;
			prevError = error;
		}
		
		System.out.print("Max iterations reached.\n\n");
	}
}
