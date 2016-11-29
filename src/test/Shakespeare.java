package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import tries.PatriciaTries;
import tries.Trie;
import tries.TriesHybrides;

public class Shakespeare {
	private final static String DIRECTORY_PATH = "/home/alexandre/git/ALGAV_Tries/test-data/shakespeare";
	
	public final static boolean VISUALIZE = false;
	public final static boolean HYBRIDE = true;
	public final static boolean PATRICIA = true;
	
	
	public static void launchShakespearTest() {
		long start, time;
		
		start = System.currentTimeMillis();
		ArrayList<ArrayList<String>> data = loadDataFiles();
		time = System.currentTimeMillis();
		System.out.println("Loaded in " + getTimer(start, time));
		
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
					fileContent.add(sc.nextLine());
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
	
}
