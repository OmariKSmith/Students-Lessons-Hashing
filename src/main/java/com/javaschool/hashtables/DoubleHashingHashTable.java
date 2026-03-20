package com.javaschool.hashtables;

public class DoubleHashingHashTable<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private static final double LOAD_FACTOR = 0.5;

    @SuppressWarnings("unchecked")
    public DoubleHashingHashTable(int capacity) {
        table = (Entry<K, V>[]) new Entry[capacity];
        size = 0;
    }

    public DoubleHashingHashTable() {
        this(11); // prime table size
    }

    private int hash1(K key) {
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    private int hash2(K key) {
        return 1 + ((key.hashCode() & 0x7fffffff) % (table.length - 1));
    }

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        if ((double) size / table.length >= LOAD_FACTOR) {
            resize(2 * table.length + 1);
        }

        int h1 = hash1(key);
        int h2 = hash2(key);

        int firstDeleted = -1;

        for (int i = 0; i < table.length; i++) {
            int index = (h1 + i * h2) % table.length;
            Entry<K, V> entry = table[index];

            if (entry == null) {
                int target = (firstDeleted != -1) ? firstDeleted : index;
                table[target] = new Entry<>(key, value);
                size++;
                return;
            }

            if (entry.isDeleted) {
                if (firstDeleted == -1) firstDeleted = index;
            } else if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        throw new IllegalStateException("Hash table is full");
    }

    public V get(K key) {
        int h1 = hash1(key);
        int h2 = hash2(key);

        for (int i = 0; i < table.length; i++) {
            int index = (h1 + i * h2) % table.length;
            Entry<K, V> entry = table[index];

            if (entry == null) return null;
            if (!entry.isDeleted && entry.key.equals(key)) return entry.value;
        }

        return null;
    }

    public V remove(K key) {
        int h1 = hash1(key);
        int h2 = hash2(key);

        for (int i = 0; i < table.length; i++) {
            int index = (h1 + i * h2) % table.length;
            Entry<K, V> entry = table[index];

            if (entry == null) return null;
            if (!entry.isDeleted && entry.key.equals(key)) {
                entry.isDeleted = true;
                size--;
                return entry.value;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] old = table;
        table = (Entry<K, V>[]) new Entry[newCapacity];
        size = 0;

        for (Entry<K, V> e : old) {
            if (e != null && !e.isDeleted) {
                put(e.key, e.value);
            }
        }
    }

    public static void main(String[] args) {
        DoubleHashingHashTable<Integer, String> map = new DoubleHashingHashTable<>();

        map.put(3, "one");


        System.out.println(map.get(3));
        System.out.println(map.get(12));
        System.out.println(map.get(23));

        map.remove(12);
        System.out.println(map.get(12));
    }
}
