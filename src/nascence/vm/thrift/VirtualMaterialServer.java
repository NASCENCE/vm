package nascence.vm.thrift;

import nascence.vm.VarElman;
import nascence.vm.VirtualMaterial;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
//import org.apache.thrift.server.TThreadPoolServer;
//import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
//import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;


import emInterfaces.emEvolvableMotherboard;

public class VirtualMaterialServer {

	public static VirtualMaterialHandler handler;

	public static emEvolvableMotherboard.Processor processor;

	public static void main(String[] args) {
		try {
			VirtualMaterial vm = new VarElman(); // let's test with Elman RNN
			
			handler = new VirtualMaterialHandler(vm);
			processor = new emEvolvableMotherboard.Processor(handler);

			Runnable simple = new Runnable() {
				public void run() {
					simple(processor);
				}
			};

			new Thread(simple).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static void simple(emEvolvableMotherboard.Processor processor) {
		try {
			TServerTransport serverTransport = new TServerSocket(9090);
			TServer server = new TSimpleServer(
					new Args(serverTransport).processor(processor));

			System.out.println("Starting the VirtualMaterial server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
