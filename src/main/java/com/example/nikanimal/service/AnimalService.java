package com.example.nikanimal.service;

import com.example.nikanimal.entity.Animal;
import com.example.nikanimal.exceptions.NotFoundException;

import java.util.List;

public interface AnimalService {
    /**
     * Создает новый объект животное в базе данных.
     *
     * @param animal объект животного, содержащий информацию о создаваемой записи
     * @return возвращает объект животное с присвоенным идентификатором, сохраненный в базе данных
     */
    Animal create(Animal animal);

    /**
     * Возвращает объект животное по его идентификатору.
     *
     * @param id идентификатор животного в базе данных
     * @return возвращает объект животное с указанным Id
     * @throws NotFoundException если животное с указанным идентификатором не найдено в базе данных
     */

    Animal getById(Long id);

    /**
     * Обновляет информацию о животном.
     *
     * @param animal животного, содержащий обновленную информацию
     * @return объект животное с обновленной информацией, сохраненный в базе данных
     * @throws NotFoundException если переданный объект животного не найден в базе данных
     */
    Animal update(Animal animal);

    /**
     * Удаляет запись о животном по указанному идентификатору из базы данных.
     *
     * @param id идентификатор животного, который должен быть удален
     */
    void delete(Long id);

    /**
     * @return возвращает всех животных
     */
    List<Animal> getAll();
}
