package aufgabe1;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayDictionary<K extends Comparable <? super K>,V> implements Dictionary<K,V> {

    /*private static class Entry<K,V> {
        K key;
        V value;
        Entry(K k, V v) {key = k; value = v;}
    };*/

    private static final int DEF_CAPACITY = 32;
    private int size;
    K key;
    V value;
    private Entry<K,V>[] data;

    @SuppressWarnings("unchecked")
    public SortedArrayDictionary() {
        size = 0;
        data = new Entry[DEF_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int newCapacity) {
        if (newCapacity < size)
            return;
        Entry[] old = data;
        data = new Entry[newCapacity];
        System.arraycopy(old, 0, data, 0, size);
    }


    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);
            // Vorhandener Eintrag wird überschrieben:
        if (i != -1) {
            V r = data[i].getValue();
            data[i].setValue(value);
            return r;
        }
        // Neueintrag:
        if (data.length == size) {
            ensureCapacity(2*size);
        }
        int j = size-1;
        while (j >= 0 && key.compareTo(data[j].getKey()) < 0) {
            data[j+1] = data[j];
            j--;
        }
        data[j+1] = new Entry<K,V>(key,value);
        size++;
        return null;
    }


    @Override
    public V search(K key) {
        int i = searchKey(key);
        if (i >= 0) {
            return data[i].getValue();
        } else {
            return null;
        }
    }



    private int searchKey(K key) {
        int li = 0;
        int re = size - 1;
        while (re >= li) {
            int m = (li + re)/2;
            if (key.compareTo(data[m].getKey()) < 0) {
                re = m - 1;
            }
            else if (key.compareTo(data[m].getKey()) > 0) {
                li = m + 1;
            } else
                return m; // key gefunden
        }
        return -1; // key nicht gefunden
    }


    @Override
    public V remove(K key) {
        int i = searchKey(key);
        if (i == -1) {
            return null;
        }
        // Datensatz loeschen und Lücke schließen
        V r = data[i].getValue();
        for (int j = i; j < size-1; j++) {
            data[j] = data[j+1];
        }
        data[--size] = null;
        return r;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
       return new SortedArrayDictionaryIterator();
    }

    private class SortedArrayDictionaryIterator implements Iterator<Entry<K, V>> {
        int i = -1;

        @Override
        public boolean hasNext() {
            if ((i + 1) < size) {
                return true;
            }
            return false;
        }

        @Override
        public Entry<K,V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[++i];

        }

    }


}
