/**
 * 
 */
package com.bp.learning.blockchain.noobchain;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * @author baophuc
 *
 */
public class StringUtil {

	// Apply Sha256 to a string and returns the result.
	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Apply sha256 to our input
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // this will contain hash as hexadecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Applies ECDSA Signature and returns the result (as bytes)
	 * @param privateKey The private key
	 * @param input The input
	 * @return the Signature (as bytes)
	 */
	public static byte[] applyECDSAig(PrivateKey privateKey, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return output;
	}
	
	/**
	 * Verify the Signature based on ECDSA
	 * @param publicKey The public key
	 * @param data The data included
	 * @param signature The signature to be verified with the public key
	 * @return the boolean value (as true | false)
	 */
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
}
}
