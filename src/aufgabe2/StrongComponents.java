// O. Bittel;
// 05-09-2018

package aufgabe2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

	//private final Map<Integer,Set<V>> comp = new TreeMap<>();
	private int numberOfComp;
	final Map<Integer, Set<V>> comp = new TreeMap<>();

	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(DirectedGraph<V> g) {
		// ...
		DepthFirstOrder<V> dfo = new DepthFirstOrder<>(g);
		List<V> post = dfo.postOrder();
		List<V> invertI = new LinkedList<>(post);

		Collections.reverse(invertI);

		DirectedGraph<V> inverted = g.invert();
		dfInverted(inverted, invertI);


	}

	private void dfInverted(DirectedGraph<V> g, List<V> invertI) {
		Set<V> visited = new HashSet<>();
		for (V v : invertI) {
			if (!visited.contains(v)) {
				Set<V> comp = new HashSet<>();
				dfInverted(v, g, visited, comp);
				this.comp.put(this.numberOfComp, comp);
				this.numberOfComp++;
			}
		}

	}

	public void dfInverted(V v, DirectedGraph<V> g, Set<V> visited, Set<V> comp) {
		// Knoten zu StrongComponent hinzufügen solange Rekursion nicht verlassen wird
		comp.add(v);
		visited.add(v);
		for (V w : g.getSuccessorVertexSet(v)) {
			if (!visited.contains(w)) {
				dfInverted(w, g, visited, comp);
			}
		}
	}





	/*public void hallotest (DirectedGraph<V> g) {

		// Aufgabe a)
		DepthFirstOrder<V> dfo = new DepthFirstOrder<>(g);
		List<V> p = dfo.postOrder();
		List<V> pi = new LinkedList<>();
		pi.addAll(p);
		Collections.reverse(pi);

		// Aufgabe b)
		DirectedGraph<V> gi = g.invert();
		//System.out.println(gi);

		//Aufgabe c)
		//DepthFirstOrder<V> ksDF = new DepthFirstOrder<>(gi, pi);
		//comp.putAll(ksDF.comp);


	}
*/


	

	/**
	 * 
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return comp.size();
	}

	@Override
	public String toString() {
		// Component 0: 5, 6, 7,
		StringBuilder s = new StringBuilder();
		for (Map.Entry<Integer, Set<V>> e : comp.entrySet()) {
			s.append("Component ")
					.append(e.getKey().toString())
					.append(": ");
			int i = 0;
			for (V v : e.getValue()) {
				s.append(v.toString());
				if (i++ != e.getValue().size() - 1) {
					s.append(", ");
				}
			}
			s.append("\n");
		}
		return s.toString();
	}


	
	/**
	 * Liest einen gerichteten Graphen von einer Datei ein. 
	 * @param fn Dateiname.
	 * @return gerichteter Graph.
	 * @throws FileNotFoundException
	 */
	public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		Scanner sc = new Scanner(fn);
		sc.nextInt();
		sc.nextInt();
		while (sc.hasNextInt()) {
			int v = sc.nextInt();
			int w = sc.nextInt();
			g.addEdge(v, w);
		}
		return g;
	}
	
	private static void test1() {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);
		
		g.addEdge(1,4);
		g.addEdge(5,4);
		
		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);
		
		g.addEdge(7,8);
		g.addEdge(8,2);
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		
		System.out.println(sc.numberOfComp());  // 4
		
		System.out.println(sc);
			// Component 0: 5, 6, 7, 
        	// Component 1: 8, 
            // Component 2: 1, 2, 3, 
            // Component 3: 4, 
	}
	
	private static void test2() throws FileNotFoundException {
		DirectedGraph<Integer> g = readDirectedGraph(new File("/Users/pfau/02_Sonstiges/02_intelij/01_Projekte/07_HTWG_SEM3_AlgoDat/src/aufgabe2/mediumDG.txt"));
		System.out.println(g.getNumberOfVertexes());
		System.out.println(g.getNumberOfEdges());
		System.out.println(g);
		
		System.out.println("");
		
		StrongComponents<Integer> sc = new StrongComponents<>(g);
		System.out.println(sc.numberOfComp());  // 10
		System.out.println(sc);
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		test1();
		test2();
	}
}
