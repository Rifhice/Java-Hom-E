package Tools;
import java.security.Key;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Security {
	//0mGL4sUP3RK3y
	private static final String key = "sUp3rK3y";
	
	/**
	 * Encrypt a string with Blowfish algorithm. 
	 * @param string, String
	 * @return String, the string encrypted correctly, else null.
	 */
	public static String encrypt(String string){
		try {
			Key clef = new SecretKeySpec(key.getBytes("ISO-8859-2"),"Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE,clef);
			return new String(cipher.doFinal(string.getBytes()));
		}
		catch (Exception e) {
	        System.out.println("ERROR encrypting string : "+e.getMessage());
			return null;
		}
	}
	
	/**
	 * Decrypt a blowfish-encoded string. 
	 * @param string, String
	 * @return String, the string decrypted, else null.
	 */
	public static String decrypt(String string){
		try {
			Key clef = new SecretKeySpec(key.getBytes("ISO-8859-2"),"Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE,clef);
			return new String(cipher.doFinal(string.getBytes()));
		}
		catch (Exception e){
			System.out.println("ERROR decrypting string : "+e.getMessage());
			return null;
		}
	}
	
}
