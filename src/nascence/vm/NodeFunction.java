package nascence.vm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NodeFunction {
	Object object;
	Method method;
	int code; // for serialization

	public NodeFunction(Object object, Method method,int code) {
		this.object = object;
		this.method = method;
		this.code = code;
	}

	public double evalExcitation(Double[] array) {
		double result;
		Object[] args = new Object[1];
		args[0] = array;
		try {
			result = (Double) method.invoke(object, args);
			return result;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 0.;
	}

	public double evalActivation(Double exc) {
		double result;
		Object[] args = new Object[1];
		args[0] = exc;
		try {
			result = (Double) method.invoke(object, args);
			return result;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 0.;
	}

}
