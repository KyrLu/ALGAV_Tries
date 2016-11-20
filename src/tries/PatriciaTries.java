package tries;

public class PatriciaTries implements Trie {

	private final char finalChar = 3;	// le caractere ETX (end of text) indique, quand present a la fin d'un subWord, la fin d'un mot.
										// Utiliser NUL (finalChar = 0) ?
	private final int sizeTrie = 256;	// 255 caractere ASCII (+ ETX, when a word end)
	private String subWord;				// subWord de ce PATRICIATries. Si il finit par ETX, fin du mot (et le tries devrait etre vide)
	private PatriciaTries[] tries;		// liste des sous-arbres. Taille de 256 (255 caractere + ETX)
	
	public PatriciaTries(String subWord, PatriciaTries[] tries) {
		this.subWord = subWord;
		this.tries = tries;
	}
	
	public PatriciaTries(String subWord) {
		this(subWord, null);
	}
	
	public boolean isPrefix(String word) {
		return subWord.startsWith(subWord);
	}
	
	public boolean lastTrie() {
		return subWord.endsWith("" + finalChar);
	}
	
	public PatriciaTries ajouterMot(String word) {	// TODO
		if (word.isEmpty() || subWord.isEmpty()) // Pas fini, debut peut-etre mauvais
			subWord = "" + finalChar;
		else {
			if (tries[(int)word.charAt(0)] == null)
				subWord = word + finalChar;
			else
				tries[(int)word.charAt(0)].ajouterMot(word);
		}
		return this;
	}
	
	public PatriciaTries suppression(String word) {	// TODO
		return this;
	}
	
	public boolean recherche(String word) {			// TODO
		return false;
	}

	@Override
	public int comptageMot() {
		// TODO Auto-generated method stub
		return 0;
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

	//XXX Pas encore testé.
	@Override
	public int hauteur() {
		return nextLevel(0);
	}

	
	protected int nextLevel(int level) {
		int result = level;
		
		for (PatriciaTries t : tries) {
			if (t != null)
				result = Math.max(result, nextLevel(level+1));
		}
		
		return result;
	}

	//XXX Pas encore teste.
	@Override
	public double profondeurMoyenne() {
		return nextLevelMean(0);
	}
	
	protected double nextLevelMean(int level) {
		double result = 0;
		double nonNull = 0;
		
		for (PatriciaTries t : tries) {
			if (t != null)
				result += nextLevelMean(level+1);
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

	public PatriciaTries[] getTries() {
		return tries;
	}

	public void setTries(PatriciaTries[] tries) {
		this.tries = tries;
	}
}
