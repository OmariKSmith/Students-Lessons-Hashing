package com.javaschool.hashtables;

public class DoubleHashingHashTable<K, V> {

    private static final class Entry<K, V> {
        final K key;
        V value;
        boolean deleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private static final double MAX_LOAD = 0.5;

    @SuppressWarnings("unchecked")
    public DoubleHashingHashTable(int capacity) {
        table = (Entry<K, V>[]) new Entry[nextPrime(capacity)];
        size = 0;
    }

    public DoubleHashingHashTable() {
        this(11);
    }

    private int hash(K key) {
        return key.hashCode() & 0x7fffffff;
    }

    private int hash1(int hash) {
        return hash % table.length;
    }

    private int hash2(int hash) {
        return 1 + (hash % (table.length - 1));
    }

    private int probeIndex(int h1, int h2, int i) {
        return (h1 + i * h2) % table.length;
    }

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        if ((double) size / table.length >= MAX_LOAD) {
            resize(2 * table.length);
        }

        int hash = hash(key);
        int h1 = hash1(hash);
        int h2 = hash2(hash);

        int firstDeleted = -1;

        for (int i = 0; i < table.length; i++) {
            int index = probeIndex(h1, h2, i);
            Entry<K, V> e = table[index];

            if (e == null) {
                int target = (firstDeleted != -1) ? firstDeleted : index;
                table[target] = new Entry<>(key, value);
                size++;
                return;
            }

            if (e.deleted) {
                if (firstDeleted == -1) firstDeleted = index;
            } else if (e.key.equals(key)) {
                e.value = value;
                return;
            }
        }

        if (firstDeleted != -1) {
            table[firstDeleted] = new Entry<>(key, value);
            size++;
            return;
        }
    }

    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int hash = hash(key);
        int h1 = hash1(hash);
        int h2 = hash2(hash);

        for (int i = 0; i < table.length; i++) {
            int index = probeIndex(h1, h2, i);
            Entry<K, V> e = table[index];

            if (e == null) return null;

            if (!e.deleted && e.key.equals(key)) {
                return e.value;
            }
        }

        return null;
    }

    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int hash = hash(key);
        int h1 = hash1(hash);
        int h2 = hash2(hash);

        for (int i = 0; i < table.length; i++) {
            int index = probeIndex(h1, h2, i);
            Entry<K, V> e = table[index];

            if (e == null) return null;

            if (!e.deleted && e.key.equals(key)) {
                e.deleted = true;
                size--;
                return e.value;
            }
        }

        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        int prime = nextPrime(newCapacity);
        Entry<K, V>[] old = table;

        table = (Entry<K, V>[]) new Entry[prime];
        size = 0;

        for (Entry<K, V> e : old) {
            if (e != null && !e.deleted) {
                put(e.key, e.value);
            }
        }
    }


    private static int nextPrime(int n) {
        if (n < 2) return 2;
        int candidate = (n % 2 == 0) ? n + 1 : n;
        while (!isPrime(candidate)) candidate += 2;
        return candidate;
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DoubleHashingHashTable {\n");
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> e = table[i];
            sb.append(String.format("  [%2d] = ", i));
            if (e == null)          sb.append("empty");
            else if (e.deleted) sb.append("<deleted>");
            else sb.append("(").append(e.key).append(" -> ").append(e.value).append(")");
            sb.append("\n");
        }
        return sb.append("  size = ").append(size).append("\n}").toString();
    }
}