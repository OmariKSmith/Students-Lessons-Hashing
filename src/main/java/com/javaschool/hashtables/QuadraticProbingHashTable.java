package com.javaschool.hashtables;

public class QuadraticProbingHashTable {

    private Integer[] keys;
    private boolean[] deleted;

    private int size;
    private int deletedCount;
    private int capacity;

    private static final double MAX_LOAD = 0.50;

    public QuadraticProbingHashTable() {
        this.capacity = 11; // must be prime
        this.keys = new Integer[capacity];
        this.deleted = new boolean[capacity];
        this.size = 0;
        this.deletedCount = 0;
    }

    // h(x) = x^2 mod m
    private int hash(int x) {
        long sq = (long) x * (long) x;
        return (int) (sq % capacity);
    }

    private int probeIndex(int h, int i) {
        int offset = (i + i * i) / 2;
        return (h + offset) % capacity;
    }

    private double loadFactor() {
        return (double) size / capacity;
    }


    public void add(int key) {
        // Resize if too full OR too many tombstones
        if (loadFactor() >= MAX_LOAD || deletedCount > size) {
            resize();
        }

        int h = hash(key);
        int firstDeleted = -1;

        for (int i = 0; i < capacity; i++) {
            int idx = probeIndex(h, i);

            // ✅ Found existing key → do nothing
            if (keys[idx] != null && !deleted[idx] && keys[idx] == key) {
                return;
            }

            // ✅ Record tombstone
            if (deleted[idx] && firstDeleted == -1) {
                firstDeleted = idx;
            }

            // ✅ Empty slot → insert
            if (keys[idx] == null && !deleted[idx]) {
                int target = (firstDeleted != -1) ? firstDeleted : idx;

                keys[target] = key;

                if (deleted[target]) {
                    deleted[target] = false;
                    deletedCount--;
                }

                size++;
                return;
            }
        }

        // Found no truly empty found, but tombstone does exist
        if (firstDeleted != -1) {
            keys[firstDeleted] = key;
            deleted[firstDeleted] = false;
            deletedCount--;
            size++;
        }
    }

    public boolean find(int key) {
        int h = hash(key);

        for (int i = 0; i < capacity; i++) {
            int idx = probeIndex(h, i);

            if (keys[idx] == null && !deleted[idx]) return false;

            if (keys[idx] != null && !deleted[idx] && keys[idx] == key) {
                return true;
            }
        }

        return false;
    }

    public void remove(int key) {
        int h = hash(key);

        for (int i = 0; i < capacity; i++) {
            int idx = probeIndex(h, i);

            if (keys[idx] == null && !deleted[idx]) return;

            if (keys[idx] != null && !deleted[idx] && keys[idx] == key) {
                keys[idx] = null;
                deleted[idx] = true;
                size--;
                deletedCount++;
                return;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        int newCapacity = nextPrime(capacity * 2);

        Integer[] oldKeys = keys;
        boolean[] oldDeleted = deleted;

        keys = new Integer[newCapacity];
        deleted = new boolean[newCapacity];
        capacity = newCapacity;

        size = 0;
        deletedCount = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && !oldDeleted[i]) {
                add(oldKeys[i]);
            }
        }
    }


    private int nextPrime(int n) {
        int candidate = (n % 2 == 0) ? n + 1 : n;
        while (!isPrime(candidate)) candidate += 2;
        return candidate;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    // ── Debug View ─────────────────────────────────────────

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table (size=").append(size)
                .append(", capacity=").append(capacity)
                .append(", tombstones=").append(deletedCount)
                .append(")\n");

        for (int i = 0; i < capacity; i++) {
            sb.append(i).append(": ");
            if (keys[i] == null) {
                sb.append(deleted[i] ? "<deleted>" : "---");
            } else {
                sb.append(keys[i]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}