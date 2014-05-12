package nascence.vm.thrift;

import java.nio.ByteBuffer;

import nascence.vm.VirtualMaterial;

import org.apache.thrift.TException;

import emInterfaces.*;


public class VirtualMaterialHandler implements emEvolvableMotherboard.Iface{

	VirtualMaterial vm;
	
	public VirtualMaterialHandler(VirtualMaterial vm){
		this.vm = vm;
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
	 * The reset() function in the case of RNN virtual material resets the state but keeps the weights
	 * (the weights may be not yet initialized). The weights would have to be initialized by the reprogramme() function. 
	 * the virtual material should understand the encoding of the program and use it to reconstruct the weights.
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runSequences() throws emException, TException {
		// TODO Auto-generated method stub
		
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
		return null;
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

}
