package test;

import tries.PatriciaTries;
import tries.Trie;
import tries.TriesHybrides;

public class Main {

	public static final String exempleDeBase = "A quel genial professeur de dactylographie sommes nous redevables de "
			+ "la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque"
			+ " elle fait appel a chacune des touches du clavier de la machine a ecrire";
	
	public final static String test1 = "test tete tester table autre testeur testeurs";
	public final static String test2 = "test tete tester";
	public final static String test3 = "test tete tester table";
	public final static String test4 = "test tester test table test testation testationner";
	
	public static void main(String[] args) {
		testHybride();
		testPatricia();
	}
	
	public static void testHybride() {
		System.out.println("Hybride");
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
			System.out.print(mot+ " ");
		System.out.println();

		System.out.println("Hauteur : " + hybride.hauteur());
		System.out.println("Hauteur moyenne : " + hybride.profondeurMoyenne());
		
		System.out.println(hybride.prefixe("de"));
		
		TriesVisualisation.displayTrie(hybride);

		System.out.println("Profondeur moyenne avant equilibrage = " + hybride.profondeurMoyenne());
		hybride = ((TriesHybrides) hybride).equilibrer();
		System.out.println("Profondeur moyenne apres equilibrage = " + hybride.profondeurMoyenne());
		
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
		System.out.println("Patricia");
		PatriciaTries patricia = new PatriciaTries();
		patricia = (PatriciaTries) ajoutPhrase(patricia, exempleDeBase);
//		patricia = (PatriciaTries) ajoutPhrase(patricia, test1);
//		patricia = (PatriciaTries) ajoutPhrase(patricia, test2);
//		patricia = (PatriciaTries) ajoutPhrase(patricia, test3);
//		patricia = (PatriciaTries) ajoutPhrase(patricia, test4);
		
		
//		PatriciaTries pt2 = new PatriciaTries();
		
//		patricia = (PatriciaTries) ajoutPhrase(patricia, "test tete tester table autre testeur testeurs");
//		pt2 = (PatriciaTries) ajoutPhrase(pt2, "a b c d aa bb cc dd ac a b c d");
//		
//		System.out.println("Before");
//		System.out.println(patricia);
//		System.out.println(pt2);
//		
//		patricia = patricia.fusion(pt2);
//		 
//		System.out.println("After");
//		System.out.println(patricia);
		
		afficherListMots(patricia.listeMots());
		
		
		System.out.println(patricia);
		System.out.println("Hauteur : " + patricia.hauteur());
		System.out.println("profondeur moyenne : " + patricia.profondeurMoyenne());
		System.out.println("Nombre de mots : " + patricia.comptageMot());
		afficherListMots(patricia.listeMots());

		System.out.println("Nils : " + patricia.comptageNil());
		TriesVisualisation.displayTrie(patricia);
	
		
		
		
	}
	
	public static void afficherListMots(String[] liste) {
		System.out.println("Liste de mots : ");
		for (String string : liste) {
			System.out.print(string + " ");
		}
		System.out.println();
	}
	
	public static Trie ajoutPhrase(Trie arbre, String phrase) {
		String[] mots = phrase.split("[ ,]"); //rajouter virgule dans les mots ?
		
		for (String mot : mots) {
			if (! mot.isEmpty()) {
//				System.out.println(mot);
				arbre.ajouterMot(mot.toLowerCase());
//				System.out.println(arbre);
			}
		}
		return arbre;
	}

}
