package test;

import tries.PatriciaTries;
import tries.Trie;
import tries.TriesHybrides;

public class Main {

	public static final String exempleDeBase = "A quel genial professeur de dactylographie sommes nous redevables de "
			+ "la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque"
			+ " elle fait appel a chacune des touches du clavier de la machine a ecrire";
	
	public static void main(String[] args) {
//		testHybride();
		testPatricia();
	}
	
	public static void testHybride() {
//		TriesHybrides hybride = new TriesHybrides(' ', 0);
//		hybride = (TriesHybrides) ajoutPhrase(hybride, exempleDeBase);
		
		
		Trie hybride = new TriesHybrides();
		hybride = (TriesHybrides) ajoutPhrase(hybride, exempleDeBase);
		System.out.println(hybride);
		System.out.println("Contient professeur : " + hybride.recherche("professeur"));
		System.out.println("Contient dactylographie : " + hybride.recherche("dactylographie"));
		System.out.println("Contient dactylo : " + hybride.recherche("dactylo"));
		System.out.println("Contient de : " + hybride.recherche("de"));
		System.out.println("Contient chacune : " + hybride.recherche("chacune"));
		System.out.println("Ne contient pas elles : " + ! hybride.recherche("elles"));
		System.out.println("Ne contient pas dacty : " + ! hybride.recherche("dacty"));
		System.out.println("Ne contient pas touche : " + ! hybride.recherche("touche"));
		System.out.println("Ne contient pas ecureuil : " + ! hybride.recherche("ecureuil"));
		
		hybride.suppression("dactylo");
		System.out.println(hybride);
		System.out.println("Suppression dactylo : " + ! hybride.recherche("dactylo"));
		System.out.println("Contient toujours dactylographie : " + hybride.recherche("dactylographie"));
		
		System.out.println(hybride.comptageMot());
		

		for (String mot : hybride.listeMots())
			System.out.println(mot);

		System.out.println("Hauteur : " + hybride.hauteur());
		System.out.println("Hauteur moyenne : " + hybride.profondeurMoyenne());
		
		System.out.println(hybride.prefixe("de"));
		
		TriesVisualisation.displayTrie(hybride);
		
		
//		hybride.ajouterMot("ecrire");
//		hybride.ajouterMot("appeler");
//		hybride.ajouterMot("tester");
//		hybride.ajouterMot("appel");
//		hybride.ajouterMot("testeur");
//		System.out.println(hybride);
//		hybride.ajouterMot("testeurs");
//		System.out.println(hybride);
	
	}
	
	
	public static void testPatricia() {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, exempleDeBase);
	
		System.out.println(patricia);
		System.out.println("Hauteur : " + patricia.hauteur());
		System.out.println("profondeur moyenne : " + patricia.profondeurMoyenne());
		TriesVisualisation.displayTrie(patricia);
	
	}
	
	public static Trie ajoutPhrase(Trie arbre, String phrase) {
		String[] mots = phrase.split("[ ,]"); //rajouter virgule dans les mots ?
		for (String mot : mots) {
			if (! mot.isEmpty()) {
				System.out.println(mot);
				arbre.ajouterMot(mot);
//				System.out.println(arbre);
			}
		}
		return arbre;
	}

}
