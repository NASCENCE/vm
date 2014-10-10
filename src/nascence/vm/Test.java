package nascence.vm;

import nascence.vm.io.MathEvalVarElman;

public class Test {
	public static void main(String[] arg){
	 
	 double[][] wIn = {{0.1, 0.1}, {0.1, 0.1}};
	 double[][] wRec = {{0.0, 0.0}, {0.0, 0.0}};
	 double[] wThr = {0.1, 0.1};
	 double[][] wOut = {{0.1, 0.1}, {0.1, 0.1}};
	 double[] wOutThr = {0.1, 0.1};
	 String [] exc = {"timesArray", "timesArray"};
	 String [] act = {"tanh", "tanh"};
	 double[] state = {0.1,0.1};
	 int[] periods = {1,1};
	 double[] in = {.5,-.5};
	 int [] mask = {0,1};
	 
	 VarElman ve = new VarElman();
	 ve.initVarElman(wIn, wRec, wThr, wOut, wOutThr, exc, act, exc, act, periods, state,0.0);
	 
	 ve.evalVarElman(in, 0);
	 
	 double[] out = ve.getOutput();
	 ve.setOutputMask(mask);
	 out = ve.getOutput();
	 
	 byte[] array = ve.serializeToByteArray();
	 ve.programme(array);
/*	 byte[] array2 = ve.serializeToByteArray();
	 for(int i=0;i<array2.length;i++){
		 System.out.print(array2[i]+" ");
	 }*/
	 VarElman re = new VarElman();
	 re.genVarElmanRandom(8, 32, 8, 5.0, 0.5, false, true);
	 array = re.serializeToByteArray();
	 re.programme(array);
	 
	 int[] oMask={0, 0, 0, 0, 0, 0, 0, 1};
	 double[] input ={0, 0, 0, 0, 0, 0, 0, 0}; 
	 
	 re.setOutputMask(oMask);
	 re.evalVarElman(input,0);
	 
	 re.writeToFile("/tmp/saved.vmc");
	 
	 MathEvalVarElman meve = new MathEvalVarElman();
	 meve.readFromFile("/tmp/saved.vmc");
	 meve.evaluate(input, oMask);

	 re.readFromFile("/home/koutnij/Dropbox/vm-199.vmc");
	 
	 int[] oMask2={0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
	 double[] input2 ={0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	 re.setOutputMask(oMask2);
	 
	 re.evalVarElman(input2, 0);
	 double[] output = re.getOutput();
	 for(int i=0;i<output.length;i++){
		 System.out.println(output[i]);
	 }
	 
 }
}
