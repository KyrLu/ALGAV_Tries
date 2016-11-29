package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.Test;

import tries.PatriciaTries;
import tries.Trie;

public class UnitaryTestsPatricia {
	public static final String exempleDeBase = "A quel genial professeur de dactylo sommes nous redevables de "
			+ "la superbe phrase ci dessous, un modele du genre, que toute dactylographie connait par coeur puisque"
			+ " elle fait appel a chacune des touches du clavier de la machine a ecrire";
	
	public final static String test1 = "test tete tester table autre testeur testeurs";
	public final static String test2 = "test tete tester";
	public final static String test3 = "test tete tester table";
	public final static String test4 = "test tester test table test testation testationner";
	public final static String test5 = "tester tete";
	public final static String test6 = "a b c d aa bb cc dd ac a b c d";
	public final static String test7 = exempleDeBase + "qu pro con nait";
	public final static String test8 = exempleDeBase + "ga greivs greece great greatly grease gra";
	
	/*INSERTION*/
	@Test
	public void testInsert1() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertTrue(contains(toArrayList(test1.split(" ")), toArrayList(patricia.listeMots())));
		assertEquals(7, patricia.comptageMot());

	}
	
	@Test
	public void testInsert2() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test2);
		assertTrue(contains(toArrayList(test2.split(" ")), toArrayList(patricia.listeMots())));
		assertEquals(3, patricia.comptageMot());

	}
	
	@Test
	public void testInsert3() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test3);
		assertTrue(contains(toArrayList(test3.split(" ")), toArrayList(patricia.listeMots())));
		assertEquals(4, patricia.comptageMot());

	}
	
	@Test
	public void testInsert4() {
		PatriciaTries patricia = new PatriciaTries("");
		TreeSet<String> words = new TreeSet<>();
		for (String string : test4.toLowerCase().split("[ ,]")) 
			if (!string.equals(""))
				words.add(string);
		patricia = (PatriciaTries) ajoutPhrase(patricia, test4);
		assertTrue(contains(toArrayList(test4.split(" ")), toArrayList(patricia.listeMots())));
		assertEquals(words.size(), patricia.comptageMot());

	}

	@Test
	public void testInsert5() {
		PatriciaTries patricia = new PatriciaTries("");
		
		
		
		patricia = (PatriciaTries) ajoutPhrase(patricia, test6);
		assertTrue(contains(toArrayList(test6.split(" ")), toArrayList(patricia.listeMots())));
		assertEquals(9, patricia.comptageMot());

	}
	
	@Test
	public void testInsert6() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test7.toLowerCase());

		TreeSet<String> words = new TreeSet<>();
		for (String string : test7.toLowerCase().split("[ ,]")) 
			if (!string.equals(""))
				words.add(string);
		
		assertTrue(contains(toArrayList(test7.split("[ ,]")), toArrayList(patricia.listeMots())));
		assertEquals(words.size(), patricia.comptageMot());
	}
	
	@Test
	public void testInsertBase() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, exempleDeBase.toLowerCase());

		TreeSet<String> words = new TreeSet<>();
		for (String string : exempleDeBase.toLowerCase().split("[ ,]")) 
			if (!string.equals(""))
				words.add(string);
		
		assertTrue(contains(toArrayList(exempleDeBase.split("[ ,]")), toArrayList(patricia.listeMots())));
		assertEquals(words.size(), patricia.comptageMot());
	}
	
	
	/*RECHERCHE*/
	@Test
	public void testRecherche1() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		
		assertTrue(patricia.recherche("test"));
	}
	
	@Test
	public void testRecherche2() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertFalse(patricia.recherche("testationner"));
	}
	
	@Test
	public void testRecherche3() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertFalse(patricia.recherche("t"));
	}
	
	@Test
	public void testRecherche4() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertFalse(patricia.recherche("est"));
	}
	
	@Test
	public void testRecherche5() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test5);
		assertFalse(patricia.recherche("test"));
	}
	
	@Test
	public void testRecherche8() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test8);
		Shakespeare.toFile(patricia, "unit.txt");
		assertTrue(patricia.recherche("greivs"));
	}
	
	
	
	/*PREFIXE*/
	@Test
	public void testPrefixe1() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertEquals(0, patricia.prefixe("b"));
	}
	
	@Test
	public void testPrefixe2() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertEquals(4, patricia.prefixe("test"));
	}
	
	@Test
	public void testPrefixe3() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertEquals(4, patricia.prefixe("tes"));
	}
	
	
	/*SUPPRESSION*/
	@Test
	public void testSuppr1() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		patricia.suppression("test");
		assertFalse(patricia.recherche("test"));
	}
	
	@Test
	public void testSuppr2() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test2);
		patricia.suppression("test");
		assertFalse(patricia.recherche("test"));
	}
	
	@Test
	public void testSuppr3() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test2);
		patricia.suppression("te");
		assertEquals(3, patricia.comptageMot());
	}
	
	@Test
	public void testSuppr4() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test2);
		patricia.suppression("st");
		assertEquals(3, patricia.comptageMot());
	}
	
	@Test
	public void testSuppr5() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test6);
		
		
		patricia.suppression("ac");
		assertEquals(8, patricia.comptageMot());
	}
	
	public static boolean contains(ArrayList<String> array1, ArrayList<String> array2) {
		for (String s : array2) {
			if (!array1.contains(s))
				return false;
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
