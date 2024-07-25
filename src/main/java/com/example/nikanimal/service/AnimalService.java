package com.example.nikanimal.service;

import com.example.nikanimal.entity.Animal;

public interface AnimalService {
    Animal create(Animal animal);

    Animal getById(Long id);

    Animal update(Animal animal);

    void delete(Long id);

}
