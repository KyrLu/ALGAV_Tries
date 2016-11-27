package tries;

import java.util.ArrayList;

public class PatriciaTries implements Trie {
	
//	private final char finalChar = 3;	// le caractere ETX (end of text) indique, quand present a la fin d'un subWord, la fin d'un mot.
	// Utiliser NUL (finalChar = 0) ?
	private final int sizeTrie = 256;	// 255 caractere ASCII (+ ETX, when a word end)
	private String subWord;				// subWord de ce PATRICIATries. Si il finit par ETX, fin du mot (et le tries devrait etre vide)
	private ArrayList<PatriciaTries> tries;		// liste des sous-arbres. Taille de 256 (255 caractere + ETX)
	private boolean isFinal;
	
	public PatriciaTries(String subWord, ArrayList<PatriciaTries> tries) {
		this.subWord = subWord;
		this.tries = tries;
		isFinal = false;
	}

	public PatriciaTries(String subWord) {
		this(subWord, new ArrayList<>());
	}
	
	public PatriciaTries() {
		this("", new ArrayList<>());
	}

	public boolean isPrefix(String word) {
		return subWord.startsWith(subWord);
	}


	public PatriciaTries ajouterMot(String word) {	
//		System.out.println("current word : " + word);

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
			PatriciaTries pt = new PatriciaTries(word);
			pt.setFinal();
			tries.add(pt);

			return pt;
		}

		//TODO Optimisation plus que douteuse, à modifier.
		int candidateLength = candidate.getSubWord().length();
		int wordLength = word.length(); 
		int commonPrefixLength = commonPrefix.length();
		
		
//		System.out.println("common : " + commonPrefix);
//		System.out.println("candidate : " + candidate.subWord);

		if (commonPrefixLength == wordLength && commonPrefixLength < candidateLength) { //Simple extension d'un mot. testFIN -> test[erFIN, FIN]
//			System.out.println("Cas #1");
			PatriciaTries newPt = new PatriciaTries(commonPrefix);
			candidate.subWord = candidate.subWord.substring(commonPrefixLength);
			this.tries.remove(candidate);
			this.tries.add(newPt);
			newPt.tries.add(candidate);
			newPt.tries.add(getEmptyFinal());
			
			
			
		} else if (commonPrefixLength < candidateLength 
				&& commonPrefixLength < wordLength) { //éclatement, si on insere tete dans testFIN -> te[teFIN, stFIN]
//			System.out.println("Cas #2");
			
			PatriciaTries newPt = new PatriciaTries(commonPrefix);
			PatriciaTries oldPt = new PatriciaTries(candidate.subWord.substring(commonPrefixLength));
			
			if (candidate.isFinal) {
				candidate.isFinal = false;
				oldPt.isFinal = true;
			}
			
			oldPt.tries.addAll(candidate.tries);
			
			tries.remove(candidate);
			tries.add(newPt);
			newPt.tries.add(oldPt);
			newPt.ajouterMot(word.substring(commonPrefixLength));
			
		} else if (commonPrefixLength < wordLength 
				&& commonPrefixLength == candidateLength) { 
//			System.out.println("Cas #3");
//			System.out.println("adding : " + word.substring(commonPrefixLength));
			candidate.ajouterMot(word.substring(commonPrefixLength));
			if (candidate.isFinal && candidate.tries.size() != 0) {
				candidate.isFinal = false;
				candidate.tries.add(getEmptyFinal());
			}
		}
		

		return this;
	}
	
	private PatriciaTries getEmptyFinal() {
		PatriciaTries result = new PatriciaTries();
		result.setFinal();
		
		return result;
	}

	private String getPrefixInCommon(String word) {
		String result = "";
		int size = Math.min(subWord.length(), word.length());

		for (int i = 0; i < size; i++) {
			if (subWord.charAt(i) == word.charAt(i))
				result += subWord.charAt(i);
			else return result;
		}

		return result;
	}


	public PatriciaTries suppression(String word) {
		
		String commonPrefix = "";
		PatriciaTries candidate = null;
		
		for (PatriciaTries pt : tries) {
			commonPrefix = pt.getPrefixInCommon(word);
			if (!commonPrefix.equals("")) {
				candidate = pt;
				break;
			}
		}
		
		//Le mot à supprimer n'est pas dans l'arbre.
		if (candidate == null) 
			return this;
		else if (commonPrefix.length() < candidate.subWord.length())
			return this;
		
		
		if (commonPrefix.length() == candidate.subWord.length() && commonPrefix.length() == word.length()) {
			
			if (candidate.removeEmptyFinal()) {
				if (candidate.tries.size() == 1) { //merge tries si il ne contient qu'un element.
					PatriciaTries toDelete = candidate.tries.get(0);
					
					candidate.isFinal = toDelete.isFinal;
					candidate.subWord += toDelete.subWord;
					candidate.tries.addAll(toDelete.tries);
					candidate.tries.remove(toDelete);
				}
				
				return this;
			}
		} else if (commonPrefix.length() == candidate.subWord.length() && commonPrefix.length() < word.length()) {
			candidate.suppression(word.substring(commonPrefix.length()));
		}
		
		return this;
	}

	public boolean recherche(String word) {
		String commonPrefix;
		
		if (word.equals("")) {
			return isFinal || hasEmptyFinal();
		}
		
		for (PatriciaTries pt : tries) {
			commonPrefix = pt.getPrefixInCommon(word);
			if (!commonPrefix.equals("")) {
				
				if (pt.subWord.equals(word) && (pt.isFinal || pt.hasEmptyFinal()))
					return true;
				else if (pt.subWord.length() > word.length())
					return false;
				else 
					return pt.recherche(word.substring(commonPrefix.length()));
			}
		}
		
		return false;
	}

	@Override
	public int comptageMot() {
		int result = 0;
		
		if (isFinal)
			result++;
		
		for (PatriciaTries pt : tries) {
			result += pt.comptageMot(); 
		}
		
		return result;
	}

	@Override
	public String[] listeMots() {
		return toArray(listeMotsIntern());
	}
	
	private ArrayList<String> listeMotsIntern() {
		ArrayList<String> result = new ArrayList<>();
		
		if (isFinal) {
			result.add(subWord);
			return result;
			
		} else {
			ArrayList<String> tmp = new ArrayList<>();
			
			for (PatriciaTries pt : tries) {
				tmp.addAll(pt.listeMotsIntern());
			}
			
			for (String s : tmp) {
				result.add(subWord + s);
			}

			return result;
		}
		
	}
	
	
	private String[] toArray(ArrayList<String> liste) {
		String[] result = new String[liste.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = liste.get(i);
		}
		
		return result;
		
	}
	
	@Override
	public int comptageNil() {
		int result = 0;
		
		if (tries.size() == 0)
			return 1;
		
		for (PatriciaTries pt : tries) {
			result += pt.comptageNil();
		}
		
		
		return result;
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
	public int prefixe(String word) {
		String commonPrefix;
		
		if (word.equals(""))
			return tries.size();
		
		for (PatriciaTries pt : tries) {
			commonPrefix = pt.getPrefixInCommon(word);
			if (!commonPrefix.equals("")) {
				
				if (pt.subWord.length() > commonPrefix.length() && word.length() == commonPrefix.length()) {
					
					return pt.comptageMot() + ((pt.isFinal) ? 1 : 0);
				} else if (pt.subWord.length() == commonPrefix.length() && word.length() > commonPrefix.length()) {
					
					return pt.prefixe(word.substring(commonPrefix.length()));
				} else if (commonPrefix.length() == word.length() && word.length() == pt.subWord.length()) {
					return pt.comptageMot() + ((pt.isFinal) ? 1 : 0);
				} else {
					return 0;
				}
			}
		}
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
	
	private void setFinal() {
		this.isFinal = true;
	}
	
	private boolean hasEmptyFinal() {
		for (PatriciaTries pt : tries) {
			if (pt.subWord.equals("") && pt.isFinal)
				return true;
		}
		
		return false;
	}
	
	private boolean removeEmptyFinal() {
		for (PatriciaTries pt : tries) {
			if (pt.subWord.equals("") && pt.isFinal) {
				tries.remove(pt);
				return true;
			}
		}
		
		return false;
	}
	
	
	private String toString(int level) {
		String result = "";
		String tabs = getTabs(level);
		
		result +=  tabs + "(" + subWord + ((isFinal)? "FIN" : "") + ")";
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PatriciaTries) {
			PatriciaTries pt = (PatriciaTries) obj;
			return pt.subWord.equals(subWord);
		} else {
			return false;
		}
	}
}











