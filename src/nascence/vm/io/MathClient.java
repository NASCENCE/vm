package nascence.vm.io;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import nascence.vm.VarElman;
import nascence.vm.thrift.Sequence;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

//import shared.SharedStruct;

/**
 * 
 * @author koutnij
 *
 * Nascence API client for Mathematica
 * This is a wrapper. A user does not have to assemble all the objects
 * inside Mathematica.
 * 
 * The wrapper can be used from other java code as well, it is not restricted
 * to Mathematica only.
 */


import emInterfaces.emEvolvableMotherboard;
import emInterfaces.emException;
import emInterfaces.emSequenceItem;
import emInterfaces.emWaveForm;

public class MathClient {
	emEvolvableMotherboard.Client client;
	TTransport transport;
	int nInputs;
	int nOutputs;

	/**
	 * Constructor that can be called from any other program. Connects to a
	 * virtual material client and to a log server.
	 */
	public MathClient(String url, int portNumber) {
		try {
			transport = new TSocket(url, portNumber);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			client = new emEvolvableMotherboard.Client(protocol);
		} catch (TException x) {
			x.printStackTrace();
		}
	}

	/**
	 * Close the transport.
	 */
	public void closeConnection() {
		transport.close();
	}

	public String getMotherboardID() throws emException, TException {
		return client.getMotherboardID();
	}

	/**
	 * This function generates a VarElman VM from the matrices specified and
	 * programmes the VM server with it
	 * 
	 * @param wIn
	 * @param wRec
	 * @param wThr
	 * @param wOut
	 * @param wOutThr
	 * @param excFnNames
	 * @param actFnNames
	 * @param outExcFnNames
	 * @param OutActFnNames
	 * @param periods
	 * @param state
	 * @param weightNoise
	 * @throws TException 
	 * @throws emException 
	 */
	public void programmeVarElman(double[][] wIn, double[][] wRec, double[] wThr,
			double[][] wOut, double[] wOutThr, String[] excFnNames,
			String[] actFnNames, String[] outExcFnNames,
			String[] OutActFnNames, int[] periods, double[] state,
			double weightNoise) throws emException, TException {

		VarElman local = new VarElman();
		nInputs = wIn[0].length;
		nOutputs = wOutThr.length;
		local.initVarElman(wIn, wRec, wThr, wOut, wOutThr, excFnNames,
				actFnNames, outExcFnNames, OutActFnNames, periods, state,
				weightNoise);
		programme(local, client);

	}

	public void programme(VarElman net, emEvolvableMotherboard.Client cl) throws emException, TException {
		byte[] ar = net.serializeToByteArray();
		cl.reprogramme(ByteBuffer.wrap(ar), ar.length);
	}

	public void programmeVarElmanRandom(int nIn, int nNodes, int nOut, double wRange,
			double p, boolean isARNN, boolean isRecurrent) throws emException, TException {
		VarElman local = new VarElman();

		nInputs = nIn;
		nOutputs = nOut;
		local.genVarElmanRandom(nIn, nNodes, nOut, 5.0, 0.5, false, false);
		programme(local, client);
	}

	/**
	 * Transforms an array to emSequenceItem sequence and sends it to the VM.
	 * Returns The results as an array as well. The input pins are chosen by a
	 * separate array of the pin numbers. All other pins are used as output
	 * pins.
	 * @throws TException 
	 * @throws emException 
	 */

	public double[][] evaluateArray(double[][] data, int amplitude, int[] inputPins) throws emException, TException {
		ArrayList<Integer> pinList = new ArrayList<Integer>();
		ArrayList<Integer> pinListOutput = new ArrayList<Integer>();

		boolean[] usedInputPins = new boolean[nInputs];

		for (int i = 0; i < usedInputPins.length; i++) {
			usedInputPins[i] = false;
		}

		for (int i = 0; i < inputPins.length; i++) {
			pinList.add(inputPins[i]);
			usedInputPins[inputPins[i]] = true;
		}

		// complementary output pins
		for (int i = 0; i < usedInputPins.length; i++) {
			if (!usedInputPins[i]) {
				pinListOutput.add(i);
			}
		}

		// a helper function that makes waveforms from a raw array (columns)
		Sequence seq = new Sequence(data, amplitude, pinList);
		List<emSequenceItem> items = seq.getThisSequence();

		client.clearSequences(); // delete existing

		// push the data into the VM
		for (emSequenceItem item : items) {
			client.appendSequenceAction(item);
		}

		client.runSequences(); // run VM
		client.joinSequences(); // wait for results

		// read all the output waveforms
		double[][] output = new double[data.length][pinListOutput.size()];

		emWaveForm w;
		List<Integer> samples;
		double v;
		// read and transpose
		for (int p = 0; p < pinListOutput.size(); p++) {

			w = client.getRecording(pinListOutput.get(p));
			samples = w.getSamples();
			for (int i = 0; i < samples.size(); i++) {
				v = ((double) samples.get(i)) / amplitude;
				output[i][p] = v;
			}
		}
		return output;
	}

	public void test() throws TException {
		client.ping(); // ping the board
		System.out.println(getMotherboardID()); // get it's ID
		
		double[][] data = { { .1, .2, .3, .4, .5, .6, .7, },
				{ .7, .6, .5, .4, .3, .2, .1 } };
		int[] inputPins = { 0, 1, 2, 3, 4, 5, 6 };
		
		programmeVarElmanRandom(8, 32, 8, 5.0, 0.5, false, false);
		
		double[][] result = evaluateArray(data,255,inputPins);
		
		for(int i=0;i<result.length;i++){
			for(int j=0;j<result[0].length;j++){
				System.out.print(result[i][j]+" ");
			}
			System.out.println();
		}
		
		closeConnection();
	}
	
	public static void main(String[] arg){
		MathClient mc = new MathClient("localhost",9090);
		try {
			mc.test();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
