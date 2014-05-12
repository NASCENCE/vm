package nascence.vm.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIO {
	public static void write(byte[] array, String fileName){
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(array);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static byte[] read(String fileName){
		try {
			File f = new File(fileName);
			int fl = (int)f.length();
			
			byte[] buffer = new byte[fl];
			
			FileInputStream fis = new FileInputStream(fileName);
			
			fis.read(buffer,0,fl);
			fis.close();
			
			System.out.println(fl + " bytes read from "+fileName);
			return buffer;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
