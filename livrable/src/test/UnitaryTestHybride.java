package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.Test;

import tries.TriesHybrides;
import tries.Trie;

public class UnitaryTestHybride {
	public static final String exempleDeBase = "A quel genial professeur de dactylo sommes nous redevables de "
			+ "la superbe phrase ci dessous, un modele du genre, que toute dactylographie connait par coeur puisque"
			+ " elle fait appel a chacune des touches du clavier de la machine a ecrire";
	
	public final static String test1 = "test tete tester table autre testeur testeurs";
	public final static String test2 = "test tete tester";
	public final static String test3 = "test tete tester table";
	public final static String test4 = "test tester test table test testation testationner";
	public final static String test5 = "tester tete";
	public final static String test6 = "a b c d aa bb cc dd ac a b c d";
	public final static String test7 = exempleDeBase + " qu pro con nait";
	public final static String test8 = exempleDeBase + " ga greivs greece great greatly grease gra";
	
	/*INSERTION*/
	@Test
	public void testInsert1() {
		TriesHybrides hybride = new TriesHybrides();

		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		assertTrue(contains(toArrayList(test1.split(" ")), toArrayList(hybride.listeMots())));
		assertEquals(7, hybride.comptageMot());

	}
	
	@Test
	public void testInsert2() {
		TriesHybrides hybride = new TriesHybrides();

		hybride = (TriesHybrides) ajoutPhrase(hybride, test2);
		assertTrue(contains(toArrayList(test2.split(" ")), toArrayList(hybride.listeMots())));
		assertEquals(3, hybride.comptageMot());

	}
	
	@Test
	public void testInsert3() {
		TriesHybrides hybride = new TriesHybrides();

		hybride = (TriesHybrides) ajoutPhrase(hybride, test3);
		assertTrue(contains(toArrayList(test3.split(" ")), toArrayList(hybride.listeMots())));
		assertEquals(4, hybride.comptageMot());

	}
	
	@Test
	public void testInsert4() {
		TriesHybrides hybride = new TriesHybrides();
		TreeSet<String> words = new TreeSet<>();
		for (String string : test4.toLowerCase().split("[ ,]")) 
			if (!string.equals(""))
				words.add(string);
		hybride = (TriesHybrides) ajoutPhrase(hybride, test4);
		assertTrue(contains(toArrayList(test4.split(" ")), toArrayList(hybride.listeMots())));
		assertEquals(words.size(), hybride.comptageMot());

	}

	@Test
	public void testInsert5() {
		TriesHybrides hybride = new TriesHybrides();
		
		
		
		hybride = (TriesHybrides) ajoutPhrase(hybride, test6);
		assertTrue(contains(toArrayList(test6.split(" ")), toArrayList(hybride.listeMots())));
		assertEquals(9, hybride.comptageMot());

	}
	
	@Test
	public void testInsert6() {
		TriesHybrides hybride = new TriesHybrides();

		hybride = (TriesHybrides) ajoutPhrase(hybride, test7.toLowerCase());

		TreeSet<String> words = new TreeSet<>();
		for (String string : test7.toLowerCase().split("[ ,]")) 
			if (!string.equals(""))
				words.add(string);
		
		assertTrue(contains(toArrayList(test7.split("[ ,]")), toArrayList(hybride.listeMots())));
		assertEquals(words.size(), hybride.comptageMot());
	}
	
	@Test
	public void testInsertBase() {
		TriesHybrides hybride = new TriesHybrides();

		hybride = (TriesHybrides) ajoutPhrase(hybride, exempleDeBase.toLowerCase());

		TreeSet<String> words = new TreeSet<>();
		for (String string : exempleDeBase.toLowerCase().split("[ ,]")) 
			if (!string.equals(""))
				words.add(string);
		assertTrue(contains(toArrayList(exempleDeBase.split("[ ,]")), toArrayList(hybride.listeMots())));
		assertEquals(words.size(), hybride.comptageMot());
	}
	
	
	/*RECHERCHE*/
	@Test
	public void testRecherche1() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		
		assertTrue(hybride.recherche("test"));
	}
	
	@Test
	public void testRecherche2() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		assertFalse(hybride.recherche("testationner"));
	}
	
	@Test
	public void testRecherche3() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		assertFalse(hybride.recherche("t"));
	}
	
	@Test
	public void testRecherche4() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		assertFalse(hybride.recherche("est"));
	}
	
	@Test
	public void testRecherche5() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test5);
		assertFalse(hybride.recherche("test"));
	}
	
	@Test
	public void testRecherche8() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test8);
		assertTrue(hybride.recherche("greivs"));
	}
	
	
	
	/*PREFIXE*/
	@Test
	public void testPrefixe1() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		assertEquals(0, hybride.prefixe("b"));
	}
	
	@Test
	public void testPrefixe2() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		assertEquals(4, hybride.prefixe("test"));
	}
	
	@Test
	public void testPrefixe3() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		assertEquals(4, hybride.prefixe("tes"));
	}
	
	
	/*SUPPRESSION*/
	@Test
	public void testSuppr1() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test1);
		hybride.suppression("test");
		assertFalse(hybride.recherche("test"));
	}
	
	@Test
	public void testSuppr2() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test2);
		hybride.suppression("test");
		assertFalse(hybride.recherche("test"));
	}
	
	@Test
	public void testSuppr3() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test2);
		hybride.suppression("te");
		assertEquals(3, hybride.comptageMot());
	}
	
	@Test
	public void testSuppr4() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test2);
		hybride.suppression("st");
		assertEquals(3, hybride.comptageMot());
	}
	
	@Test
	public void testSuppr5() {
		TriesHybrides hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, test6);
		hybride.suppression("ac");
		assertEquals(8, hybride.comptageMot());
	}
	
	
	public static boolean contains(ArrayList<String> array1, ArrayList<String> array2) {
		for (String s : array2) {
			if (!s.equals("") && !array1.contains(s)) {
				System.out.println(s + " is missing!");
				return false;
			}
		}
		
		return true;
	}
	
	
	public static ArrayList<String> toArrayList(String[] array) {
		ArrayList<String> result = new ArrayList<>();
		
		for (String string : array) {
			result.add(string);
		}
		
		return result;
	}
	
	public static Trie ajoutPhrase(Trie arbre, String phrase) {
		String[] mots = phrase.split("[ ,]"); //rajouter virgule dans les mots ?
		for (String mot : mots) {
			if (! mot.isEmpty()) {
				arbre.ajouterMot(mot);
			}
		}
		return arbre;
	}
}
