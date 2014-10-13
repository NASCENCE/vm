package nascence.vm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VarFRNN implements VirtualMaterial {

	NodeFunction[] excFns = null;
	NodeFunction[] actFns = null;
	double[][] wIn;
	double[][] wRec;
	double[] wThr;
	int[] periods;
	double[] state;
	double[] stateTmp;
	int t; // current time
	boolean isRecurrent = true; // if false, the recurrent weight matrix
								// multiplication is skipped
	double weightNoise=0;

	/**
	 * This function generates the VarFRNN
	 * 
	 * @param nIn
	 *            number of inputs
	 * @param nNodes
	 *            total number of computing nodes (neurons), the output nodes
	 *            would be the first nOut of them
	 * @param wRange
	 *            weight magnitude
	 * @param p
	 *            probability of a connection (weight)
	 * @param excFnList
	 *            a list of excitation functions
	 * @param actFnList
	 *            a list of activation functions
	 * @param periods
	 *            execution periods (a list of 1 -> FRNN, non-ones -> ARNN)
	 */
	public void genVarFRNNrandom(int nIn, int nNodes, double wRange, double p,
			boolean isARNN, boolean isRecurrent) {
		wIn = genWeightMatrix(nNodes, nIn, wRange, p);
		if(isRecurrent){
			wRec = genWeightMatrix(nNodes, nNodes, wRange, p);
		}else{
			wRec = genWeightMatrix(nNodes, nNodes, wRange, 0.0); // zero probability of the connection
		}
		wThr = genWeightMatrix(1, nNodes, wRange, p)[0];
		try {
			excFns = ExcFnList.genRandomExcFnList(nNodes);
			actFns = ActFnList.genRandomActFnList(nNodes);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		periods = new int[nNodes];
		if (isARNN) {
			// should random sorted periods, change the code!
			for (int i = 0; i < nNodes; i++) {
				periods[i] = 1;
			}
		} else {
			// 1
			for (int i = 0; i < nNodes; i++) {
				periods[i] = 1;
			}
		}
		state = new double[nNodes];
		stateTmp = new double[nNodes];

		reset();
	}

	public void initVarFRNN(double[][] wIn, double[][] wRec, double[] wThr,
			String[] excFnNames, String[] actFnNames, int[] periods,
			double[] state, double weightNoise) {
		this.wIn = wIn;
		this.wRec = wRec;
		isRecurrent = !possibleZeroQ(this.wRec);

		this.wThr = wThr;
		this.periods = periods;
		this.state = state;
		stateTmp = new double[state.length];
		try {
			excFns = ExcFnList.genExcFnList(excFnNames);
			actFns = ActFnList.genActFnList(actFnNames);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.weightNoise= weightNoise;
		reset();
	}
	
	public int getNumberOfInputs(){
		return wIn[0].length;
	}

	public double[] evalVarFRNN(double[] in, int t) {
		int nNodes = state.length;
		Double[] parts;
		if (isRecurrent) {
			parts = new Double[in.length + state.length + 1];
		} else {
			parts = new Double[in.length + 1];
		}

		double excitation, activation;
		for (int i = 0; i < nNodes; i++) {
			if (0 == (t % periods[i])) { // update node state iff it's time
				// input
				for (int j = 0; j < in.length; j++) {
					parts[j] = (noise()+wIn[i][j]) * in[j];
					// System.out.println(wIn[i][j] +"*"+ in[j]+"="+parts[j]);
				}
				// recurrent
				if (isRecurrent) {
					//System.out.println("Using Recurrent weights");
					for (int j = 0; j < state.length; j++) {
						parts[in.length + j] = (noise()+wRec[i][j]) * state[j];
						// System.out.println(wRec[i][j] +"*"+
						// state[j]+"="+parts[in.length + j]);
					}
				}
				// bias
				parts[parts.length - 1] = noise()+wThr[i];
				// System.out.println(parts[in.length + state.length]);
				// excitation function
				excitation = excFns[i].evalExcitation(parts);
				// System.out.println("excitation = "+excitation);
				activation = actFns[i].evalActivation(excitation);
				// System.out.println("activation = "+activation);
				stateTmp[i] = activation;
			} else {
				stateTmp[i] = state[i];
			}
		}
		for (int i = 0; i < nNodes; i++) {
			state[i] = stateTmp[i];
		}
		return state;
	}

	double[][] genWeightMatrix(int nOutputs, int nInputs, double wRange,
			double p) {
		double[][] result = new double[nOutputs][nInputs];
		for (int i = 0; i < nOutputs; i++) {
			for (int j = 0; j < nInputs; j++) {
				if (Math.random() < p) {
					result[i][j] = wRange * (2 * Math.random() - 1.0);
				} else {
					result[i][j] = 0;
				}
			}
		}
		return result;
	}

	public void reset() {
		for (int i = 0; i < state.length; i++) {
			state[i] = 0;
		}
		t = 0;
	}

	public void runInput(double[] input) {
		evalVarFRNN(input, t++);
	}

	public double[] getOutput() {
		return state;
	}

	public String getID() {
		return "Virtual Material - Fully Connected Recurrent Neural Network";
	}

	public void programme(byte[] array) {
		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		DataInputStream s = new DataInputStream(bais);
		programme(s);
		System.out.println("Programmed, using " + array.length + " bytes");
		if (bais.available() > 0) {
			System.out.println("Warning: " + bais.available()
					+ " remains unused, things will go wrong");
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
			int nIn = s.readInt();
			int nNodes = s.readInt();
			wIn = new double[nNodes][nIn];
			for (int n = 0; n < nNodes; n++) {
				for (int i = 0; i < nIn; i++) {
					wIn[n][i] = s.readDouble();
				}
			}
			wRec = new double[nNodes][nNodes];
			for (int n = 0; n < nNodes; n++) {
				for (int n2 = 0; n2 < nNodes; n2++) {
					wRec[n][n2] = s.readDouble();
				}
			}
			isRecurrent = !possibleZeroQ(this.wRec);

			wThr = new double[nNodes];
			for (int n = 0; n < nNodes; n++) {
				wThr[n] = s.readDouble();
			}

			int[] ind = new int[nNodes];
			for (int n = 0; n < nNodes; n++) {
				ind[n] = s.readInt();
			}
			excFns = ExcFnList.genExcFnList(ind);
			for (int n = 0; n < nNodes; n++) {
				ind[n] = s.readInt();
			}
			actFns = ActFnList.genActFnList(ind);
			periods = new int[nNodes];
			for (int n = 0; n < nNodes; n++) {
				periods[n] = s.readInt();
			}

			// the state is programmed as well
			state = new double[nNodes];
			stateTmp = new double[nNodes];
			for (int n = 0; n < nNodes; n++) {
				state[n] = s.readDouble();
			}
			t = s.readInt(); // no reset, time get read as well

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] serializeToByteArray() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream s = new DataOutputStream(baos);
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
		int nIn = wIn[0].length;
		int nNodes = wIn.length;
		try {
			s.writeInt(nIn);
			s.writeInt(nNodes);
			for (int n = 0; n < nNodes; n++) {
				for (int i = 0; i < nIn; i++) {
					s.writeDouble(wIn[n][i]);
				}
			}
			for (int n = 0; n < nNodes; n++) {
				for (int n2 = 0; n2 < nNodes; n2++) {
					s.writeDouble(wRec[n][n2]);
				}
			}
			for (int n = 0; n < nNodes; n++) {
				s.writeDouble(wThr[n]);
			}
			for (int n = 0; n < nNodes; n++) {
				s.writeInt(excFns[n].code);
			}
			for (int n = 0; n < nNodes; n++) {
				s.writeInt(actFns[n].code);
			}
			for (int n = 0; n < nNodes; n++) {
				s.writeInt(periods[n]);
			}
			for (int n = 0; n < nNodes; n++) {
				s.writeDouble(state[n]);
			}
			s.writeInt(t);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Checks whether the matrix is a zero matrix
	 * 
	 * @param matrix
	 * @return
	 */

	public static boolean possibleZeroQ(double[][] matrix) {
		double error = 1e-100;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (Math.abs(matrix[i][j]) > error) {
					return false;
				}
			}
		}
		return true;
	}
	public double noise(){
		return weightNoise*2*Math.random()-1.0;
	}
}
