//package cs144;

import java.io.*;
import java.security.*;

public class ComputeSHA {
	public static void computeSHA(String str) {
		try {
			MessageDigest algo = MessageDigest.getInstance("SHA-1");
			algo.update(str.getBytes());
			System.out.println(bytes2hex(algo.digest()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String file_proc(String filename) {
		File file = new File(filename);
		StringBuilder sb = new StringBuilder();
		try {
			InputStream f = new FileInputStream(file);
			int size = f.available();
			for(int i = 0; i < size; i++) {
				sb.append((char)f.read());
			}
			f.close();
		} catch (FileNotFoundException e) {
			System.out.print("FileNotFoundException");
        } catch (IOException e) {
			System.out.print("IOException");
		}
		return sb.toString();
	}
	
	private static String bytes2hex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}
		return sb.toString();
	}
	
	public static void main(String args[]) {
		computeSHA(file_proc(args[0]));
	}
}
