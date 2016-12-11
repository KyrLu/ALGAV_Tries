package test;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import tries.PatriciaTries;
import tries.Trie;
import tries.TriesHybrides;

public class TriesVisualisation {
	private static int crtId = 0;
	private static String DEFAULT_STYLE = "node { "
				+ "text-background-color: white;"
				+ "shape : box;"
				+ "fill-color : red;"
				+ "text-size : 15px;"
				+ "text-padding : 5px;"
				+ "size : 15px;"
				+ "z-index : 1; } "
				+ "edge {"
				+ "fill-color : red;}";
	
	public static void displayTrie(Trie t) {
		if (t instanceof TriesHybrides) {
			displayHybride((TriesHybrides) t);
		} else if (t instanceof PatriciaTries){
			displayPatricia((PatriciaTries) t);
		} else {
			System.out.println("Sorry, this trie is not supported yet.");
		}
		
		
	}
	
	private static void displayHybride(TriesHybrides t) {
		Graph graph = new SingleGraph("Hybride");
		
		buildHybride(t, graph, graph.addNode(t.getCaractere() + ":" + nextId()), 0);
		graph.addAttribute("ui.stylesheet", DEFAULT_STYLE);
		graph.display();
	}
	
	private static void buildHybride(TriesHybrides t, Graph g, Node crtNode, int level) {
		Node n = crtNode;
		Node tmp;
		setLabel(n, t.getCaractere()+"" + ((t.getValeur() != null) ? ":" + t.getValeur() : "") + " #" + level);

		TriesHybrides[] childs = new TriesHybrides[3];
		childs[0] = t.getInf(); childs[1] = t.getEq();childs[2] = t.getSup();
		
		
		for (TriesHybrides tChild : childs) {
			if (tChild != null) {
				tmp = g.addNode(tChild.getCaractere() + ":" + nextId());
				g.addEdge(nextId()+"", n, tmp);
				buildHybride(tChild, g, tmp, level+1);
			}
		}
		
	}
	
	private static void displayPatricia(PatriciaTries pt) {
		Graph g = new SingleGraph("Patricia");
		
		buildPatricia(pt, g, 0);

		g.addAttribute("ui.stylesheet", DEFAULT_STYLE);

		g.display();
	}
	
	private static Node buildPatricia(PatriciaTries pt, Graph g, int level) {
		Node n;
			
		n = g.addNode(nextId()+"");
		setLabel(n, pt.getSubWord() + ((pt.isFinal()) ? "FIN" : "")  + " #" + level);
		if (level == 0)
			n.setAttribute("color", "red");
		
		for (PatriciaTries t : pt.getTries()) {
				g.addEdge(nextId()+"", n, buildPatricia(t, g, level+1));
		}
		
		return n;
		
	}
	
	private static void setLabel(Node n, String l) {
		n.setAttribute("label", l);
	}
	
	private static int nextId() {
		return crtId++;
	}
	
}
