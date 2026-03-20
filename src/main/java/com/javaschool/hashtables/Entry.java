package com.javaschool.hashtables;

public class Entry<K, V> {
    private K key;
    private V value;

    /**
     * Constructs an entry with the specified key and value.
     *
     * @param key   The key associated with this entry.
     * @param value The value associated with this key.
     */
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key associated with this entry.
     *
     * @return The key associated with this entry.
     */
    public K getKey() {
        return this.key;
    }

    /**
     * Sets the key for this entry.
     *
     * @param key The key to set for this entry.
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Returns the value associated with this entry.
     *
     * @return The value associated with this entry.
     */
    public V getValue() {
        return this.value;
    }

    /**
     * Sets the value for this entry.
     *
     * @param value The value to set for this entry.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Determines whether the entries are equal, 
     * returning true if their keys equal.
     *

     */
    public boolean equals(Object obj) {
        Entry<K, V> other = (Entry<K, V>) obj;
        return this.key.equals(other.key);
    }
    @Override
    public String toString() {
        return "(" + key + " -> " + value + ")";
    }

}