package test;

import tries.PatriciaTries;
import tries.Trie;
import tries.TriesHybrides;

public class Main {

	public static final String exempleDeBase = "A quel genial professeur de dactylographie sommes nous redevables de "
			+ "la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque"
			+ " elle fait appel a chacune des touches du clavier de la machine a ecrire";
	
	public static void main(String[] args) {
//		PatriciaTries patricia = new PatriciaTries("");
//		patricia = (PatriciaTries) ajoutPhrase(patricia, exempleDeBase);
//		
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
		
//		hybride.ajouterMot("ecrire");
//		hybride.ajouterMot("appeler");
//		hybride.ajouterMot("tester");
//		hybride.ajouterMot("appel");
//		hybride.ajouterMot("testeur");
//		System.out.println(hybride);
//		hybride.ajouterMot("testeurs");
//		System.out.println(hybride);
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
