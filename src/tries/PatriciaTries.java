package tries;

import java.util.ArrayList;

public class PatriciaTries implements Trie {

	private final char finalChar = 3;	// le caractere ETX (end of text) indique, quand present a la fin d'un subWord, la fin d'un mot.
	// Utiliser NUL (finalChar = 0) ?
	private final int sizeTrie = 256;	// 255 caractere ASCII (+ ETX, when a word end)
	private String subWord;				// subWord de ce PATRICIATries. Si il finit par ETX, fin du mot (et le tries devrait etre vide)
	private ArrayList<PatriciaTries> tries;		// liste des sous-arbres. Taille de 256 (255 caractere + ETX)

	public PatriciaTries(String subWord, ArrayList<PatriciaTries> tries) {
		this.subWord = subWord;
		this.tries = tries;
	}

	public PatriciaTries(String subWord) {
		this(subWord, new ArrayList<>());
	}

	public boolean isPrefix(String word) {
		return subWord.startsWith(subWord);
	}

	public boolean lastTrie() {
		return subWord.endsWith("" + finalChar);
	}

	public PatriciaTries ajouterMot(String word) {	// TODO Cette version ajoute tout les mots. Mais les mots sont-ils bien mis ? Mystère.
		//		if (word.isEmpty() || subWord.isEmpty()) // Pas fini, debut peut-etre mauvais
		//			subWord = "" + finalChar;
		//		else {
		//			if (tries[(int)word.charAt(0)] == null)
		//				subWord = word + finalChar;
		//			else
		//				tries[(int)word.charAt(0)].ajouterMot(word);
		//		}
		//		return this;

		System.out.println("current word : " + word);

		PatriciaTries candidate = null;
		String commonPrefix = "";

		for (PatriciaTries pt : tries) {
			commonPrefix = pt.getPrefixInCommon(word);
			if (!commonPrefix.equals("")) {
				candidate = pt;
				break;
			}
		}

		if (candidate == null) { //Pas de prefix parmis tout les candidats
			PatriciaTries pt = new PatriciaTries(addFinalChar(word));
			tries.add(pt);

			return pt;
		}

		//TODO Optimisation plus que douteuse, à modifier.
		int candidateLength = lengthWFC(candidate.getSubWord());
		int wordLength = lengthWFC(word); 
		int commonPrefixLength = lengthWFC(commonPrefix);


		if (commonPrefixLength == wordLength) { //Mot deja present
			candidate.setAsFinal();
		} else if (commonPrefixLength < candidateLength && commonPrefixLength < wordLength) { //éclatement, si on insere tete dans test -> te[te, st]
			PatriciaTries newPt = new PatriciaTries(commonPrefix, candidate.tries);
			tries.remove(candidate);
			tries.add(newPt);
			newPt.ajouterMot(candidate.subWord.substring(commonPrefixLength));
			newPt.ajouterMot(word.substring(commonPrefixLength));
		} else if (commonPrefixLength < wordLength && commonPrefixLength == candidateLength) {
			candidate.ajouterMot(word.substring(commonPrefixLength));
		}

		return this;//XXX ligne tmp
	}

	//Va exclure un potentiel finalChar.
	private String getPrefixInCommon(String word) {
		String result = "";
		int size = Math.min(lengthWFC(subWord), lengthWFC(word));

		for (int i = 0; i < size; i++) {
			if (subWord.charAt(i) == word.charAt(i) && subWord.charAt(i) != finalChar)
				result += subWord.charAt(i);
			else return result;
		}

		return result;
	}

	private String addFinalChar(String w) {
		if (w.length() == 0 || w.charAt(w.length()-1) == finalChar)
			return w;
		else
			return w + finalChar;
	}

	private void setAsFinal() {
		subWord = addFinalChar(subWord);
	}

	/**
	 * length without final char
	 * Retourne la taille d'une String en excluant le char finalChar.
	 */
	private final int lengthWFC(String w) {
		if (w.length() == 0) {
			return 0;
		} else {
			if (w.charAt(w.length()-1) == finalChar)
				return w.length()-1;
			else return w.length();
		}
	}

	public PatriciaTries suppression(String word) {	// TODO
		return this;
	}

	public boolean recherche(String word) {			// TODO
		return false;
	}

	@Override
	public int comptageMot() {
		int result = 0;
		
		if (subWord.contains(finalChar+""))
			result++;
		
		for (PatriciaTries pt : tries) {
			result += pt.comptageMot(); 
		}
		
		return result;
	}

	@Override
	public String[] listeMots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int comptageNil() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hauteur() {
		return nextLevel(0);
	}


	protected int nextLevel(int level) {
		int result = level;

		if (tries.size() == 0)
			return result;

		for (PatriciaTries t : tries) {
			result = Math.max(result, t.nextLevel(level+1));
		}

		return result;
	}

	//TODO ne fonctionne pas, affiche 0 tout le temps.
	@Override
	public double profondeurMoyenne() {
		return nextLevelMean(0);
	}

	protected double nextLevelMean(int level) {
		double result = 0;
		double nonNull = 0;

		for (PatriciaTries t : tries) {
			if (t != null)
				result += t.nextLevelMean(level+1);
		}

		//Evite une division par 0.
		if (nonNull == 0)
			return 0;

		return result/nonNull;
	}

	@Override
	public int prefixe(String mot) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSubWord() {
		return subWord;
	}

	public void setSubWord(String subWord) {
		this.subWord = subWord;
	}

	public ArrayList<PatriciaTries> getTries() {
		return tries;
	}

	public void setTries(ArrayList<PatriciaTries> tries) {
		this.tries = tries;
	}

	
	private String getTabs(int nb) {
		String result = "";
		
		for (int i = 0; i < nb; i++)
			result += "\t";
		
		return result;
	}
	
	private String toString(int level) {
		String result = "";
		String tabs = getTabs(level);
		
		result +=  tabs + "(" + subWord + ")";
		result += "{\n";

		for (PatriciaTries pt : tries) {
			result += pt.toString(level+1);
		}
		result +="\n" + tabs +"} \n";

		return result;
	}
	
	@Override
	public String toString() {
		return toString(0);
	}
}
