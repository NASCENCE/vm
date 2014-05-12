package nascence.vm;

public class Functions {

	
	public static double plus(Double x, Double y) {
		return x + y;
	}

	public static double times(Double x, Double y) {
		return x * y;
	}

	public static double sin(Double x) {
		return Math.sin(x);
	}

	public static double tanh(Double x) {
		return Math.tanh(x);
	}
	
	public static double sigmBipolar(Double x) {
		return 2/(1+Math.exp(-x))-1;
	}

	public static double plusArray(Double[] ar) {
		double result = 0;
		for (double i : ar) {
			result += i;
		}
		return result;
	}

	public static double timesArray(Double[] ar) {
		double result = ar[0];
		for (int i = 1; i < ar.length; i++) {
			result *= ar[i];
		}
		return result;
	}

}
