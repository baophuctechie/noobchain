package com.bp.learning.blockchain.noobchain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

public class Wallet {
	
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public Wallet() {
		generateKeyPair();
	}
	
	/**
	 * 
	 */
	public void generateKeyPair() {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			
			keygen.initialize(ecSpec, random);
			KeyPair keyPair = keygen.generateKeyPair();
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}
