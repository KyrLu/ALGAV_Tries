package tries;

public class TriesHybrides implements Trie {
	
	private char caractere;
	private int valeur;
	private TriesHybrides inf;
	private TriesHybrides eq;
	private TriesHybrides sup;

	public TriesHybrides(char caractere, int valeur, TriesHybrides inf,
			TriesHybrides eq, TriesHybrides sup) {
		this.caractere = caractere;
		this.valeur = valeur;
		this.inf = inf;
		this.eq = eq;
		this.sup = sup;
	}

	public TriesHybrides(char caractere, int valeur) {
		this(caractere, valeur, null, null, null);
	}

	@Override
	public Trie ajouterMot(String mot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trie suppression(String mot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean recherche(String mot) {
		// TODO Auto-generated method stub
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

	@Override
	public int hauteur() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int profondeurMoyenne() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int prefixe(String mot) {
		// TODO Auto-generated method stub
		return 0;
	}

}
