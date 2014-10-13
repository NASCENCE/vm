package nascence.vm.thrift;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import emInterfaces.emException;
import emInterfaces.emSequenceItem;
import emInterfaces.emWaveForm;
import nascence.vm.VarElman;

public class TestHandler {
	public TestHandler() {
		VarElman local = new VarElman(); // generate a RNN locally
		local.genVarElmanRandom(8, 32, 8, 5.0, 0.5, false, true); // at random
		VirtualMaterialHandler vmh = new VirtualMaterialHandler(local);
		byte[] ar = local.serializeToByteArray();
		try {
			vmh.reprogramme(ByteBuffer.wrap(ar), ar.length);

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

			vmh.clearSequences(); // delete existing

			// push the data into the VM
			for (emSequenceItem item : items) {
				vmh.appendSequenceAction(item);
			}

			vmh.runSequences();
			
			vmh.joinSequences(); // wait for results

			emWaveForm output = vmh.getRecording(7); // expect the result on
														// pin 7

			List<Integer> samples = output.getSamples();

			// print out the samples (integers)
			for (int s : samples) {
				System.out.print(s + " ");
			}
			System.out.println();

		} catch (emException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] arg) {
		TestHandler th = new TestHandler();
	}
}
