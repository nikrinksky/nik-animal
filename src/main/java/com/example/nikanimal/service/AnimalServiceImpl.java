package com.example.nikanimal.service;

import com.example.nikanimal.entity.Animal;
import com.example.nikanimal.exceptions.NotFoundException;
import com.example.nikanimal.repository.AnimalRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal create(Animal animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Animal getById(Long id) {
        Optional<Animal> animalId = animalRepository.findById(id);
        if (animalId.isEmpty()) {
            throw new NotFoundException("Нет такого животного");
        }
        return animalId.get();
    }

    @Override
    public Animal update(Animal animal) {
        Optional<Animal> animalId = animalRepository.findById(animal.getId());
        if (animalId.isEmpty()) {
            throw new NotFoundException("Такого кота нет");
        }
        Animal animalUpdate = animalId.get();
        if (animal.getName() != null) {
            animalUpdate.setName(animal.getName());
        }

        if (animal.getType() != null) {
            animalUpdate.setType(animal.getType());
        }

        if (animal.getAge() != null) {
            animalUpdate.setAge(animal.getAge());
        }

        return animalRepository.save(animalUpdate);
    }

    @Override
    public void delete(Long id) {
        Optional<Animal> animalId = animalRepository.findById(id);
        if (animalId.isEmpty()) {
            throw new NotFoundException("Нет такого животного");
        }
        animalRepository.deleteById(id);
    }

    @Override
    public List<Animal> getAll() {

        return animalRepository.findAll();
    }

}
