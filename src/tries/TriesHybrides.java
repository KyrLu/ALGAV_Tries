package tries;

import java.util.ArrayList;

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
	 * Pour garder la possibilite de mettre une valeur
	 * La fonction de l'interface utilise cette fonction : ajouterMot(mot, id)
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
				if (this.valeur == null)
					this.valeur = valeur;
				else
					id--; // mot pas ajoute ; une maniere + elegante ?
			}
		}
		return this;
	}

	@Override
	public Trie ajouterMot(String mot) {
		id++;
		return ajouterMot(mot, id);
	}

	public Trie ajouterMotPlusConversion(String mot, int valeur) {
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
				if (this.valeur == null)
					this.valeur = valeur;
				else
					id--; // mot pas ajoute ; une maniere + elegante ?
			}
		}
		return this.equilibrer();
	}

	public Trie ajouterMotPlusConversion(String mot) {
		id++;
		return ajouterMotPlusConversion(mot, id);
	}
	
	@Override
	public Trie suppression(String mot) {
		char initiale = mot.charAt(0);
		if (initiale ==  caractere) {
			if (mot.length() == 1) {
				if (inf == null && eq == null && sup == null)
					return null;
				else {
					valeur = null;
					return this;
				}
			}
			else {
				if (eq == null)
					return this;
				else {
					eq = (TriesHybrides) eq.suppression(mot.substring(1));
					if (eq == null && sup == null && inf == null && valeur == null)
						return null;
					else
						return this;
				}
			}
		}
		else if (initiale < caractere) {
			if (inf == null)
				return this;
			else {
				inf = (TriesHybrides) inf.suppression(mot);
				if (inf == null && eq == null && sup == null && valeur == null)
					return null;
				else
					return this;
			}
		}
		else {
			if (sup == null)
				return this;
			else {
				sup = (TriesHybrides) sup.suppression(mot);
				if (sup == null && eq == null && inf == null && valeur == null)
					return null;
				else
					return this;
			}
		}
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
		int count = valeur != null ? 1 : 0;
		if (inf != null)
			count += inf.comptageMot();
		if (eq != null)
			count += eq.comptageMot();
		if (sup != null)
			count += sup.comptageMot();
		return count;
	}

	@Override
	public String[] listeMots() {
		ArrayList<String> liste = new ArrayList<String>();
		if (inf != null)
			for (String mot : inf.listeMots())
				liste.add(mot);

		if (valeur != null)
			liste.add("" + caractere);
		
		if (eq != null)
			for (String mot : eq.listeMots())
				liste.add(caractere + mot);
		
		if (sup != null)
			for (String mot : sup.listeMots())
				liste.add(mot);
		return (String[]) liste.toArray(new String[liste.size()]);
	}

	@Override
	public int comptageNil() {
		int count = valeur == null ? 1 : 0;
		if (inf != null)
			count += inf.comptageNil();
		if (eq != null)
			count += eq.comptageNil();
		if (sup != null)
			count += sup.comptageNil();
		return count;
	}

	@Override
	public int hauteur() {
		return nextLevel(0);
	}

	/**
	 * Methode propagee dans l'arbre afin de connaitre sa taille.
	 */
	protected int nextLevel(int level) {
		int result = level;
		
		//Utiliser une methode tierce pour limiter le duplication de code n'a pour effet que d'alourdir une methode simple.
		if (inf != null)
			result = Math.max(result, inf.nextLevel(level+1));
		if (eq != null)
			result = Math.max(result, eq.nextLevel(level+1));
		if (sup != null)
			result = Math.max(result, sup.nextLevel(level+1));
		
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
		double[] tmp;
		
		if (inf != null) {
			tmp = inf.nextLevelMean(level+1);
			result[0] += tmp[0];
			result[1] += tmp[1];
		} else {
			result[0] += level;
			result[1] += 1;
		}
		if (eq != null) {
			tmp = eq.nextLevelMean(level+1);
			result[0] += tmp[0];
			result[1] += tmp[1];
		} else {
			result[0] += level;
			result[1] += 1;
		}
		if (sup != null) {
			tmp = sup.nextLevelMean(level+1);
			result[0] += tmp[0];
			result[1] += tmp[1];
		} else {
			result[0] += level;
			result[1] += 1;
		}
		
		return result;
	}
	
	@Override
	public int prefixe(String mot) {
		char initiale = mot.charAt(0);
		if (initiale == caractere) {
			
			if (mot.length() == 1) {
				int nb = 0;
				if (valeur != null)
					nb = 1;
				if (eq != null)
					return eq.comptageMot() + nb;
				else
					return nb;
			}
			else if (eq != null)
				return eq.prefixe(mot.substring(1));
		}
		else {
			if (initiale < caractere) {
				if (inf != null)
					return inf.prefixe(mot);
			}
			else if (sup != null)
				return sup.prefixe(mot);
		}
		return 0;
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

	public TriesHybrides ajouterListMots(String[] listeMots) {
		if (listeMots().length < 3)
			for (String mot : listeMots)
				this.ajouterMot(mot);
		else {
			char caractereEq;
			if (! this.estVide())
				caractereEq = caractere;
			else
				caractereEq = listeMots[listeMots.length / 2].charAt(0);
			if (inf == null)
				inf = new TriesHybrides();
			if (sup == null)
				sup = new TriesHybrides();
			int i = 0;
			ArrayList<String> listeInf = new ArrayList<String>();
			ArrayList<String> listeEq = new ArrayList<String>();
			ArrayList<String> listeSup = new ArrayList<String>();
			
			while (i < listeMots.length && listeMots[i].charAt(0) < caractereEq) {
				listeInf.add(listeMots[i]);
				i++;
			}
			while (i < listeMots.length && listeMots[i].charAt(0) == caractereEq) {
				listeEq.add(listeMots[i]);
				i++;
			}
			while (i < listeMots.length) {
				listeSup.add(listeMots[i]);
				i++;
			}
			inf.ajouterListMots(listeInf.toArray(new String[listeInf.size()]));
			eq.ajouterListMots(listeInf.toArray(new String[listeEq.size()]));
			sup.ajouterListMots(listeInf.toArray(new String[listeSup.size()]));
		}
		return this;
	}
	
	public TriesHybrides equilibrer() {
		int nbMotInf = inf == null ? 0 : inf.comptageMot();
		int nbMotSup = sup == null ? 0 : sup.comptageMot();
		int nbMotEq = eq == null ? 0 : eq.comptageMot();
		nbMotEq += valeur == null ? 0 : 1;
		int total = nbMotEq + nbMotInf + nbMotSup;
		if (nbMotInf > total * 0.6) {
			TriesHybrides newP = inf;
			this.inf = inf.sup;
			newP.sup = this.equilibrer();
			return newP;
		}
		else if (nbMotSup > total * 0.6) {
			TriesHybrides newP = sup;
			this.sup = sup.inf;
			newP.inf = this.equilibrer();
			return newP;
		}
		return this;
	}
	
	public boolean estVide() {
		return caractere == 0;
	}
	
	public char getCaractere() {
		return caractere;
	}
	
	public TriesHybrides getInf() {
		return inf;
	}
	
	public TriesHybrides getEq() {
		return eq;
	}
	
	public TriesHybrides getSup() {
		return sup;
	}
	
	public Integer getValeur() {
		return valeur;
	}
}
