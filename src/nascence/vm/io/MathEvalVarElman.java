package nascence.vm.io;

import nascence.vm.VarElman;

public class MathEvalVarElman {
	VarElman ve;

	public MathEvalVarElman() {
		ve = new VarElman();
	}

	public double[] evaluate(double[][] wIn, double[][] wRec, double[] wThr,
			double[][] wOut, double[] wOutThr, String[] excFnNames,
			String[] actFnNames, String[] outExcFnNames,
			String[] OutActFnNames, int[] periods, double[] state,
			double[] input, int[] mask, double weightNoise) {

		ve.initVarElman(wIn, wRec, wThr, wOut, wOutThr, excFnNames, actFnNames,
				outExcFnNames, OutActFnNames, periods, state, weightNoise);
		ve.setOutputMask(mask);
		ve.evalVarElman(input, 0);
		double[] rr= ve.getOutput();
		double[] result = new double[rr.length];
		System.arraycopy(rr, 0, result, 0, rr.length);
		
		return result;
	}

	public void setup(double[][] wIn, double[][] wRec, double[] wThr,
			double[][] wOut, double[] wOutThr, String[] excFnNames,
			String[] actFnNames, String[] outExcFnNames,
			String[] OutActFnNames, int[] periods, double[] state, double weightNoise) {
		ve.initVarElman(wIn, wRec, wThr, wOut, wOutThr, excFnNames, actFnNames,
				outExcFnNames, OutActFnNames, periods, state, weightNoise);
	}

	public double[] evaluate(double[] input, int[] mask) {
		ve.setOutputMask(mask);
		double[] result = ve.evalVarElman(input, 0);
		/*
		 * for (int j = 0; j < mask.length; j++) { System.out.print(input[j] +
		 * " " + mask[j] + "  "); } System.out.println(" -> " + result[0]);
		 */
		return result;
	}

	public double[][] evaluateList(double[][] input, int[][] mask) {
		double[][] result = new double[input.length][];
		double[] rr;

		for (int i = 0; i < input.length; i++) {
			ve.setOutputMask(mask[i]);
			//rr = ve.evalVarElman(input[i], 0); // was rr =, not good, no mask
			ve.evalVarElman(input[i], 0); // t = 0 (FWD net for the moment)
			rr= ve.getOutput();
			/*
			 * for (int j = 0; j < mask[0].length; j++) {
			 * System.out.print(input[i][j] + " " + mask[i][j] + "  "); }
			 */
			result[i] = new double[rr.length];
			System.arraycopy(rr, 0, result[i], 0, rr.length);
			// System.out.println(" -> " + result[i][0]);
		}

		/*
		 * for (int i = 0; i < result.length; i++) { for (int j = 0; j <
		 * result[0].length; j++) { System.out.print(result[i][0] + " "); }
		 * System.out.println(); }
		 */

		return result;
	}
	public void writeToFile(String fileName) {
		ve.writeToFile(fileName);
	}

	public void readFromFile(String fileName) {
		ve.readFromFile(fileName);
	}	
}
