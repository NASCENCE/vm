package nascence.vm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;

/**
 * Variable node Elman network. As a virtual material, the network has the same
 * amount of inputs as outputs. Typically, the unused inputs will become the
 * outputs. The inputs and outputs contain different sets of weights (could have
 * been implemented such that the input/output weights are the same and are used
 * in the particular fashion.
 * 
 * @author koutnij
 * 
 */

public class VarElman extends VirtualMaterialImpl implements VirtualMaterial {

	VarFRNN varFRNN;
	double[][] wOut;
	double[] wOutThr;

	NodeFunction[] outExcFns = null;
	NodeFunction[] outActFns = null;

	BitSet outputMask = null; // bit mask for the output, the inputs are the
								// other nodes then, but the user should mask
								// them with zeroes
	double[] outputBuffer;
	double weightNoise = 0;

	public void genVarElmanRandom(int nIn, int nNodes, int nOut, double wRange,
			double p, boolean isARNN, boolean isRecurrent) {
		varFRNN = new VarFRNN();
		varFRNN.genVarFRNNrandom(nIn, nNodes, wRange, p, isARNN, isRecurrent);

		wOutThr = varFRNN.genWeightMatrix(1, nOut, wRange, p)[0];
		wOut = varFRNN.genWeightMatrix(nOut, nNodes, wRange, p);

		try {
			outExcFns = ExcFnList.genRandomExcFnList(nOut);
			outActFns = ActFnList.genRandomActFnList(nOut);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.weightNoise = weightNoise;
		reset();
	}

	public void setOutputMask(BitSet outputMask) {
		this.outputMask = outputMask;
	}

	public void setOutputMask(boolean[] outputMask) {
		this.outputMask = new BitSet(outputMask.length);
		for (int i = 0; i < outputMask.length; i++) {
			this.outputMask.set(i, outputMask[i]);
		}
	}

	public void setOutputMask(int[] outputMask) {
		this.outputMask = new BitSet(outputMask.length);
		for (int i = 0; i < outputMask.length; i++) {
			this.outputMask.set(i, outputMask[i] == 1);
		}
	}

	/**
	 * This takes a list of indices of pins to be masked
	 */
	public void setOutputMaskIndices(int maskLength, List<Integer> maskIndices) {
		this.outputMask = new BitSet(maskLength);
		this.outputMask.set(0, maskLength, false);

		for (int i : maskIndices) {
			this.outputMask.set(i, true);
		}
	}

	public int getNumberOfInputs() {
		return varFRNN.getNumberOfInputs();
	}

	public int getNumberOfOutputs() {
		return wOutThr.length;
	}

	public void clearOutputMask() {
		outputMask = null;
	}

	public void initVarElman(double[][] wIn, double[][] wRec, double[] wThr,
			double[][] wOut, double[] wOutThr, String[] excFnNames,
			String[] actFnNames, String[] outExcFnNames,
			String[] OutActFnNames, int[] periods, double[] state,
			double weightNoise) {
		varFRNN = new VarFRNN();
		varFRNN.initVarFRNN(wIn, wRec, wThr, excFnNames, actFnNames, periods,
				state, weightNoise);

		this.wOut = wOut;
		this.wOutThr = wOutThr;

		try {
			outExcFns = ExcFnList.genExcFnList(excFnNames);
			outActFns = ActFnList.genActFnList(actFnNames);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		reset();
	}

	public double[] evalVarElman(double[] in, int t) {
		double[] hiddenState = varFRNN.evalVarFRNN(in, t);
		Double[] parts = new Double[hiddenState.length + 1];
		double excitation, activation;

		for (int i = 0; i < wOut.length; i++) {
			for (int j = 0; j < hiddenState.length; j++) {
				parts[j] = (noise() + wOut[i][j]) * hiddenState[j];
			}
			parts[hiddenState.length] = noise() + wOutThr[i]; // bias
			excitation = outExcFns[i].evalExcitation(parts);
			activation = outActFns[i].evalActivation(excitation);
			outputBuffer[i] = activation;
		}
		return outputBuffer;
	}

	public void reset() {
		varFRNN.reset();
		outputBuffer = new double[wOut.length];
	}

	public void runInput(double[] input) {

	}

	public double[] getOutput() {
		double[] result;
		if (outputMask == null) { // no output mask set, report all outputs
			result = outputBuffer;
		} else { // mask the outputs and report only the non-masked out ones
			int card = outputMask.cardinality();
			result = new double[card];
			int k = 0;
			for (int i = outputMask.nextSetBit(0); i >= 0; i = outputMask
					.nextSetBit(i + 1)) {
				result[k++] = outputBuffer[i];
			}
		}
		return result;
	}

	public String getID() {
		return "Virtual Material - Elman Recurrent Neural Network";
	}

	public void programme(byte[] array) {
		System.out.print("Programming, using " + array.length + " bytes ... ");

		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		DataInputStream s = new DataInputStream(bais);
		varFRNN = new VarFRNN();
		varFRNN.programme(s);
		programme(s);

		System.out.println("Done");
		if (bais.available() > 0) {
			System.out
					.println("Warning: "
							+ bais.available()
							+ " remains unused, the byte array gt misinterpreted, things will go wrong");
		}
		try {
			s.close();
			bais.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void programme(DataInputStream s) {
		try {
			int nOut = s.readInt();
			int nNodes = varFRNN.wThr.length;

			wOut = new double[nOut][nNodes];
			for (int o = 0; o < nOut; o++) {
				for (int n = 0; n < nNodes; n++) {
					wOut[o][n] = s.readDouble();
				}
			}

			wOutThr = new double[nOut];
			for (int o = 0; o < nOut; o++) {
				wOutThr[o] = s.readDouble();
			}

			int[] ind = new int[nOut];
			for (int o = 0; o < nOut; o++) {
				ind[o] = s.readInt();
			}
			outExcFns = ExcFnList.genExcFnList(ind);
			for (int o = 0; o < nOut; o++) {
				ind[o] = s.readInt();
			}
			outActFns = ActFnList.genActFnList(ind);
			outputBuffer = new double[wOut.length];

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public byte[] serializeToByteArray() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream s = new DataOutputStream(baos);
		varFRNN.writeToStream(s);
		writeToStream(s);
		byte[] result = baos.toByteArray();
		try {
			s.close();
			baos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return result;
	}

	public void writeToStream(DataOutputStream s) {
		int nOut = wOut.length;
		int nNodes = wOut[0].length;
		try {
			s.writeInt(nOut);
			for (int o = 0; o < nOut; o++) {
				for (int n = 0; n < nNodes; n++) {
					s.writeDouble(wOut[o][n]);
				}
			}

			for (int o = 0; o < nOut; o++) {
				s.writeDouble(wOutThr[o]);
			}

			for (int o = 0; o < nOut; o++) {
				s.writeInt(outExcFns[o].code);
			}
			for (int o = 0; o < nOut; o++) {
				s.writeInt(outActFns[o].code);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double noise() {
		return weightNoise * 2 * Math.random() - 1.0;
	}
}
