package com.bp.learning.blockchain.noobchain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

	public String transactionId;
	public PublicKey sender;
	public PublicKey reciepient;
	public float value;
	public byte[] signature;
	
	public List<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence = 0;
	
	public Transaction(PublicKey sender, PublicKey reciepient, 
			float value, ArrayList<TransactionInput> inputs) {
		this.sender = sender;
		this.reciepient = reciepient;
		this.value = value;
		this.inputs = inputs;
	}
	
	private String calculateHash() {
		sequence++;
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) +
				StringUtil.getStringFromKey(reciepient) +
				Float.toString(value) + sequence);
	}
	
	/**
	 * Sign all the data we don't wish to be tampered with.
	 * @param privateKey The private key
	 */
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(this.sender) +
				StringUtil.getStringFromKey(this.reciepient) +
				Float.toString(this.value);
		this.signature = StringUtil.applyECDSAig(privateKey, data);
	}
	
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(this.sender) +
				StringUtil.getStringFromKey(this.reciepient) +
				Float.toString(this.value);
		return StringUtil.verifyECDSASig(this.sender, data, signature);
				
	}
}
