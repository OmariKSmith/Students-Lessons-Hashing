package com.javaschool.hashtables;

public class QuadraticProbingHashTable {

    private Integer[] keys;
    private boolean[] used;   // tracks which slots were ever used (for probing)
    private int size;
    private int capacity;
    private final double maxLoadFactor = 0.75;

    public QuadraticProbingHashTable() {
        this.capacity = 5;   // required initial capacity
        this.keys = new Integer[capacity];
        this.used = new boolean[capacity];
        this.size = 0;
    }

    // Required hash function: h(x) = x^2 mod m
    private int hash(int x) {
        long sq = (long) x * (long) x;
        return (int) (sq % capacity);
    }

    // Required probing: offset(i) = (i + i^2) / 2
    private int probeIndex(int h, int i) {
        int offset = (i + i * i) / 2;
        return (h + offset) % capacity;
    }

    private double loadFactor() {
        return (double) size / capacity;
    }

    // Resize when load factor > 0.75 (double size)
    private void resize() {
        int oldCapacity = capacity;
        Integer[] oldKeys = keys;
        boolean[] oldUsed = used;

        capacity *= 2;
        keys = new Integer[capacity];
        used = new boolean[capacity];
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            if (oldKeys[i] != null) {
                add(oldKeys[i]);
            }
        }
    }

    // Add operation
    public void add(int key) {
        if (loadFactor() > maxLoadFactor) {
            resize();
        }

        int h = hash(key);

        for (int i = 0; i < capacity; i++) {
            int idx = probeIndex(h, i);

            if (keys[idx] == null) {
                keys[idx] = key;
                used[idx] = true;
                size++;
                return;
            }

            if (keys[idx] != null && keys[idx] == key) {
                return; // duplicate add ignored
            }
        }
    }

    // Find operation
    public boolean find(int key) {
        int h = hash(key);

        for (int i = 0; i < capacity; i++) {
            int idx = probeIndex(h, i);

            if (!used[idx]) return false; // never used → key cannot be here
            if (keys[idx] != null && keys[idx] == key) return true;
        }

        return false;
    }

    // Remove operation
    public void remove(int key) {
        int h = hash(key);

        for (int i = 0; i < capacity; i++) {
            int idx = probeIndex(h, i);

            if (!used[idx])
                return; // key not found
            if (keys[idx] != null && keys[idx] == key) {
                keys[idx] = null;
                size--;
                rehashCluster(idx);
                return;
            }
        }
    }

    // Rehash cluster after deletion
    private void rehashCluster(int start) {
        int i = 1;

        while (true) {
            int idx = (start + i) % capacity;

            if (!used[idx] || keys[idx] == null) return;

            int keyToRehash = keys[idx];
            keys[idx] = null;
            size--;

            add(keyToRehash);

            i++;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table (size=").append(size)
          .append(", capacity=").append(capacity).append(")\n");

        for (int i = 0; i < capacity; i++) {
            sb.append(i).append(": ");
            sb.append(keys[i] == null ? "---" : keys[i]);
            sb.append("\n");
        }
        return sb.toString();
    }
}
