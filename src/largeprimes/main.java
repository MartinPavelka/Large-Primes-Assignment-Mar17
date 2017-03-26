package largeprimes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class main {
	
	private static final String FILENAME = "primes.txt";
	private static int [] sequence = new int[22];
	private static int [] counter = new int[20];;
	
	/*
	 *  Adds number to the sequence
	 *  
	 *  @param	number	the number to be added
	 */
	private static void pushNumberToSequence(int number) {
		for (int i=sequence.length-1; i>=0; i--) {
			if (i==0)
				sequence[i] = number;
			else
				sequence[i] = sequence[i-1];
		}
	}
	
	/*
	 *  Checks if the sequence contains a pattern and if it does, adds to counter
	 *  patterns: 515, 5115, 51115, ...,  5(20*'1')5
	 */
	private static void checkSequenceForMatch() {
		if (sequence[0] != 5)
			return;
		if (sequence[1] == 5){
			return;
		}

		for (int i=2; i<=sequence.length-1; i++) {
			if (sequence[i] == 5){
				counter[i-2] = counter[i-2]+1;
				break;
			}
		};
	}
	
	/*
	 *  Generates string from pattern based on ones count
	 *  
	 *  @param	onesCount	count of ones
	 */
	private static String genSeqStr(int onesCount) {
		String ret = "";
		ret = ret + "5";
		for (int i=0; i<onesCount; i++){
			ret = ret + "1";
		}
		ret = ret + "5";
		return ret;
		
	};
	
	public static void main(String[] args) {
		int numbersCount = 0;
		int linesCount = 0;

		// Initialize arrays
		for (int i=0;i<sequence.length;i++)
			sequence[i] = 0;
		for (int i=0;i<counter.length;i++)
			counter[i] = 0;

		BufferedReader br = null;
		FileReader fr = null;
		try {
			String sCurrentLine;
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			while ((sCurrentLine = br.readLine()) != null) {
				linesCount++;

				sCurrentLine = sCurrentLine.trim();
				String [] sNumbers = sCurrentLine.split(" ");
				for (int i=0; i<sNumbers.length; i++) {
					// Each number string of the line
					int number;
					try {
						number = Integer.parseInt(sNumbers[i]);
					} catch(NumberFormatException e) {
						continue;
					}
					numbersCount++;
					int result = number % 6;
					
					pushNumberToSequence(result);
					checkSequenceForMatch();
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// Print results
		System.out.println("Read " + Integer.toString(numbersCount) + " numbers in " + Integer.toString(linesCount)+ " lines.");
		for(int i = 0; i<counter.length; i++) {
			System.out.println(Integer.toString(counter[i]) + "x\t" + genSeqStr(i+1) );
		}
	}
	

}
