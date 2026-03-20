package com.javaschool.hashtables;


public interface HashTable<K,V>{
    /** * Inserts the specified key-value pair into the hash table. * If the key already exists in the hash table, its value is updated to the specifi * * @param key The key with which the specified value is to be associated. * @param value The value to be associated with the specified key. */
    public void put(K key,V value);

    /** * Returns the value associated with the specified key, or null if the hash table c * * @param key The key whose associated value is to be returned. * @return The value associated with the specified key, or null if the hash table c */
    public V get(K key);

    /** * Returns the number of key-value mappings in the hash table. * * @return The number of key-value mappings in the hash table. */
    public int size ();

    /** * Returns true if the hash table contains no key-value mappings. * * @return true if the hash table contains no key-value mappings. */
    public boolean isEmpty ();
}