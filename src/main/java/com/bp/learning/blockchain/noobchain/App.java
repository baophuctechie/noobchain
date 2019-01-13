package com.bp.learning.blockchain.noobchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.gson.GsonBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static List<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	public static Wallet walletA;
	public static Wallet walletB;
	
    public static void main( String[] args )
    {
    	// Setup Bouncey castle as a Security Provider
    	Security.addProvider(new BouncyCastleProvider());
    	// Create the new wallets
    	walletA = new Wallet();
    	walletB = new Wallet();
    	// Test public and private keys
    	System.out.println("Private and public keys:");
    	System.out.println(StringUtil.getStringFromKey(walletA.getPrivateKey()));
    	System.out.println(StringUtil.getStringFromKey(walletA.getPublicKey()));
    	// Create a test transaction from WalletA to WalletB
    	Transaction transaction = new Transaction(walletA.getPublicKey(), walletB.getPublicKey(),  5, null);
    	transaction.generateSignature(walletA.getPrivateKey());
    	//Verify the signature works and verify it from the public key
    	System.out.println("Is signature verified");
    	System.out.println(transaction.verifySignature());
    	
        /*Block genesisBlock = new Block("Hi, I'm the first block", "0");
        blockchain.add(genesisBlock);
        System.out.println("Trying to mine block 1...");
        blockchain.get(0).mineBlock(difficulty);
        
        Block secondBlock = new Block("Yo I'm the second block", genesisBlock.getHash());
        blockchain.add(secondBlock);
        System.out.println("Trying to mine block 2...");
        blockchain.get(1).mineBlock(difficulty);
        
        Block thirdBlock = new Block("Hey I'm the third block", secondBlock.getHash());
        blockchain.add(thirdBlock);
        System.out.println("Trying to mine block 3...");
        blockchain.get(2).mineBlock(difficulty);
        
        System.out.println("\nBlockchain is valid: " + isChainValid());
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);*/
    }
    
    public static boolean isChainValid() {
    	Block currentBlock;
    	Block previousBlock;
    	
    	for (int i = 1; i < blockchain.size(); i++) {
    		currentBlock = blockchain.get(i);
    		previousBlock = blockchain.get(i-1);
    		
    		if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
    			System.out.println("Current Hashes are not equal");
    			return false;
    		}
    		if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
    			System.out.println("Previous Hashes are not equal");
    			return false;
    		}
    	}
    	
    	return true;
    }
}
