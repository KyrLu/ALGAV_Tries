package tries;

public interface Trie {
	public Trie ajouterMot(String mot);
	public Trie suppression(String mot);
	public boolean recherche(String mot);
	public int comptageMot();
	public String[] listeMots();
	public int comptageNil();
	public int hauteur();
	public int profondeurMoyenne();
	public int prefixe(String mot);
}
