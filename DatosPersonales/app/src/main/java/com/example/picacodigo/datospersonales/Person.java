package com.example.picacodigo.datospersonales;

/**
 * Created by victor on 12/11/14.
 */
public class Person {

    private String name;
    private int age;

    Person(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }
}
