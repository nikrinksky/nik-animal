package com.example.nikanimal.controller;

import com.example.nikanimal.entity.Animal;
import com.example.nikanimal.repository.AnimalRepository;
import com.example.nikanimal.service.AnimalServiceImpl;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalRepository animalRepository;

    @SpyBean
    private AnimalServiceImpl animalService;

    private Long id = 1L;
    private String name = "Том";
    private String type = "кот";
    private int age = 3;

    private Animal animalObject() {
        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);
        animal.setType(type);
        animal.setAge(age);
        return animal;
    }

    public JSONObject animalJSON() {
        JSONObject animalJSON = new JSONObject();
        animalJSON.put("id", id);
        animalJSON.put("name", name);
        animalJSON.put("type", type);
        animalJSON.put("age", age);
        return animalJSON;
    }


    @Test
    void create() throws Exception {
        when(animalRepository.save(any(Animal.class))).thenReturn(animalObject());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/animal")
                        .param("id", String.valueOf(id))
                        .param("name", name)
                        .param("type", type)
                        .param("age", String.valueOf(age))

                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void getById() throws Exception {
        when(animalRepository.findById(any(Long.class))).thenReturn(Optional.of(animalObject()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animal/1")
                        .content(animalJSON().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animal/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        when(animalRepository.findAll()).thenReturn(new ArrayList<>(List.of(animalObject())));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animal")
                        .content(animalJSON().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}