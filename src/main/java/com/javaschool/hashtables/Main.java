package com.javaschool.hashtables;

public class Main {
    public static void main(String[] args) {
      // Detective.buildIPTable("src/main/resources/log-1982.txt");
      //  Detective.findOverlaps("src/main/resources/log-1982.txt" ,"src/main/resources/log-2024.txt");


    QuadraticProbingHashTable ll = new QuadraticProbingHashTable();
        ll.add(0);
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);
        ll.add(6);
        ll.add(7);
        ll.add(8);
        ll.add(9);
        ll.add(10);

        for (int i = 0; i < 10; i++) {
            System.out.println((int)(Math.pow(i,2)) % 11);
        }

        //System.out.println(ll.get(500));
        System.out.println(ll.find(3));



    }
}
