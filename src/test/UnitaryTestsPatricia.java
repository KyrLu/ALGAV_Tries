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

	
	//TODO ajouter deux fois le meme mot.
	
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
	public void testInsertBase() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, exempleDeBase.toLowerCase());
		assertTrue(contains(toArrayList(exempleDeBase.split("[ ,]")), toArrayList(patricia.listeMots())));
	}
	

	@Test
	public void testInsert4() {
		PatriciaTries patricia = new PatriciaTries("");

		patricia = (PatriciaTries) ajoutPhrase(patricia, test4);
		assertTrue(contains(toArrayList(test4.split(" ")), toArrayList(patricia.listeMots())));
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
				System.out.println(mot);
				arbre.ajouterMot(mot);
				System.out.println(arbre);
			}
		}
		return arbre;
	}
	
}
