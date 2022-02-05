
public class GetRoots {
	public static void main(String[] args) {
		RootMethods funct1 = new Function3();
		System.out.print("FUNCTION 1\n----------------------------------------------------------------------------------------------------\n");
		funct1.bisection(1, -1.5, 50, .01);
		//funct1.falsePos(-1.5, 1, 50, .01);
		funct1.newtRaph(-1, 50, .01);
		//funct1.secant(0, 1, 100, .01);
		//funct1.modSecant(, .01, 100, .01);
		
		System.out.println();
		
		/*RootMethods funct2 = new Function2();
		System.out.print("FUNCTION 2\n----------------------------------------------------------------------------------------------------\n");
		funct2.bisection(120, 130, 50, .01);
		funct2.falsePos(120, 130, 50, .01);
		funct2.newtRaph(120, 50, .01);
		funct2.secant(120, 130, 100, .01);
		funct2.modSecant(120, .01, 100, .01);*/
	}
}

class Function1 extends RootMethods {
	public double functAt(double x) {
		return 2*Math.pow(x, 3) - 11.7*Math.pow(x, 2) + 17.7*x - 5;
	}
	public double derivAt(double x) {
		return 6*Math.pow(x, 2) - 23.4*x + 17.7;
	}
}

class Function2 extends RootMethods {
	public double functAt(double x) {
		return x + 10 - x*Math.cosh(50/x);
	}
	public double derivAt(double x) {
		return 50 * Math.sinh(50/x)/x - Math.cosh(50/x) + 1;
	}
}

class Function3 extends RootMethods {
	public double functAt(double x) {
		return Math.pow(x, 5) - Math.pow(x, 3) + 3;
	}
	public double derivAt(double x) {
		return 5*Math.pow(x, 4) - 3*Math.pow(x, 2); 
	}
}