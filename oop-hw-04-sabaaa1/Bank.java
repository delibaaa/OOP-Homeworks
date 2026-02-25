// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	static BlockingQueue<Transaction> transactions ;
	private final Transaction nullTrans = new Transaction(-1,0,0);
	private static final int DEFAULT_BALANCE = 1000;
    Account[] accounts;
	CountDownLatch latch;
	
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file)  {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				// Use the from/to/amount
				int amount = (int)tokenizer.nval;
				transactions.put(new Transaction(from, to, amount));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) throws InterruptedException {
		latch = new CountDownLatch(numWorkers);
		Thread[] workers = new Thread[numWorkers];
		for (int i = 0; i < numWorkers; i++) {
			workers[i] = new Thread(new Worker());
			workers[i].start();
		}
		readFile(file);
		for (int i = 0; i < numWorkers; i++) {
			transactions.put(nullTrans);
		}
		latch.await();
		for (Account acc : accounts) {
			System.out.println(acc);
		}
	}
	class Worker extends Thread{

		@Override
		public void run() {
			try {
				while(true) {
					Transaction currTransaction = transactions.take();
					if (currTransaction.getFrom() ==-1 && currTransaction.getTo() ==0 && currTransaction.getAmount() ==0) break;
					accounts[currTransaction.getFrom()].withDraw(currTransaction.getAmount());
					accounts[currTransaction.getTo()].add(currTransaction.getAmount());
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				latch.countDown();
			}
		}
	}

	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) throws InterruptedException {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}

		transactions = new ArrayBlockingQueue<>(Math.max(64,numWorkers * 8));
		Bank bank = new Bank();
		bank.accounts = new Account[ACCOUNTS];
		for(int i = 0 ; i < ACCOUNTS; i++){
			bank.accounts[i] = new Account(bank, i, DEFAULT_BALANCE);
		}
		bank.processFile(file, numWorkers);
	}
}

