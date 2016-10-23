package Tries;

public class PatriciaTries {

	private final char finalChar = 3;	// ETX (end of text) character indicate, when use at the end of subWord, that it the end of a word.
										// Use NUL (finalChar = 0) ?
	private final int sizeTrie = 256;	// 255 character (+ ETX, when a word end)
	private String subWord;				// subWord of this PATRICIATries. If it end by ETX, end of a word (and tries should be null)
	private PatriciaTries[] tries;		// list of subTries. Size of 256 (255 character + ETX)
	
	public PatriciaTries(String subWord, PatriciaTries[] tries) {
		this.subWord = subWord;
		this.tries = tries;
	}
	
	public PatriciaTries(String subWord) {
		this(subWord, null);
	}
	
	public PatriciaTries addWord(String word) {	// TODO
		return null;
	}
	
	public PatriciaTries delWord(String word) {	// TODO
		return null;
	}
	
	public boolean containWord(String word) {	// TODO
		return false;
	}
	
	public boolean isPrefix(String word) {
		return subWord.startsWith(subWord);
	}
	
	public boolean lastTrie() {
		return subWord.endsWith("" + finalChar);
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
