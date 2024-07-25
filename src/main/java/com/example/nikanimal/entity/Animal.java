package com.example.nikanimal.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private Integer age;


    public Animal(long id, String name, String type, Integer age) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public Animal(String name, String type, Integer age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public Animal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && age == animal.age && Objects.equals(name, animal.name) && Objects.equals(type, animal.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, age);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", age=" + age +
                '}';
    }
}
