package aufgabe1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HashDictionary<K extends Comparable <? super K>,V> implements Dictionary<K,V> {

    private int loadFactor;
    private LinkedList<Entry<K,V>>[] data = new LinkedList[3];
    private static final int DEF_CAPACITY = 31;

    public HashDictionary(int loadFactor){
        this.loadFactor=loadFactor;
    }

    public HashDictionary() {
        data = new LinkedList[DEF_CAPACITY];
    }


    @Override
    public V insert(K key, V value) {
        int index = key.hashCode() % data.length;
        if (index < 0) {
            index = index * (-1);
        }

        if (data[index] == null) {
            data[index] = new LinkedList<>();
            data[index].add(new Entry<K,V>(key, value));
            return null;
        }

        for (Entry<K,V> e : data[index]) {
            if (e.getKey().equals(key)) {
                V val = e.getValue();
                e.setValue(value);
                return val;
            }
        }

        data[index].add(new Entry<K,V>(key, value));
        if (data[index].size() > loadFactor) {
            extend();
        }
        return null;
    }


    private void extend() {
        LinkedList<Entry<K,V>>[] bu = new LinkedList[findprimenumber()];

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                continue;
            }


            for (Entry<K,V> e : data[i]) {
                int index = e.getKey().hashCode() % bu.length;

                if (index < 0) {
                    index = index * (-1);
                }

                if (bu[index] == null) {
                    bu[index] = new LinkedList<>();
                }
                bu[index].add(e);
            }



        }
        data = bu;

    }

    private int findprimenumber() {
        int n = data.length * 2;
        return findprimenumberR(n);

    }

   private int findprimenumberR(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                n++;
                findprimenumberR(n);
            }
            return n;
        }
        //no prime
        return -1;
   }




    @Override
    public V search(K key) {
        int index = key.hashCode() % data.length;
        if (index < 0) {
            index = index * (-1);
        }

        if (data[index] != null) {
            for (Entry<K,V> e : data[index]) {
                if (e.getKey().equals(key)) {
                    return e.getValue();
                }
            }



        }
        return  null;
    }

    @Override
    public V remove(K key) {
        int index = key.hashCode() % data.length;
        if (index < 0) {
            index = index * (-1);
        }

        if (data[index] != null) {
            for (Entry<K,V> e : data[index]) {
                if (e.getKey().equals(key)) {
                    V val = e.getValue();
                    data[index].remove(e);
                    return val;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        int n = 0;
        for (int i = 0; i < data.length; i++) {
            n += data[i].size();
        }
        return n;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
       return new HashDictionaryIterator();
    }

    private class HashDictionaryIterator implements Iterator<Entry<K,V>> {
        int arrayPos = 0;
        int ListPos = 0;


        @Override
        public boolean hasNext() {
            while (arrayPos < data.length) {
                if (data[arrayPos] != null) {
                    if (ListPos < data[arrayPos].size()) {
                        return true;
                    }
                }
                arrayPos++;
                ListPos = 0;
            }
            return false;
        }


        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                return null;
            }

            Entry<K,V> e = data[arrayPos].get(ListPos);
            ListPos++;
            return e;
        }

    }
}
