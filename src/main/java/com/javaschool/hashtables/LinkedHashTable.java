package com.javaschool.hashtables;

import java.util.LinkedList;

public class LinkedHashTable<K, V> implements HashTable<K, V> {

    private static final int INIT_CAPACITY = 10;

    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private int capacity;


    public LinkedHashTable() {
        this.capacity = INIT_CAPACITY;
        this.table = (LinkedList<Entry<K, V>>[]) new LinkedList[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    @Override
        public void put(K key, V value) {

        int index = hash(key);

        //System.out.println(" hash -> index -> " + index);

        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        // check LinkedList @ index for duplicate key
        for (Entry<K, V> entry : table[index]) {
            // duplicate guard
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        // new entry is 'put' in table
        table[index].add(new Entry<>(key, value));
        size++;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        System.out.println("index @ get " + index);
        LinkedList<Entry<K, V>> bucket = table[index];

        if (bucket == null){
            return null;
        }

        // walker
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes the entry associated with the given key.
     * Returns the removed value, or null if the key was not found.
     */
    public V remove(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];

        if (bucket == null) {
            return null;
        }

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                bucket.remove(entry);
                size--;
                return oldValue;
            }
        }

        return null;
    }
    public LinkedList<Entry<K,V>>[] getBuckets() {
        return table;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LinkedHashTable {\n");

        for (int i = 0; i < capacity; i++) {
            sb.append("  [").append(i).append("] = ");

            if (table[i] == null || table[i].isEmpty()) {
                sb.append("null\n");
            } else {
                sb.append(table[i]).append("\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
