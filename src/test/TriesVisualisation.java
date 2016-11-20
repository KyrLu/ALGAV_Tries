package test;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.springbox.BarnesHutLayout;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.view.Viewer;

import tries.Trie;
import tries.TriesHybrides;

public class TriesVisualisation {
	private static int crtId = 0;
	
	
	public static void displayTrie(Trie t) {
		if (t instanceof TriesHybrides) {
			displayHybride((TriesHybrides) t);
		} else {
			System.out.println("Sorry, this trie is not supported yet.");
		}
		
		
	}
	
	private static void displayHybride(TriesHybrides t) {
		Graph graph = new SingleGraph("Hybride");
		
		buildHybride(t, graph, graph.addNode(t.getCaractere() + ":" + nextId()));
		
		Viewer v = graph.display();
	}
	
	private static void buildHybride(TriesHybrides t, Graph g, Node crtNode) {
		Node n = crtNode;
		Node tmp;
		n.setAttribute("label", t.getCaractere()+"" + ((t.getValeur() != null) ? ":" + t.getValeur() : ""));
		
		TriesHybrides[] childs = new TriesHybrides[3];
		childs[0] = t.getInf(); childs[1] = t.getEq();childs[2] = t.getSup();
		
		
		for (TriesHybrides tChild : childs) {
			if (tChild != null) {
				tmp = g.addNode(tChild.getCaractere() + ":" + nextId());
				g.addEdge(nextId()+"", n, tmp);
				buildHybride(tChild, g, tmp);
			}
		}
		
	}
	
	private static int nextId() {
		return crtId++;
	}
	
}
