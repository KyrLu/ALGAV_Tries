package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import tries.PatriciaTries;
import tries.Trie;

public class UnitaryTestsPatricia {
	public static final String exempleDeBase = "A quel genial professeur de dactylographie sommes nous redevables de "
			+ "la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque"
			+ " elle fait appel a chacune des touches du clavier de la machine a ecrire";
	
	public final static String test1 = "test tete tester table autre testeur testeurs";
	public final static String test2 = "test tete tester";
	public final static String test3 = "test tete tester table";
	public final static String test4 = "test tester test table test testation testationner";
	public final static String test5 = "tester tete";
	
	/*INSERTION*/
	@Test
	public void testInsert1() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
		assertTrue(contains(toArrayList(test1.split(" ")), toArrayList(patricia.listeMots())));
	}
	
	@Test
	public void testInsert2() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test2);
		assertTrue(contains(toArrayList(test2.split(" ")), toArrayList(patricia.listeMots())));
	}
	
	@Test
	public void testInsert3() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test3);
		assertTrue(contains(toArrayList(test3.split(" ")), toArrayList(patricia.listeMots())));
	}
	
	@Test
	public void testInsert4() {
		PatriciaTries patricia = new PatriciaTries("");
		
		patricia = (PatriciaTries) ajoutPhrase(patricia, test4);
		assertTrue(contains(toArrayList(test4.split(" ")), toArrayList(patricia.listeMots())));
	}

	@Test
	public void testInsertBase() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, exempleDeBase.toLowerCase());
		assertTrue(contains(toArrayList(exempleDeBase.split("[ ,]")), toArrayList(patricia.listeMots())));
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
		System.out.println(patricia);
		patricia.suppression("te");
		System.out.println(patricia);
		assertEquals(3, patricia.comptageMot());
	}
	
	@Test
	public void testSuppr4() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, test2);
		patricia.suppression("st");
		assertEquals(3, patricia.comptageMot());
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
