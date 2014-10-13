package nascence.vm.thrift;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import nascence.vm.VarElman;
import nascence.vm.VirtualMaterial;

import org.apache.thrift.TException;

import emInterfaces.*;

/**
 * This class is the wrapper for thevirtual material itself. It maps the
 * function from the Nascence API to the function of the virtual material
 * virtual class.
 * 
 * @author koutnij
 * 
 */
public class VirtualMaterialHandler implements emEvolvableMotherboard.Iface {

	VirtualMaterial vm;
	Sequence inputSequence;
	Sequence outputSequence;

	public VirtualMaterialHandler(VirtualMaterial vm) {
		this.vm = vm;
		inputSequence = new Sequence();
		// outputSequence = new Sequence();
	}

	@Override
	public int ping() throws emException, TException {
		System.out.println("Server ping");
		return 0;
	}

	@Override
	public void setLED(int index, boolean state) throws emException, TException {
		// Virtual material has no LEDs

	}

	@Override
	public String getMotherboardID() throws emException, TException {
		return vm.getID(); // get the ID from the actual VM implementation
	}

	@Override
	public String getMotherboardState() throws emException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastError() throws emException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * The reset() function in the case of RNN virtual material resets the state
	 * but keeps the weights (the weights may be not yet initialized). The
	 * weights would have to be initialized by the reprogramme() function. the
	 * virtual material should understand the encoding of the program and use it
	 * to reconstruct the weights.
	 */
	@Override
	public boolean reset() throws emException, TException {
		vm.reset();
		return true;
	}

	@Override
	public boolean reprogramme(ByteBuffer bin, int length) throws emException,
			TException {

		vm.programme(bin.array());
		return true;
	}

	@Override
	public emDebugInfo getDebugState() throws emException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearSequences() throws emException, TException {
		inputSequence = new Sequence();
		outputSequence = new Sequence();
	}

	/**
	 * This function replays the sequences to the neural network
	 * 
	 */
	@Override
	public void runSequences() throws emException, TException {
		VarElman net = (VarElman) vm;
		int nInputs = net.getNumberOfInputs();
		int nOutputs = net.getNumberOfOutputs();// should be the same as #

		double[] rawOutput;
		double[][] inputs = inputSequence.getAsArray(nInputs);
		List<Integer> unusedPins = inputSequence.getUnusedInputPins(); // output pins

		double[][] outputs = new double[inputs.length][];
			
		net.setOutputMaskIndices(nOutputs, unusedPins);

		// this needs to go in a thread to be able to wait for the result
		for (int t = 0; t < inputs.length; t++) {
			rawOutput = net.evalVarElman(inputs[t], t); // t is only good for CW-RNNs
			outputs[t] = net.getOutput(); // considers the mask as well
		}

		outputSequence = new Sequence(outputs,255,unusedPins); // 255 levels
		// unusedPins become the used pins in the case of the output
	}

	@Override
	public void stopSequences() throws emException, TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void joinSequences() throws emException, TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void appendSequenceAction(emSequenceItem Item) throws emException,
			TException {
		// TODO Auto-generated method stub

	}

	@Override
	public emWaveForm getRecording(int srcPin) throws emException, TException {
		// TODO Auto-generated method stub
		return outputSequence.getRecording(srcPin);
	}

	@Override
	public void clearRecording(int srcPin) throws emException, TException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTemperature() throws emException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLogServer(emLogServerSettings logServer) throws emException,
			TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConfigRegister(int index, int value) throws emException,
			TException {
		// TODO Auto-generated method stub

	}

}
