import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Scrabble {
	
	private static int maxScore = 0;
	private static String maxWord = "";
	
	// Main function calling all the other functions
	public static void main(String[] args) {
	
		String rack = "aaazeta";
		int[] scores = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26};
		char[] rackArr = rack.toCharArray();
		Arrays.sort(rackArr);
		ArrayList<String> words = getWordsFromFile("C:\\Users\\desriram\\workspace\\Anagrams\\src\\sowpods.txt");
		ArrayList<String> wordsCopy = (ArrayList<String>) words.clone();
		sortAllWords(wordsCopy);
		HashMap<String, ArrayList<Integer>> hash = putIntoHashMap(wordsCopy);
		makeCombinationsAndCheck(hash,rack,scores,words);
		p(getMaxWord());
	}
	
	private static String getMaxWord() {
		return maxWord;
	}

	public static void makeCombinationsAndCheck(HashMap<String,ArrayList<Integer>> hash, String inputstring,int[] scores, ArrayList<String> words) {
		StringBuilder output = new StringBuilder();
		combine(hash,inputstring,output,scores,words);
	}
	
	public static void combine(HashMap<String,ArrayList<Integer>> hash, String inputstring,StringBuilder output,int[] scores, ArrayList<String> words) { 
		combine(hash,inputstring,output,0,scores, words); 
	}
	
    private static void combine(HashMap<String,ArrayList<Integer>> hash,String inputstring,StringBuilder output,int start ,int[] scores,ArrayList<String> words){
    	
        for( int i = start; i < inputstring.length(); ++i ){
            output.append( inputstring.charAt(i) );
            String oStr = output.toString();
            char[] oArr = oStr.toCharArray();
            Arrays.sort(oArr);
            String temp = String.copyValueOf(oArr);
            if(hash.containsKey(temp))
            	if(calcScore(oStr,scores) > maxScore) {
            		maxScore = calcScore(oStr,scores);
            		maxWord = words.get(hash.get(temp).get(0));
            	}
            	
            if ( i < inputstring.length() )
            combine(hash,inputstring,output,i + 1,scores,words);
            output.setLength( output.length() - 1 );
        }
    }
	
	
	
	
	private static int calcScore(String in,int[] scores) {
		int sum = 0;
		for(int i=0;i<in.length();i++)
			sum+=scores[in.charAt(i)-'a'];
		return sum;
	}

	// Create a hashmap of String --> List of Indices of the anagrams
	private static HashMap<String, ArrayList<Integer>> putIntoHashMap(ArrayList<String> wordsCopy) {
		
		HashMap<String,ArrayList<Integer>> hash = new HashMap<String,ArrayList<Integer>>();
		for(int i = 0; i < wordsCopy.size(); i++) {
			if(hash.containsKey(wordsCopy.get(i)) == false) {
				ArrayList<Integer> al = new ArrayList<Integer>();
				al.add(i);
				hash.put(wordsCopy.get(i), al);
			} else {
				hash.get(wordsCopy.get(i)).add(i);
			}
					
		}
		return hash;
		
	}

	// Sort all the words internally
	private static void sortAllWords(ArrayList<String> wordsCopy) {
		
		for(int i = 0; i < wordsCopy.size(); i++) {
			char[] temp = wordsCopy.get(i).toCharArray();
			Arrays.sort(temp);
			wordsCopy.set(i, String.valueOf(temp));
		}
			
		
	}

	private static void p(Object o) {
		System.out.print(o);
		
	}

	// Read all the words from the file
	private static ArrayList<String> getWordsFromFile(String path) {

		ArrayList<String> words = new ArrayList<String>();
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(path);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        words.add(line);
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		
		return words;
	}
}


