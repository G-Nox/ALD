package aufgabe4;

public class TelVerbindung implements java.lang.Comparable<TelVerbindung> {
    public TelKnoten u;
    public TelKnoten v;
    public int c;

    public TelVerbindung(TelKnoten u,TelKnoten v, int c) {
        // Legt eine neue Telefonverbindung von u nach v mit Verbindungskosten c an.
        // u - Anfangsknoten.
        // v - Endknoten.
        // c - Verbindungskosten
        this.u = u;
        this.v = v;
        this.c = Math.abs(u.x - v.x) + Math.abs(u.y - v.y); // Manhattan-Distanz
    }


    @Override
    public String toString() {
        return "TelVerbindung " + "Anfang: " + u + "    " + "Ende: " + v +
                "    " + "Gewicht: " + c;
    }

    @Override
    public int compareTo(TelVerbindung o) {
        if (this.c == o.c) {
            return 0;
        } else if (this.c > o.c) {
            return 1;
        }
        return -1;
    }
}
