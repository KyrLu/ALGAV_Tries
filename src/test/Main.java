package test;

import tries.PatriciaTries;
import tries.Trie;
import tries.TriesHybrides;

public class Main {

	public static final String exempleDeBase = "A quel genial professeur de dactylographie sommes nous redevables de "
			+ "la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque"
			+ " elle fait appel a chacune des touches du clavier de la machine a ecrire";
	
	public static void main(String[] args) {
		PatriciaTries patricia = new PatriciaTries("");
		patricia = (PatriciaTries) ajoutPhrase(patricia, exempleDeBase);
		
		TriesHybrides hybride = new TriesHybrides(' ', 0);
		hybride = (TriesHybrides) ajoutPhrase(hybride, exempleDeBase);
	}
	
	public static Trie ajoutPhrase(Trie arbre, String phrase) {
		String[] mots = phrase.split("[ ,]"); //rajouter virgule dans les mots ?
		for (String mot : mots) {
			if (! mot.isEmpty())
				arbre.ajouterMot(mot);
		}
		return arbre;
	}

}
