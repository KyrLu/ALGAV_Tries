package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import tries.PatriciaTries;
import tries.Trie;
import tries.TriesHybrides;

public class Shakespeare {
	private final static String DIRECTORY_PATH = "/home/alexandre/git/ALGAV_Tries/test-data/shakespeare";
	private final static String DIRECTORY_PATH_LOREM = "/home/alexandre/git/ALGAV_Tries/test-data/lorem";
	public final static boolean VISUALIZE = false;
	public final static boolean HYBRIDE = false;
	public final static boolean PATRICIA = true;
	
	private static int wordCount = -1;
	private static TreeSet<String> wordList;
	
	
	public static void launchShakespearTest() {
		long start, time;
		
		start = System.currentTimeMillis();
		ArrayList<ArrayList<String>> data = loadDataFiles();
//		ArrayList<ArrayList<String>> data = loadLoremDataFiles();
		time = System.currentTimeMillis();
		System.out.println("Loaded in " + getTimer(start, time));
		int total = 0;
		
		for (ArrayList<String> arrayList : data) {
			total += arrayList.size();
		}
		
		System.out.println(total);
		
		
		
		TreeSet<String> words = new TreeSet<>();
		for (ArrayList<String> arrayList : data) {
			words.addAll(arrayList);
		}
		wordCount = words.size();
		wordList = words;
		System.out.println("Total word count : " + words.size());
		
		
		System.out.println();
		
		if (HYBRIDE) {
			System.out.println("Testing Hybride trie.");
			
			TriesHybrides hybride = new TriesHybrides();

			insertTrie(hybride, data);
			statsTrie(hybride);
			
			if (VISUALIZE)
				TriesVisualisation.displayTrie(hybride);
		}
		
		System.out.println();
		
		if (PATRICIA) {
			System.out.println("Testing Patricia trie.");
			
			PatriciaTries patricia = new PatriciaTries();
			insertTrie(patricia, data);
			toFile(patricia, "t_print.txt");
			statsTrie(patricia);
			
			
			
			
			
			if (VISUALIZE)
				TriesVisualisation.displayTrie(patricia);
		}
		
		
		
	}
	
	private static Trie insertTrie(Trie t, ArrayList<ArrayList<String>> data) {
		long start, time;
		System.out.println("Building ...");
		start = System.currentTimeMillis();
		for (ArrayList<String> a : data) {
			for (String s : a) {
				t.ajouterMot(s);
			}
		}
		time = System.currentTimeMillis();
		System.out.println("Built in " + getTimer(start, time));
		
		return t;
	}
	
	private static void statsTrie(Trie t) {
		long start, time;
		
		
		start = System.currentTimeMillis();
		int wordCount = t.comptageMot();
		time = System.currentTimeMillis();
		System.out.println("Found " + wordCount + " words in " + getTimer(start, time));
		
		if (Shakespeare.wordCount > 0)
			System.out.println("The result is supposed to be " + Shakespeare.wordCount + ".");
		
		start = System.currentTimeMillis();
		int nilCount = t.comptageNil();
		time = System.currentTimeMillis();
		System.out.println("Found " + nilCount + " nils in " + getTimer(start, time));
		
		start = System.currentTimeMillis();
		int height = t.hauteur();
		time = System.currentTimeMillis();
		System.out.println("Height " + height + " in " + getTimer(start, time));
		
		start = System.currentTimeMillis();
		double depthAverage = t.profondeurMoyenne();
		time = System.currentTimeMillis();
		System.out.println("Depth average " + depthAverage + " in " + getTimer(start, time));
		
		
		if (wordCount != Shakespeare.wordCount) {
			TreeSet<String> missingWords = findMissingWords(wordList, t.listeMots());
			System.out.println("The missing words are :");
			for (String string : missingWords) {
				System.out.print(string + ", ");
			}
			
			System.out.println();
		}
		
	}
	
	private static TreeSet<String> findMissingWords(TreeSet<String> src, String[] toSearch) {
		TreeSet<String> result = new TreeSet<>();
		result.addAll(src);
		
		for (String string : toSearch) {
			result.remove(string);
		}
		
		return result;
	}
	
	private static String getTimer(long start, long time, boolean inSecond) {
		double result = time - start;
		
		if (inSecond)
			result /= 1000.0;
		
		return result + ((inSecond) ? "s" : "ms");
	}
	
	private static String getTimer(long start, long time) {
		return getTimer(start, time, true);
	}
	
	private static ArrayList<ArrayList<String>> loadDataFiles() {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		ArrayList<String> fileContent;
		File dir = new File(DIRECTORY_PATH);
		File currentFile;
		Scanner sc;
		
		
		if (!dir.isDirectory())
			throw new Error(DIRECTORY_PATH + " is not a directory.");
		
		
		System.out.println("Loading test files....");
		
		for (String s : dir.list()) {
			currentFile = new File(dir.getAbsolutePath() + "/" + s);
			try {
				System.out.print("Loading " + s + "....");
				sc = new Scanner(currentFile);
				fileContent = new ArrayList<>();
				while (sc.hasNextLine()) {
					fileContent.add(sc.nextLine().toLowerCase());
				}
				
				result.add(fileContent);
				sc.close();
				System.out.println("Done");
			} catch (FileNotFoundException e) {
				System.out.println("Failed");
			}
			
			
		}
		
		System.out.println("Loading complete.");
		
		return result;
	}
	

	private static ArrayList<ArrayList<String>> loadLoremDataFiles() {
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		ArrayList<String> fileContent;
		File dir = new File(DIRECTORY_PATH_LOREM);
		File currentFile;
		Scanner sc;
		
		
		if (!dir.isDirectory())
			throw new Error(DIRECTORY_PATH + " is not a directory.");
		
		
		System.out.println("Loading test files....");
		
		for (String s : dir.list()) {
			currentFile = new File(dir.getAbsolutePath() + "/" + s);
			try {
				System.out.print("Loading " + s + "....");
				sc = new Scanner(currentFile);
				fileContent = new ArrayList<>();
				while (sc.hasNextLine()) {
					fileContent.add(sc.nextLine().toLowerCase());
				}
				
				result.add(fileContent);
				sc.close();
				System.out.println("Done");
			} catch (FileNotFoundException e) {
				System.out.println("Failed");
			}
			
			
		}
		
		System.out.println("Loading complete.");
		
		return result;
	}
	
	public static void main(String[] args) {
		launchShakespearTest();
	}
	
	public static void toFile(Trie t, String file) {
		PrintWriter out;
		try {
			out = new PrintWriter(new File(file));
			out.append("*************************************\n");
			out.append(t.toString() + "\n");
			out.append("*************************************\n");
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
