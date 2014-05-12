package nascence.vm;

import java.util.EnumSet;

public class ExcFnList {
	public static enum ExcFns {
		plusArray, timesArray // these functions are looked up in Functions
								// class
	}

	public static final EnumSet<ExcFns> excFns = EnumSet.allOf(ExcFns.class);

	public static NodeFunction[] genRandomExcFnList(int length)
			throws SecurityException, NoSuchMethodException {
		NodeFunction[] result = new NodeFunction[length];
		Object[] ea = excFns.toArray();
		Functions fns = new Functions();
		Object[] args = new Double[1];
		args[0] = new Double(0);

		for (int i = 0; i < length; i++) {
			int fni = (int) Math.floor(excFns.size() * Math.random());
			//System.out.println(ea[fni].toString());
			result[i] = new NodeFunction(fns, fns.getClass().getMethod(
					ea[fni].toString(), args.getClass()), fni);
		}
		return result;
	}

	public static NodeFunction[] genExcFnList(String[] excFnNames)
			throws SecurityException, NoSuchMethodException {
		NodeFunction[] result = new NodeFunction[excFnNames.length];
		Functions fns = new Functions();
		Object[] args = new Double[1];
		args[0] = new Double(0);
		for (int i = 0; i < result.length; i++) {

			// the x has to be matched from the list
			result[i] = new NodeFunction(fns, fns.getClass().getMethod(
					excFnNames[i], args.getClass()), excFnIndex(excFnNames[i])); // fix
		}
		return result;
	}

	public static NodeFunction[] genExcFnList(int[] excFnIndices)
			throws SecurityException, NoSuchMethodException {
		NodeFunction[] result = new NodeFunction[excFnIndices.length];
		Object[] ea = excFns.toArray();
		Functions fns = new Functions();
		Object[] args = new Double[1];
		args[0] = new Double(0);
		for (int i = 0; i < excFnIndices.length; i++) {
			int fni = excFnIndices[i];
			//System.out.println(ea[fni].toString());
			result[i] = new NodeFunction(fns, fns.getClass().getMethod(
					ea[fni].toString(), args.getClass()), fni);
		}
		return result;
	}

	/**
	 * returns -1 for not found
	 * 
	 * @param excFnName
	 * @return
	 */
	static int excFnIndex(String excFnName) {
		Object[] ea = excFns.toArray();
		int i = 0;
		int diff = -1;
		int index = -1;
		do {
			diff = ea[i].toString().compareTo(excFnName);
			if (diff == 0) {
				index = i;
			}
			i++;
		} while (i < ea.length && diff != 0);

		return index;
	}

	public static void main(String[] arg) {
		System.out.println(excFnIndex("timesArray"));

		// example of a list of excitation functions:
		/*
		 * ExcFnList efl = new ExcFnList(); ActFnList afl = new ActFnList();
		 * 
		 * try { Double[] data = { 1.1, 2.1, (double) -3, 4.5 }; NodeFunction[]
		 * excFunctions = efl.genRandomExcFnList(10); NodeFunction[]
		 * actFunctions = afl.genRandomActFnList(data.length);
		 * 
		 * // excitation functions operate on 1D arrays for (NodeFunction i :
		 * excFunctions) { System.out.println(i.evalExcitation(data)); } //
		 * activation functions operate on scalars (double) for (int i = 0; i <
		 * actFunctions.length; i++) {
		 * System.out.println(actFunctions[i].evalActivation(data[i])); }
		 * 
		 * } catch (SecurityException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (NoSuchMethodException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

}
