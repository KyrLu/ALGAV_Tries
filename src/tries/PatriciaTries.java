package tries;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class PatriciaTries implements Trie, Comparable<PatriciaTries> {
	private String subWord;				// subWord de ce PATRICIATries. Si il finit par ETX, fin du mot (et le tries devrait etre vide)
	private TreeSet<PatriciaTries> tries;		// liste des sous-arbres. Taille de 256 (255 caractere + ETX)
	private boolean isFinal;
	
	public PatriciaTries(String subWord, TreeSet<PatriciaTries> tries) {
		this.subWord = subWord;
		this.tries = tries;
		isFinal = false;
	}

	public PatriciaTries(String subWord) {
		this(subWord, new TreeSet<>());
	}
	
	public PatriciaTries() {
		this("", new TreeSet<>());
	}

	public boolean isPrefix(String word) {
		return subWord.startsWith(subWord);
	}


	public PatriciaTries ajouterMot(String word) {	
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
			pt.isFinal = true;
			tries.add(pt);

			return pt;
		}

		int candidateLength = candidate.subWord.length();
		int wordLength = word.length(); 
		int commonPrefixLength = commonPrefix.length();
		
		if (word.equals(candidate.subWord)) {
			if (!candidate.isFinal()) {
				if (!candidate.isFinal && !candidate.hasEmptyFinal()) {
					if (candidate.tries.size() == 0)
						candidate.isFinal = true;
					else
						candidate.tries.add(getEmptyFinal());
				}
			}
		} else if (commonPrefixLength == wordLength 
				&& commonPrefixLength < candidateLength) { //test[FIN, erFIN] + tes = tes[stFIN, erFIN]
			PatriciaTries newPt = new PatriciaTries(commonPrefix);
			this.tries.remove(candidate);
			candidate.subWord = candidate.subWord.substring(commonPrefixLength);
			
			this.tries.add(newPt);
			newPt.tries.add(candidate);
			newPt.tries.add(getEmptyFinal());
			
		} else if (commonPrefixLength < candidateLength 
				&& commonPrefixLength < wordLength) { //éclatement, si on insere tete dans testFIN -> te[teFIN, stFIN]
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
		result.isFinal = true;
		
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
					PatriciaTries toDelete = candidate.tries.first();
					
					candidate.isFinal = toDelete.isFinal;
					candidate.subWord += toDelete.subWord;
					candidate.tries.addAll(toDelete.tries);
					candidate.tries.remove(toDelete);
				}
				
				return this;
			} else {
				if (candidate.isFinal)
					this.tries.remove(candidate);
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
				
				if (pt.subWord.equals(word) && (pt.isFinal())) 
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

	@Override
	public double profondeurMoyenne() {
		return nextLevelMean(0);
	}

	protected double nextLevelMean(int level) {
		if (tries.size() == 0)
			return level + 0.0;

		double result = 0;
		double nonNull = 0;
		
		
		for (PatriciaTries t : tries) {
			if (t != null) {
				result += t.nextLevelMean(level+1);
				nonNull++;
			}
		}

		//Evite une division par 0.
		if (nonNull == 0)
			return 0;

		return result/nonNull;
	}

	@Override
	public int prefixe(String word) {
		int result = 0;
		String commonPrefix;
		
		for (PatriciaTries pt : tries) {
			commonPrefix = pt.getPrefixInCommon(word);
			if (!commonPrefix.equals("")) {
				if (pt.subWord.length() > commonPrefix.length() && word.length() == commonPrefix.length()) {
					result += pt.comptageMot() + ((pt.isFinal) ? 1 : 0);
				
				} else if (pt.subWord.length() == commonPrefix.length() && word.length() > commonPrefix.length()) {
					result += pt.prefixe(word.substring(commonPrefix.length()));

				} else if (commonPrefix.length() == word.length() && word.length() == pt.subWord.length()) {
					result += pt.comptageMot() + ((pt.isFinal) ? 1 : 0);
				}
			}
		}
		return result;
	}

	public PatriciaTries fusion(PatriciaTries toFusion) {
//		if (this.subWord.equals(toFusion.subWord) && this.tries.size() == 0 && toFusion.tries.size() == 0)
//			return this;
		
		
		String commonPrefix;
		TreeSet<PatriciaTries> noConflict = (TreeSet<PatriciaTries>) toFusion.tries.clone();
		
		for (PatriciaTries ptSrc : tries) {
			for (PatriciaTries pt : toFusion.tries) {
				commonPrefix = ptSrc.getPrefixInCommon(pt.subWord);
				
				int commonPrefixLength = commonPrefix.length();
				int ptSrcLength = ptSrc.subWord.length();
				int ptLength = pt.subWord.length();
				
				
				
				if (!commonPrefix.equals("")) {
//					System.out.println();
//					System.out.println(ptSrc.subWord + " and " + pt.subWord + " are being merged.");
					
					
					noConflict.remove(pt);
					if (commonPrefixLength == ptSrcLength && commonPrefixLength == ptLength) {
//						System.out.println("Cas #1");
//						System.out.println(ptSrc.tries.size());
//						System.out.println(pt.tries.size());
						ptSrc.fusion(pt);
					} else if (commonPrefixLength < ptSrcLength && commonPrefixLength < ptLength) { // test + tete
//						System.out.println("Cas #2");
						PatriciaTries newPt = new PatriciaTries(commonPrefix);
						
						ptSrc.subWord = ptSrc.subWord.substring(commonPrefixLength);
						pt.subWord = pt.subWord.substring(commonPrefixLength);
						
						newPt.tries.add(pt);
						newPt.tries.add(ptSrc);
					} else if (commonPrefixLength == ptSrcLength && commonPrefixLength < ptLength) { // test + tester
//						System.out.println("Cas #3");
						pt.subWord = pt.subWord.substring(commonPrefixLength);
						ptSrc.tries.add(pt);
						
					} else if (commonPrefixLength == ptLength && commonPrefixLength < ptSrcLength) { // tester + test
//						System.out.println("Cas #4");

						System.out.println(ptSrc);
						System.out.println(pt);
						
						PatriciaTries newPt = new PatriciaTries(ptSrc.subWord.substring(commonPrefixLength));
						newPt.isFinal = ptSrc.isFinal;
						newPt.tries.addAll(ptSrc.tries);
						
						ptSrc.tries.clear();
						ptSrc.subWord = commonPrefix;
						ptSrc.isFinal = pt.isFinal;
						ptSrc.tries.addAll(pt.tries);
						ptSrc.tries.add(newPt);
					} else {
						System.out.println("Error : Missing case");
					}
					
				} else {
//					System.out.println("No conflict for " + ptSrc.subWord + " and " + pt.subWord + ".");
				}
			}
		}
		
		this.tries.addAll(noConflict);
		
		return this;
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
	
	private boolean isFinal() {
		return isFinal || hasEmptyFinal();
	}
	
	private String toString(int level) {
		String result = "";
		String tabs = getTabs(level);
		
		result +=  tabs + "(#" + level + " : " + subWord + ((isFinal)? "FIN" : "") + ")";
		result += "{\n";

		for (PatriciaTries pt : tries) {
			result += pt.toString(level+1);
		}
		result +="\n" + tabs +"} \n";

		return result;
	}
	
	private String getTabs(int nb) {
		String result = "";
		
		for (int i = 0; i < nb; i++)
			result += "\t";
		
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
	
	public Set<PatriciaTries> getTries() {
		return tries;
	}
	
	public String getSubWord() {
		return subWord;
	}

	@Override
	public int compareTo(PatriciaTries o) {
		return subWord.compareTo(o.subWord);
	}
}

