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
        // Создаем объект животное для теста
        Animal animal = new Animal(1L, "Tom", "кот", 3);

        // Мокируем поведение метода save в репозитории
        Mockito.when(animalRepository.save(animal)).thenReturn(animal);

        // Вызываем метод create и сохраняем результат
        Animal createdAnimal = animalServiceImpl.create(animal);

        // Проверяем, что метод save был вызван один раз
        Mockito.verify(animalRepository, Mockito.times(1)).save(animal);

        // Проверяем, что созданное животное совпадает с возвращенным результатом
        assertEquals(animal, createdAnimal);
    }

    @Test
    void getById() {
        // Создаем объект животное для теста
        Animal animal = new Animal(1L, "Tom", "кот", 3);

        // Мокируем поведение метода findById в репозитории
        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        // Вызываем метод getById
        Animal resultAnimal = animalServiceImpl.getById(1L);

        // Проверяем, что метод findById был вызван один раз с аргументом 1L
        Mockito.verify(animalRepository, Mockito.times(1)).findById(1L);

        // Проверяем, что возвращенное животное соответствует ожидаемому животному
        Assertions.assertEquals(animal, resultAnimal);
    }

    @Test
    public void getByIdNotFound() {
        // Мокируем поведение метода findById в репозитории, чтобы вернуть Optional.empty()
        Mockito.when(animalRepository.findById(2L)).thenReturn(Optional.empty());

        // Проверяем, что при передаче несуществующего id будет выброшено исключение NotFoundException
        Assertions.assertThrows(NotFoundException.class, () -> animalServiceImpl.getById(2L));

        // Проверяем, что метод findById был вызван один раз с аргументом 2L
        Mockito.verify(animalRepository, Mockito.times(1)).findById(2L);
    }


    @Test
    void update() {
        //Подготовка входных данных
        Animal existingAnimal = new Animal(1L, "Tom", "кот", 3);
        Animal updatedAnimal = new Animal(1L, "Tommy", "кот", 4);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(existingAnimal));
        when(animalRepository.save(updatedAnimal)).thenReturn(updatedAnimal);

        //Подготовка ожидаемого результата
        Animal result = animalServiceImpl.update(updatedAnimal);

        //Начало теста
        assertEquals(updatedAnimal, result);
        verify(animalRepository).findById(1L);
        verify(animalRepository).save(updatedAnimal);
    }

    @Test
    void update_NonExistingAnimal_NotFoundExceptionThrown() {
        //Начало теста
        Animal nonExistingAnimal = new Animal(1L, "Tom", "кот", 3);
        //Подготовка ожидаемого результата
        when(animalRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Начало теста
        assertThrows(NotFoundException.class, () -> animalServiceImpl.update(nonExistingAnimal));
        verify(animalRepository).findById(anyLong());
        verify(animalRepository, never()).save(any());
    }

    @Test
    void delete() {
        // Создаем животного для теста
        Animal animal = new Animal(1L, "Tom", "кот", 3);

        // Мокируем поведение метода getById в сервисе
        Mockito.when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        // Вызываем метод delete
        animalServiceImpl.delete(1L);

        // Проверяем, что метод getById был вызван один раз с аргументом 1L
        Mockito.verify(animalRepository, Mockito.times(1)).findById(1L);

        // Проверяем, что метод deleteById был вызван один раз с аргументом 1L
        Mockito.verify(animalRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void getAll() {
        // Создаем список животных
        Animal animal1 = new Animal(1L, "Tom", "кот", 3);
        Animal animal2 = new Animal(1L, "Tommy", "кот", 4);
        List<Animal> animals = Arrays.asList(animal1, animal2);

        // Мокируем поведение метода findAll в репозитории
        Mockito.when(animalRepository.findAll()).thenReturn(animals);

        // Вызываем метод getAll и сохраняем результат
        List<Animal> allAnimals = animalServiceImpl.getAll();

        // Проверяем, что метод findAll был вызван один раз
        Mockito.verify(animalRepository, Mockito.times(1)).findAll();

        // Проверяем, что список животных, возвращенный из сервиса, соответствует списку, который мы задали
        assertEquals(animals, allAnimals);
    }
}