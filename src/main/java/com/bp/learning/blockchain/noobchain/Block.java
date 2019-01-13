/**
 * 
 */
package com.bp.learning.blockchain.noobchain;

import java.util.Date;

/**
 * @author baophuc
 *
 */
public class Block {

	private String hash;
	private String previousHash;
	private String data; //our data will be a simple message.
	private long timeStamp; // as number of milliseconds since 1/1/1970.
	private int nonce;
	
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	
	public String calculateHash() {
		String calculatedHash = StringUtil.applySha256(previousHash +
				Long.toString(timeStamp) + Integer.toString(nonce) +
				data);
		return calculatedHash;
	}
	
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0');
		while (!getHash().substring(0, difficulty).equals(target)) {
			nonce++;
			this.hash = calculateHash();
		}
		System.out.println("Block Mined!!!: " + this.hash);
	}
	public String getHash() {
		return hash;
	}
	
	public String getPreviousHash() {
		return previousHash;
	}
}
