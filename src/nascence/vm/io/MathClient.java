package nascence.vm.thrift;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import nascence.vm.VarElman;

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
 */

import emInterfaces.emEvolvableMotherboard;
import emInterfaces.emSequenceItem;
import emInterfaces.emWaveForm;

public class MathClient {
	emEvolvableMotherboard.Client client;
	TTransport transport;

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

	public void closeConnection() {
		transport.close();
	}

	public String getMotherboardID(){
		return client.getMotherboardID();
	}
	
	
	void test() throws TException {
		client.ping(); // ping the board
		System.out.println(client.getMotherboardID()); // get it's ID

		VarElman local = new VarElman(); // generate a RNN locally
		local.genVarElmanRandom(8, 32, 8, 5.0, 0.5, false, true); // at random

		byte[] ar = local.serializeToByteArray(); // get the weights
		client.reprogramme(ByteBuffer.wrap(ar), ar.length); // prg. the VM

		/*
		 * Now let's play some data through it. The VM has 8 inputs and 8
		 * outputs, let's use 7 inputs, therefore the 8th pin will be used as an
		 * output Let's use 2 steps of 7 values.
		 * 
		 * The example show how to wrap everything in the Nascence API
		 */
		client.reprogramme(ByteBuffer.wrap(ar), ar.length);

		double[][] data = { { .1, .2, .3, .4, .5, .6, .7, },
				{ .7, .6, .5, .4, .3, .2, .1 } };
		int[] inputPins = { 0, 1, 2, 3, 4, 5, 6 };
		ArrayList<Integer> pinList = new ArrayList<Integer>();
		for (int i = 0; i < inputPins.length; i++) {
			pinList.add(inputPins[i]);
		}

		// a helper function that makes waveforms from a raw array (columns)
		Sequence seq = new Sequence(data, 255, pinList);
		List<emSequenceItem> items = seq.getThisSequence();

		client.clearSequences(); // delete existing

		// push the data into the VM
		for (emSequenceItem item : items) {
			client.appendSequenceAction(item);
		}

		client.runSequences();
		client.joinSequences(); // wait for results
		emWaveForm output = client.getRecording(7); // expect the result on
													// pin 7

		List<Integer> samples = output.getSamples();

		// print out the samples (integers)
		for (int s : samples) {
			System.out.println(s + " ");
		}
	}

}
