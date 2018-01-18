package com.fizzed.crux.util;

public class ULIDDemo {
 
    static public void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            String ulid = ULID.random();

            System.out.println(ulid);
        }
    }
    
}