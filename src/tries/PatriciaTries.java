package tries;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class PatriciaTries implements Trie, Comparable<PatriciaTries> {
	private String subWord;					// subWord de ce PATRICIATries.
	private TreeSet<PatriciaTries> tries;	// liste des sous-arbres.
	private boolean isFinal;				// indicateur de fin de mot.

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

	/**
	 * Ajoute un mot.
	 */
	public PatriciaTries ajouterMot(String word) {	
		PatriciaTries candidate = null;
		String commonPrefix = "";

		//Cherche un prefix en commun.
		for (PatriciaTries pt : tries) {
			commonPrefix = pt.getPrefixInCommon(word);
			if (!commonPrefix.equals("")) {
				candidate = pt;
				break;
			}
		}

		//Pas de prefix parmis tout les candidats
		if (candidate == null) { 
			PatriciaTries pt = new PatriciaTries(word);
			pt.isFinal = true;
			tries.add(pt);

			return pt;
		}

		int candidateLength = candidate.subWord.length();
		int wordLength = word.length(); 
		int commonPrefixLength = commonPrefix.length();
		
		//Le mot est present mais peut etre pas final.
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

	/**
	 * Supprime un mot.
	 */
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

		//Le candidat est le mot à supprimer
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

	/**
	 * Recherche un mot.
	 */
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
		double[] result = nextLevelMean(0);
		if (result[1] == 0)
			return 0;
		return result[0]/result[1];
	}

	protected double[] nextLevelMean(int level) {
		double[] result = {0, 0};

		if (tries.size() == 0) {
			result[0] = level;
			result[1] = 1;
			
			return result;
		}


		double[] tmp;

		for (PatriciaTries t : tries) {
			tmp = t.nextLevelMean(level+1);
			result[0] += tmp[0];
			result[1] += tmp[1];
		}

		return result;
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
	/**
	 * Fusionne 2 arbres.
	 * @param toFusion
	 * 	Arbre à fusionner.
	 * @return
	 * 	Fusion des deux arbres.
	 * 
	 * XXX cette méthode est buguée. Je pense que certains noeuds final ne sont pas fusionné correectement, mais lequels...
	 */
	public PatriciaTries fusion(PatriciaTries toFusion) {
		if (this.subWord.equals(toFusion.subWord) && this.tries.size() == 0 && toFusion.tries.size() == 0)
			return this;

		String commonPrefix;
		
		@SuppressWarnings("unchecked")
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
//						System.out.println(ptSrc);
//						System.out.println(pt);

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

	public boolean isFinal() {
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
	
	public TriesHybrides conversion() {
		TriesHybrides base = new TriesHybrides();
		return base.ajouterListMots(this.listeMots());
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
	
	public static PatriciaTries fromHybride(TriesHybrides h) {
		PatriciaTries result = new PatriciaTries();
		
		if (h.getInf() != null) {
			result.tries.add(fromHybrideIntern(h.getInf()));
		}
		
		if (h.getEq() != null) {
			result.tries.add(fromHybrideIntern(h));
		}
		
		if (h.getSup() != null) {
			result.tries.add(fromHybrideIntern(h.getSup()));
		}
		
		
		return result;
	}
	
	private static PatriciaTries fromHybrideIntern(TriesHybrides h) {
		PatriciaTries result = new PatriciaTries();
		result.subWord = h.getCaractere() + "";
		
		TriesHybrides crtH = h.getEq();
		
		
		while(crtH != null && crtH.getValeur() == null && crtH.getInf() == null && crtH.getSup() == null && crtH.getEq() != null) {
			result.subWord += crtH.getCaractere();
			crtH = crtH.getEq();
		}
			
		if (crtH == null)
			return result;
		
		if (crtH.getInf() != null) {
			result.tries.add(fromHybrideIntern(crtH.getInf()));
		}
		
		if (crtH.getEq() != null) {
			result.tries.add(fromHybrideIntern(crtH));
		}
		
		if (crtH.getSup() != null) {
			result.tries.add(fromHybrideIntern(crtH.getSup()));
		}
		
		if (crtH.getValeur() != null && !result.isFinal()) {
			if (result.tries.size() != 0) {
				result.tries.add(result.getEmptyFinal());
			} else {
				result.isFinal = true;
			}
			result.subWord += crtH.getCaractere();
		}
		
		return result;
	}
	
}

