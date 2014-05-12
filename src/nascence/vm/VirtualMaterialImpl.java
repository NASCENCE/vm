package nascence.vm;

import nascence.vm.io.FileIO;

public abstract class VirtualMaterialImpl implements VirtualMaterial{
	public void writeToFile(String fileName) {
		FileIO.write(serializeToByteArray(), fileName);
	}

	public void readFromFile(String fileName) {
		programme(FileIO.read(fileName));
	}	
}
