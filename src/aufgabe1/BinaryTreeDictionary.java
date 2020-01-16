package aufgabe1;

import aufgabe1.Dictionary;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private static class Node<K, V> {
        private Node<K,V> parent;   // Elternzeiger
        int height;
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;
        private Node(K k, V v) {
            height = 0;
            key = k;
            value = v;
            left = null;
            right = null;
            parent = null;
        }
        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        public K getParent() {
            return (parent != null) ? parent.getKey() : null;
        }
    };

    private int getHeight(Node<K,V> p) {
        if (p == null)
            return -1;
        else
            return p.height;
    }

    private int getBalance(Node<K,V> p) {
        if (p == null)
            return 0;
        else
            return getHeight(p.right) - getHeight(p.left);
    }

    private static class MinEntry<K, V> {
        private K key;
        private V value;
    }

    private Node<K, V> root = null;
    private V oldValue; //Rueckgabeparameter
    private int size = 0;

    @Override
    public V insert(K key, V value) {
        root = insertR(key, value, root);
        if (root != null)
            root.parent = null;
        size++;
        return oldValue;
    }

    private Node<K, V> insertR(K key, V value, Node<K,V> p) {
        if (p == null) {
            p = new Node<>(key,value);
            oldValue = null;
        }
        else if (key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);
            if (p.left != null)
                p.left.parent = p;
        }
        else if (key.compareTo(p.key) > 0) {
            p.right = insertR(key, value, p.right);
            if (p.right != null)
                p.right.parent = p;
        }
        else {  // Schluessel bereits vorhanden:
            oldValue = p.value;
            p.value = value;
        }
        p = balance(p);
        return p;
    }

    private Node<K,V> balance(Node<K,V> p) {
        if (p == null)
            return null;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) +1;
        if (getBalance(p) == -2) {
            if (getBalance(p.left) <= 0)
                p = rotateRight(p);
            else
                p = rotateLeftRight(p);
        }
        else if (getBalance(p) == +2) {
            if (getBalance(p.right) >= 0)
                p = rotateLeft(p);
            else
                p = rotateRightLeft(p);
        }
        return p;
    }

    private Node<K,V> rotateRight(Node<K,V> p) {
        assert p.left != null;
        Node<K, V> q = p.left;
        p.left = q.right;
        if (p.left != null)
            p.left.parent = p;
        q.right = p;
        if (q.right != null)
            q.right.parent = q;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) +1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) +1;
        return q;
    }

    private Node<K, V> rotateLeft(Node<K,V> p) {
        assert p.right != null;
        Node<K,V> q = p.right;
        p.right = q.left;
        if (p.right != null)
            p.right.parent = p;
        q.left = p;
        if (q.left != null)
            q.left.parent = q;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) +1;
        q.height = Math.max(getHeight(q.right), getHeight(q.left)) +1;
        return q;
    }

    private Node<K, V> rotateLeftRight(Node<K,V> p) {
        assert p.left != null;
        p.left = rotateLeft(p.left);
        if (p.left != null)
            p.left.parent = p;
        return rotateRight(p);
    }

    private Node<K, V> rotateRightLeft(Node<K,V> p) {
        assert p.right != null;
        p.right = rotateRight(p.right);
        if (p.right != null)
            p.right.parent = p;
        return rotateLeft(p);
    }

    @Override
    public V search(K key) {
        searchR(key, root);
        return oldValue;
    }


    private Node<K, V> searchR(K key, Node<K,V> p) {
        if (p == null)
            return null;
        else if (key.compareTo(p.key) < 0) {
            if (p.left != null) {
                p = searchR(key, p.left);
                /*if (p.left != null) {
                    p.left.parent = p;
                }*/
            }
        }
        else if (key.compareTo(p.key) > 0) {
            p = searchR(key, p.right);
            /*if (p.right != null) {
                p.right.parent = p;
            }*/
        }
        else
            oldValue = p.value;

        return p;
    }

    @Override
    public V remove(K key) {
        root = removeR(key, root);
        if (root != null) {
            root.parent = null;
        }
        size--;
        return oldValue;
    }

    private Node<K,V> removeR(K key, Node<K,V> p) {
        if (p == null) {
            oldValue = null;
        }
        else if (key.compareTo(p.key) < 0) {
            p.left = removeR(key, p.left);
            if (p.left != null)
                p.left.parent = p;
        }
        else if (key.compareTo(p.key) > 0) {
            p.right = removeR(key, p.right);
            if (p.right != null)
                p.right.parent = p;
        }
        else if (p.left == null || p.right == null) {
            // p muss geloescht werden
            // und hat ein oder kein Kind
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right;
        } else {
            // p muss geloescht werden und hat zwei Kinder:
            MinEntry<K, V> min = new MinEntry<K,V>();
            p.right = getRemMinR(p.right, min);
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        p = balance(p);
        return p;
    }

    private Node<K, V> getRemMinR(Node<K,V> p, MinEntry<K, V> min) {
        assert p != null;
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else {
            p.left = getRemMinR(p.left, min);
            if (p.left != null)
                p.left.parent = p;
        }
        return p;
    }

    @Override
    public int size() {
        return size;
    }


    public void prettyPrint() {
        int tabs = 0;
        prettyPrintR(root, tabs);
    }

    public void prettyPrintR(Node<K,V> p, int tabs) {
        for (int i = 1; i < tabs; i++) {
            System.out.printf("   ");
        }
        if (tabs != 0) {
            System.out.printf("|__");
        }
        // Daten ausgeben und rekursiver Aufruf für Unterelemente
        if (p != null) {
            System.out.print("K: " + p.key);
            //Eltern
            if(p.parent != null)
                System.out.print(" P: " + p.parent.key + " V: " + p.value);
            System.out.println();
            if (p.left != null || p.right != null) {
                tabs = tabs + 1;
                prettyPrintR(p.left, tabs);
                prettyPrintR(p.right, tabs);
            }
        } else {
            // # ausgeben wenn null
            System.out.printf("#\n");
            tabs = tabs - 1;
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new BinaryTreeDictionaryIterator();
    }

    private class BinaryTreeDictionaryIterator implements Iterator<Entry<K, V>> {
        private Node<K, V> p = leftMostDescendant(root);

        @Override
        public boolean hasNext() {
            return (p != null);
        }

        @Override
        public Entry<K,V> next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Entry<K, V> buffer = new Entry<>(p.key, p.value);
            if (p.right != null) {
                p = leftMostDescendant(p.right);
            } else {        // Elternzeiger des linkstesten Kinds
                p = parentOfLeftMostAncestor(p);
            }
            return buffer;
        }

        private Node<K, V> leftMostDescendant(Node<K,V> p) {
            assert p != null;
            while (p.left != null)
                p = p.left;
            return p;
        }

        private Node<K, V> parentOfLeftMostAncestor(Node<K,V> p) {
            assert p != null;
            while (p.parent != null && p.parent.right == p)
                p = p.parent;
            return p.parent;    // kann auch null sein
        }
    }


}