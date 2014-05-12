package nascence.vm;

public abstract interface VirtualMaterial {
	public abstract void reset();
	public abstract void runInput(double[] input);
	public abstract double[] getOutput();
	public abstract String getID();
	public abstract void programme(byte[] array);
	public abstract byte[] serializeToByteArray();
}
