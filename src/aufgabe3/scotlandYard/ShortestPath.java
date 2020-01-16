// O. Bittel;
// 28.02.2019

package aufgabe3.scotlandYard;

import aufgabe2.*;
import aufgabe3.SYSimulation.src.sim.SYSimulation;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 * @author Oliver Bittel
 * @since 27.01.2015
 * @param <V> Knotentyp.
 */
public class ShortestPath<V> {

	SYSimulation sim = null;

	Map<V,Double> dist; // Distanz für jeden Knoten
	Map<V,V> pred; // Vorgänger für jeden Knoten
	// ...
	DirectedGraph<V> graph;
	Heuristic<V> heuristic;
	V z;

	/**
	 * Konstruiert ein Objekt, das im Graph g k&uuml;rzeste Wege
	 * nach dem A*-Verfahren berechnen kann.
	 * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
	 * Wird h = null gewählt, dann ist das Verfahren identisch
	 * mit dem Dijkstra-Verfahren.
	 * @param g Gerichteter Graph
	 * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
	 * dem Dijkstra-Verfahren gesucht.
	 */
	public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
		// ...
		dist = new TreeMap<>();
		pred = new TreeMap<>();
		graph = g;
		heuristic = h;
	}

	/**
	 * Diese Methode sollte nur verwendet werden,
	 * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
	 * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
	 * <p>
	 * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
	 * <p><blockquote><pre>
	 *    if (sim != null)
	 *       sim.visitStation((Integer) v, Color.blue);
	 * </pre></blockquote>
	 * @param sim SYSimulation-Objekt.
	 */
	public void setSimulator(SYSimulation sim) {
		this.sim = sim;
	}

	/**
	 * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
	 * <p>
	 * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
	 * der als nächstes aus der Kandidatenliste besucht wird, animiert.
	 * @param s Startknoten
	 * @param g Zielknoten
	 */
	public void searchShortestPath(V s, V g) {
		// ...
		z = g;
		List<V> kl = new LinkedList<>(); // leere Kandidatenliste
		for (V v : graph.getVertexSet()) {
			dist.put(v, Double.MAX_VALUE);
			pred.put(v, null);

		}
		kl.add(s);
		dist.put(s, 0.0); // Startknoten



		if (heuristic == null) { //Dijkstra-Algorithm
			while (!kl.isEmpty()) {
				int index = 0;
				double d = Double.MAX_VALUE;
				for (V w : kl) {

					if (dist.get(w) < d) {
						d = dist.get(w);
						index = kl.indexOf(w);
					}
				}
				//System.out.println("Besuche Knoten: " + kl.get(index) + " mit d = " + dist.get(kl.get(index)));
				V v = kl.remove(index); // loeschen
				if (sim != null) {
					sim.visitStation((Integer)v, Color.RED);
				}
				for (V w : graph.getSuccessorVertexSet(v)) {
					if (dist.get(w) == Double.MAX_VALUE) { // w noch nicht besucht und nicht in Kandidatenliste
						kl.add(w);
					}
					if (dist.get(v) + graph.getWeight(v, w) < dist.get(w)) {
						pred.put(w, v);
						dist.put(w, dist.get(v) + graph.getWeight(v, w));
					}
				}
			}
			System.out.println("Dist: " + dist);
			System.out.println("Pred: " + pred);


		} else { //A*-Algorithm
			while (!kl.isEmpty()) {
				int index = 0;
				double d = Double.MAX_VALUE;
				for (V w : kl) {
					if (dist.get(w) + heuristic.estimatedCost(w, z) < d) {
						d = dist.get(w) + heuristic.estimatedCost(w, z);
						index = kl.indexOf(w);
					}
				}
				V v = kl.remove(index); // loeschen

				if (sim != null) {
					sim.visitStation((Integer)v, Color.RED);
				}

				if (v.equals(z)) { // Zielknoten z erreicht
					break;
				}

				for (V w : graph.getSuccessorVertexSet(v)) {
					if (dist.get(w) == Double.MAX_VALUE) { // w noch nicht besucht und nicht in Kandidatenliste
						kl.add(w);
					}
					if (dist.get(v) + graph.getWeight(v, w) < dist.get(w)) {
						pred.put(w, v);
						dist.put(w, dist.get(v) + graph.getWeight(v, w));
					}
				}
			}

		}
	}

	/**
	 * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return kürzester Weg als Liste von Knoten.
	 */
	public List<V> getShortestPath() {
		// ...
		LinkedList<V> queue = new LinkedList<>();
		System.out.println("while");
		V v = z;
		while(v != null) {
			queue.add(v);
			v = pred.get(v);
		}
		Collections.reverse(queue);
		return queue;
	}

	/**
	 * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
	 * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
	 * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
	 * @return Länge eines kürzesten Weges.
	 */
	public double getDistance() {
		return dist.get(z);
	}

}
