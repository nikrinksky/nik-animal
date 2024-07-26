package com.example.nikanimal.service;

import com.example.nikanimal.entity.Animal;
import com.example.nikanimal.exceptions.NotFoundException;
import com.example.nikanimal.repository.AnimalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {
    @Mock
    private AnimalRepository animalRepository;
    @InjectMocks
    private AnimalServiceImpl animalServiceImpl;

    @Test
    void create() {

        Animal animal = new Animal(1L, "Tom", "кот", 3);

        Mockito.when(animalRepository.save(animal)).thenReturn(animal);

        Animal createdAnimal = animalServiceImpl.create(animal);

        Mockito.verify(animalRepository, Mockito.times(1)).save(animal);

        assertEquals(animal, createdAnimal);
    }

    @Test
    void getById() {

        Animal animal = new Animal(1L, "Tom", "кот", 3);

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        Animal resultAnimal = animalServiceImpl.getById(1L);

        Mockito.verify(animalRepository, Mockito.times(1)).findById(1L);

        Assertions.assertEquals(animal, resultAnimal);
    }

    @Test
    public void getByIdNotFound() {

        Mockito.when(animalRepository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> animalServiceImpl.getById(2L));

        Mockito.verify(animalRepository, Mockito.times(1)).findById(2L);
    }


    @Test
    void update() {

        Animal existingAnimal = new Animal(1L, "Tom", "кот", 3);
        Animal updatedAnimal = new Animal(1L, "Tommy", "кот", 4);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(existingAnimal));
        when(animalRepository.save(updatedAnimal)).thenReturn(updatedAnimal);

        Animal result = animalServiceImpl.update(updatedAnimal);

        assertEquals(updatedAnimal, result);
        verify(animalRepository).findById(1L);
        verify(animalRepository).save(updatedAnimal);
    }

    @Test
    void update_NonExistingAnimal_NotFoundExceptionThrown() {

        Animal nonExistingAnimal = new Animal(1L, "Tom", "кот", 3);

        when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> animalServiceImpl.update(nonExistingAnimal));
        verify(animalRepository).findById(anyLong());
        verify(animalRepository, never()).save(any());
    }

    @Test
    void delete() {

        Animal animal = new Animal(1L, "Tom", "кот", 3);

        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        animalServiceImpl.delete(1L);

        Mockito.verify(animalRepository, Mockito.times(1)).findById(1L);

        Mockito.verify(animalRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void getAll() {

        Animal animal1 = new Animal(1L, "Tom", "кот", 3);
        Animal animal2 = new Animal(1L, "Tommy", "кот", 4);
        List<Animal> animals = Arrays.asList(animal1, animal2);

        Mockito.when(animalRepository.findAll()).thenReturn(animals);

        List<Animal> allAnimals = animalServiceImpl.getAll();

        Mockito.verify(animalRepository, Mockito.times(1)).findAll();

        assertEquals(animals, allAnimals);
    }
}