package com.javaschool.hashtables;

public class LinearProbingHashTable<K, V> implements HashTable<K, V> {

    private static final int INIT_CAPACITY = 11;

    private Entry<K, V>[] table;
    private int size;
    private int capacity;


    public LinearProbingHashTable() {
        this.capacity = INIT_CAPACITY;
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    @Override
    public void put(K key, V value) {
        // check cap before adding to avoid index out of bounds error
        if (size >= capacity / 2) {
            resize(capacity * 2);
        }

        int index = hash(key);

        System.out.println(" hash key index " + index);

        // walker loop
        while (table[index] != null) {
            //duplicate guard
            if (table[index].getKey().equals(key)) {
                table[index].setValue(value);   // update existing
                return;
            }
            // circular indexing iterator
            index = (index + 1) % capacity;
        }

        // new entry is 'put' in table
        table[index] = new Entry<>(key, value);

        // updates table size to reflect new method 'put' in  the hash table
        size++;
    }

    @Override
    public V get(K key) {
        int index = hash(key);

        // walker loop
        while (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
            index = (index + 1) % capacity;
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

    public void delete(K key) {
        if (get(key) == null) return;

        int index = hash(key);

        // walker loop
        while (!table[index].getKey().equals(key)) {
            index = (index + 1) % capacity;
        }

        // Remove entry
        table[index] = null;

        // increment index
        index = (index + 1) % capacity;

        // Rehash cluster
        while (table[index] != null) {

            // capture entry
            Entry<K, V> entryToRehash = table[index];

            table[index] = null;
            size--;
            put(entryToRehash.getKey(), entryToRehash.getValue());
            index = (index + 1) % capacity;
        }

        size--;

        if (size > 0 && size <= capacity / 8) {
            resize(capacity / 2);
        }
    }

    private void resize(int newCapacity) {
        LinearProbingHashTable<K, V> temp = new LinearProbingHashTable<>();
        temp.capacity = newCapacity;
        temp.table = (Entry<K, V>[]) new Entry[newCapacity];

        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                temp.put(table[i].getKey(), table[i].getValue());
            }
        }

        this.table = temp.table;
        this.capacity = temp.capacity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LinearProbingHashTable {\n");

        for (int i = 0; i < capacity; i++) {
            sb.append("  [")
                    .append(i)
                    .append("] = ");

            if (table[i] == null) {
                sb.append("null");
            } else {
                sb.append("(")
                        .append(table[i].getKey())
                        .append(" -> ")
                        .append(table[i].getValue())
                        .append(")");
            }

            sb.append("\n");
        }

        sb.append("}");
        return sb.toString();
    }

}
