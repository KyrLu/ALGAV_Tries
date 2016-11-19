package tries;

public class TriesHybrides implements Trie {
	
	private char caractere;
	private Integer valeur;
	private TriesHybrides inf;
	private TriesHybrides eq;
	private TriesHybrides sup;
	private static int id = 0;

	public TriesHybrides(char caractere, Integer valeur, TriesHybrides inf,
			TriesHybrides eq, TriesHybrides sup) {
		this.caractere = caractere;
		this.valeur = valeur;
		this.inf = inf;
		this.eq = eq;
		this.sup = sup;
	}
	
	public TriesHybrides(char caractere, int valeur) {
		new TriesHybrides(caractere, valeur, null, null, null);
	}
	
	public TriesHybrides() {
		new TriesHybrides((char)0, (Integer) null, null, null, null);
	}
	
	public TriesHybrides(String mot, int valeur) {
		new TriesHybrides();
		this.ajouterMot(mot, valeur);
	}

	/*
	 * Pour garder la possibilit� de mettre une valeur
	 * La fonction de l'interface utilise cette fonction : ajouterMot(mot, id)
	 * 
	 * TODO : optimisation possible : creer les TriesHybrides fils quand
	 * le pere n'est pas vide pour ne pas verifier si les fils sont null
	 */
	public Trie ajouterMot(String mot, int valeur) {
		if (this.estVide()) {
			caractere = mot.charAt(0);
			if (mot.length() == 1)
				this.valeur = valeur;
			else {
				eq = new TriesHybrides(mot.substring(1), valeur);
			}
		}
		else {
			char initiale = mot.charAt(0);
			if (initiale < caractere) {
				if (inf == null)
					inf = new TriesHybrides(mot, valeur);
				else
					inf.ajouterMot(mot, valeur);
			}
			else if (initiale > caractere) {
				if (sup == null)
					sup = new TriesHybrides(mot, valeur);
				else
					sup.ajouterMot(mot, valeur);
			}
			else if (mot.length() > 1) {
				if (eq == null)
					eq = new TriesHybrides(mot.substring(1), valeur);
				else
					eq.ajouterMot(mot.substring(1), valeur);
			}
			else {
				this.valeur = valeur;
			}
//			else
//				id--; // mot pas ajoute ; une maniere + elegante ?
		}
		return this;
	}

	@Override
	public Trie suppression(String mot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean recherche(String mot) {
		char initiale = mot.charAt(0);
		if (initiale < caractere)
			if (inf != null)
				return inf.recherche(mot);
			else
				return false;
		else if (initiale > caractere)
			if (sup != null)
				return sup.recherche(mot);
			else
				return false;
		else {
			if (mot.length() == 1) {
				if (valeur == null)
					return false;
				else
					return true;
			}
			else
				if (eq == null)
					return false;
				else
					return eq.recherche(mot.substring(1));
		}
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
	
	@Override
	public Trie ajouterMot(String mot) {
		id++;
		return ajouterMot(mot.toLowerCase(), id);
	}
	
	@Override
	public String toString() {
		return toString(0);
	}
	
	private String toString(int tab) {
		String tabs = "";
		for (int i = 0 ; i < tab ; i++)
			tabs += "\t";
		String base = tabs + "(" + caractere + "," + valeur + ",\n";
		if (inf != null)
			base += inf.toString(tab + 1);
		else
			base += tabs + "\tnull";
		base += ",\n";
		if (eq != null)
			base += eq.toString(tab + 1);
		else
			base += tabs + "\tnull";
		base += ",\n";
		if (sup != null)
			base += sup.toString(tab + 1);
		else
			base += tabs + "\tnull";
		base += "\n" + tabs + ")";
		return base;
	}
	
	public boolean estVide() {
		return caractere == 0;
	}
}