package com.zq.spring.definition.d_merge.bean;

public abstract class Animal {

    private Person person;
    
    public Person getPerson() {
        return person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }
}
