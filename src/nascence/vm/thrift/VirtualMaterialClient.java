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
	public static void main(String[] args) {
		String url = "localhost";

		if (args.length > 1) {
			url = args[0];
		}

		try {
			TTransport transport;

			transport = new TSocket(url, 9090);
			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			emEvolvableMotherboard.Client client = new emEvolvableMotherboard.Client(
					protocol);

			test(client);

			transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
	}

	private static void test(emEvolvableMotherboard.Client client)
			throws TException {
		client.ping(); // ping the board
		System.out.println(client.getMotherboardID()); // get it's ID

		VarElman local = new VarElman(); // generate a RNN locally
		local.genVarElmanRandom(8, 32, 8, 5.0, 0.5, false); // at random

		byte[] ar = local.serializeToByteArray(); // get the weights
		client.reprogramme(ByteBuffer.wrap(ar), ar.length); // programme the board

	}
}
