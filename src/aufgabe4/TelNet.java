package aufgabe4;


import java.awt.*;
import java.util.*;
import java.util.List;

public class TelNet {

    private int lbg;
    private Map<TelKnoten, Integer> knoten;
    private List<TelVerbindung> optNetz;

    // Konstruktor
    public TelNet(int lbg) {
        // Legt ein neues Telefonnetz mit dem Leitungsbegrenzungswert lbg an.
        //Parameter: lbg - Leitungsbegrenzungswert.
        this.lbg = lbg;
        this.knoten = new HashMap<>();
        this.optNetz = new LinkedList<>();
    }

    public boolean addTelKnoten(int x, int y) {
        // Fügt einen neuen Telefonknoten mit Koordinate (x,y) dazu.
        // return true, falls die Koordinate neu ist, sonst false.
        TelKnoten telKnoten = new TelKnoten(x, y);
        if (!knoten.containsKey(telKnoten)) {
            this.knoten.put(new TelKnoten(x, y), knoten.size());
            return true;
        }
        return false;
    }

    public boolean computeOptTelNet() {
        // Berechnet ein optimales Telefonnetz als minimal aufspannenden Baum mit dem Algorithmus von Kruskal.
        // return true, falls es einen minimal aufspannden Baum gibt, sonst true.
        UnionFind unionFind = new UnionFind(knoten.size());
        PriorityQueue<TelVerbindung> edges = new PriorityQueue<>();

        for (TelKnoten u : knoten.keySet()) {
            for (TelKnoten v : knoten.keySet()) {
                if (u != v) {
                    TelVerbindung t = new TelVerbindung(u, v, 0);
                    if (t.c <= lbg)
                        edges.add(t);
                }
            }
        }

        while (unionFind.size() != 1 && !edges.isEmpty()) {
            TelVerbindung verbindung = edges.poll();


            int t1 = unionFind.find(knoten.get(verbindung.u));
            int t2 = unionFind.find(knoten.get(verbindung.v));

            if (t1 != t2) {
                unionFind.union(t1, t2);
                optNetz.add(verbindung);
            }
        }

        if (edges.isEmpty() && unionFind.size() != 1) {
            optNetz.clear();
            return false;
        }
        return true;
    }


    public List<TelVerbindung> getOptTelNet() throws java.lang.IllegalStateException {
        // Liefert ein optimales Telefonnetz als Liste von Telefonverbindungen zurück.
        // return Liste von Telefonverbindungen
        return optNetz;
    }

    public int getOptTelNetKosten() throws java.lang.IllegalStateException {
        // Liefert die Gesamtkosten eines optimalen Telefonnetzes zurück.
        // return Gesamtkosten eines optimalen Telefonnetzes.
        int kosten = 0;

        for(TelVerbindung t : optNetz) {
            kosten += t.c;
        }
        return kosten;
    }


    public void drawOptTelNet(int xMax, int yMax) throws java.lang.IllegalStateException {
        // Zeichnet das gefundene optimale Telefonnetz mit der Größe xMax*yMax in ein Fenster.
        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.setPenRadius(0.0125);
        StdDraw.setPenColor(Color.BLUE);

        for (TelKnoten tk : knoten.keySet()) {
            StdDraw.point((double) tk.x / xMax, (double) tk.y / yMax);
        }

        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.RED);
        for (TelVerbindung telVerbindung : optNetz) {
            StdDraw.line(telVerbindung.u.x / (double) xMax, telVerbindung.u.y / (double) yMax,
                    telVerbindung.u.x / (double) xMax, telVerbindung.v.y / (double) yMax);
            StdDraw.line(telVerbindung.u.x / (double) xMax, telVerbindung.v.y / (double) yMax,
                    telVerbindung.v.x / (double) xMax, telVerbindung.v.y / (double) yMax);

        }
    }

    public int size() {
        // Liefert die Anzahl der Knoten des Telefonnetzes zurück.
        return knoten.size();
    }

    @Override
    public String toString() {
        String optimalTelNetString = "";
        for( TelVerbindung t : getOptTelNet()) {
            optimalTelNetString += t.toString() + "\n";
        }

        return "Leistungsbegrenzungswert: " + lbg + "\n" +
                "Kosten: " + getOptTelNetKosten() + "\n\n" +
                " --- OPTIMALES NETZ ---\n" + optimalTelNetString;

    }


    private static void telNetTest() {
        TelNet testTelNet = new TelNet(7);

        //Knoten hinzufügen (Abb. 3)
        testTelNet.addTelKnoten(1, 1);
        testTelNet.addTelKnoten(3, 1);
        testTelNet.addTelKnoten(4, 2);
        testTelNet.addTelKnoten(3, 4);
        testTelNet.addTelKnoten(7, 5);
        testTelNet.addTelKnoten(2, 6);
        testTelNet.addTelKnoten(4, 7);

        if (testTelNet.computeOptTelNet()) {
            System.out.println(testTelNet.toString());
            testTelNet.drawOptTelNet(7, 7);
        } else {
            System.out.println("Fehler: Kleinster aufspannender Baum konnte nicht gefunden werden.");
        }
    }

    public static void generateRandomTelNet(int n, int xMax, int yMax) {
        // Fügt n zufällige Telefonknoten zum Netz dazu mit x-Koordinate aus [0,xMax] und y-Koordinate aus [0,yMax].
        // n - Anzahl Telefonknoten
        //xMax - Intervallgrenz für x-Koordinate.
        //yMax - Intervallgrenz für y-Koordinate.
        int lbg = 100;
        TelNet testTelNet = new TelNet(lbg);

        Random random = new Random();

        for (int i = 0; i < n; i++) {
            int x = random.nextInt(xMax);
            int y = random.nextInt(yMax);

            if (!testTelNet.addTelKnoten(x,y))
                i--;
        }

        if (testTelNet.computeOptTelNet()) {
            System.out.println(testTelNet.toString());
            testTelNet.drawOptTelNet(xMax, yMax);
        } else {
            System.out.println("Fehler: Kleinster aufspannender Baum konnte nicht gefunden werden.");
        }
    }

    public static void main(java.lang.String[] args) {
        System.out.println("--- TelNetTest --- \n");
        telNetTest();
        //System.out.println("\n--- RandomTelNetTest ---\n");
        //generateRandomTelNet(1000, 1000, 1000);
    }
}
