package nascence.vm.thrift;

import java.nio.ByteBuffer;

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
 * The client purpose is:
 * - to test the VirtualMaterialServer and
 * - to provide a wrapper for Mathematica, which does not have a thrift connector, but can call java methods natively 
 */

import emInterfaces.emEvolvableMotherboard;

public class VirtualMaterialClient {
	emEvolvableMotherboard.Client client;
	TTransport transport;

	/**
	 * Constructor that can be called from any other program. Connects to a
	 * virtual material client and to a log server.
	 */
	public VirtualMaterialClient(String url, int portNumber) {
		try {
			transport = new TSocket(url, portNumber);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			client = new emEvolvableMotherboard.Client(protocol);
		} catch (TException x) {
			x.printStackTrace();
		}
	}
	public void closeConnection(){
		transport.close();

	}

	public static void main(String[] args) {
        // localhost test
		VirtualMaterialClient vmc = new VirtualMaterialClient("localhost",9090);
   	    try {
			vmc.test();
		} catch (TException e) {
   		    e.printStackTrace();
		}

		vmc.closeConnection();
		
		
	}

	void test()
			throws TException {
		client.ping(); // ping the board
		System.out.println(client.getMotherboardID()); // get it's ID

		VarElman local = new VarElman(); // generate a RNN locally
		local.genVarElmanRandom(8, 32, 8, 5.0, 0.5, false); // at random

		byte[] ar = local.serializeToByteArray(); // get the weights
		client.reprogramme(ByteBuffer.wrap(ar), ar.length); // programme the
															// board

	}
}
