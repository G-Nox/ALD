package aufgabe4;

public class UnionFind {

    int part[];

    public UnionFind(int n) {
        // Legt eine neue Union-Find-Struktur mit der Partitionierung {{0}, {1}, ..., {n-1}} an.
        // n = Groesse der Grundmenge
        part = new int[n];

        for (int i = 0; i < part.length; i++) {
            part[i] = -1;
        }
    }

    public int find(int e) {
        // Liefert den Repräsentanten der Menge zurück, zu der e gehört.
        // return: Repräsentant der Menge, zu der e gehört.
        while (part[e] >= 0) { // e ist keine Wurzel
            e = part[e];
        }
        return e;
    }

    public void union(int s1, int s2) {
        //Vereinigt die beiden Mengen s1 und s2. s1 und s2 müssen Repräsentanten der jeweiligen Menge sein.
        // Die Vereinigung wird nur durchgeführt, falls s1 und s2 unterschiedlich sind. Es wird union-by-height durchgeführt.
        // s1 - Element, das eine Menge repräsentiert.
        //s2 - Element, das eine Menge repräsentiert.
        if (part[s1] >= 0 || part[s2] >= 0) { // s1 und s2 müssen Repräsentanten einer Menge sein.
            return;
        }
        if (s1 == s2) { // Falls s1 und s2 dieselbe Menge ist, dann mache nichts.
            return;
        }

        if ( -part[s1] < -part[s2]) { // Höhe von s1 < Höhe von s2
            part[s1] = s2;
        } else {
            // Die Höhe h für ein Baum lässt sich bei p[e], wobei e die Wurzel ist,
            // als negative Zahl abspeichern: p[e] = -1-h
            if ( -part[s1] == -part[s2]) {
                part[s1]--; // Höhe von s1 erhöht sich um 1
            }
            part[s2] = s1;
        }
    }

    public int size() {
        // Liefert die Anzahl der Mengen in der Partitionierung zurück.
        int count = 0;

        for (int i = 0; i < part.length-1; i++) {
            if (part[i] < 0) {
                count ++;
            }
        }
        return count;
    }
}
