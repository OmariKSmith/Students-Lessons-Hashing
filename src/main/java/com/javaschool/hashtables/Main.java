package com.javaschool.hashtables;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws InterruptedException {
      // Detective.buildIPTable("src/main/resources/log-1982.txt");
      //  Detective.findOverlaps("src/main/resources/log-1982.txt" ,"src/main/resources/log-2024.txt");


    QuadraticProbingHashTable ll = new QuadraticProbingHashTable();


        for (int i = 0; i <5; i++) {
            System.out.println(" loop index  i = " + i);
            ll.add(i);
        }

        ll.remove(2);

        ll.add(2);
        //System.out.println(ll.find(4));


        //System.out.println(ll.get(500));
        System.out.println(ll);



    }
}
