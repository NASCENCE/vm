package nascence.vm;

import java.util.EnumSet;

public class ActFnList {
	public static enum ActFns {
		tanh, sin, sigmBipolar // these functions are looked up in Functions
									// class
	}

	public static final EnumSet<ActFns> actFns = EnumSet.allOf(ActFns.class);

	public static NodeFunction[] genRandomActFnList(int length)
			throws SecurityException, NoSuchMethodException {
		NodeFunction[] result = new NodeFunction[length];
		Object[] ea = actFns.toArray();
		Functions fns = new Functions();
		// Object[] args = new Double[0];
		Double args = new Double(0);

		for (int i = 0; i < length; i++) {
			int fni = (int) Math.floor(actFns.size() * Math.random());
			//System.out.println(ea[fni].toString());
			result[i] = new NodeFunction(fns, fns.getClass().getMethod(
					ea[fni].toString(), args.getClass()),fni);
		}
		return result;
	}

	public static NodeFunction[] genActFnList(String[] actFnNames)
			throws SecurityException, NoSuchMethodException {
		NodeFunction[] result = new NodeFunction[actFnNames.length];
		Functions fns = new Functions();
		Double args = new Double(0);
		for (int i = 0; i < result.length; i++) {
			result[i] = new NodeFunction(fns, fns.getClass().getMethod(
					actFnNames[i], args.getClass()),actFnIndex(actFnNames[i]));
		}
		return result;
	}
	
	public static NodeFunction[] genActFnList(int[] actFnIndices)
			throws SecurityException, NoSuchMethodException {
		NodeFunction[] result = new NodeFunction[actFnIndices.length];
		Object[] ea = actFns.toArray();
		Functions fns = new Functions();
		Double args = new Double(0);
		for (int i = 0; i < actFnIndices.length; i++) {
			int fni = actFnIndices[i];
			//System.out.println(ea[fni].toString());
			result[i] = new NodeFunction(fns, fns.getClass().getMethod(
					ea[fni].toString(), args.getClass()),fni);
		}
		return result;
	}
	
	static int actFnIndex(String excFnName) {
		Object[] ea = actFns.toArray();
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
}
