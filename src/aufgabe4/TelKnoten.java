package aufgabe4;

public class TelKnoten implements Comparable<TelKnoten> {
    public int x;
    public int y;

    public TelKnoten(int x, int y) {
        // Legt einen neuen Telefonknoten mit den Koordinaten (x,y) an.
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "TelKnoten " + "x: " + x + "    " + "y: " + y;
    }

    @Override
    public int compareTo(TelKnoten o) {
        if (this.x == o.x && this.y == o.y)
            return 0;
        return -1;
    }

}
