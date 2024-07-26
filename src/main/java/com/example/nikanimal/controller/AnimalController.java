package com.example.nikanimal.controller;

import com.example.nikanimal.entity.Animal;
import com.example.nikanimal.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
@Tag(name = "Объект животное", description = "CRUD-методы для работы объектом животное")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    @Operation(summary = "Создание животного")
    public Animal create(@RequestParam @Parameter(description = "Имя животного") String name,
                         @RequestParam @Parameter(description = "Вид животного") String type,
                         @RequestParam @Parameter(description = "Возраст животного") Integer age
    ) {

        return animalService.create(new Animal(name, type, age));

    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение животного по id")
    public Animal getById(@PathVariable(value = "id") @Parameter(description = "ID-животного") Long id) {
        return animalService.getById(id);
    }

    @PutMapping
    @Operation(summary = "Изменить информацию о животном")
    public Animal update(
            @RequestParam @Parameter(description = "ID животного") Long id,
            @RequestParam(required = false) @Parameter(description = "Имя животного") String name,
            @RequestParam(required = false) @Parameter(description = "Вид животного") String type,
            @RequestParam(required = false) @Parameter(description = "Возраст животного") Integer age) {
        return animalService.update(new Animal(id, name, type, age));
    }

    @DeleteMapping("/{animal_id}")
    @Operation(summary = "Удаление животного по id")
    public ResponseEntity<Void> deleteById(@Parameter(description = "ID животного") Long id) {
        try {
            animalService.delete(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Получение всех животных")
    public ResponseEntity<List<Animal>> getAll() {
        return ResponseEntity.ok(animalService.getAll());
    }

}
