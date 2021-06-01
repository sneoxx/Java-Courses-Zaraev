package com.zaraev.epam.javacourses.service.impl;

import com.zaraev.epam.javacourses.domain.entity.Supplier;
import com.zaraev.epam.javacourses.dto.SupplierDTO;
import com.zaraev.epam.javacourses.repository.SupplierRepository;
import com.zaraev.epam.javacourses.service.EService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierService implements EService {

    @Autowired
    private SupplierRepository supplierRepository;// = new SupplierRepository();

    /**
     * Создание случайного supplier и передача на запись в БД
     *
     * @return экземпляр supplier
     */
    public Supplier createRandomSupplier() {
        Supplier supplier = new Supplier();
        supplier.setCompanyName(generateRandomWord());
        supplier.setPhone(getRandomNumber());
        supplierRepository.create(supplier);
        return supplier;
    }


    /**
     * Создание екземпляра supplier и передача на запись в БД на основании объекта supplierDTO
     *
     * @param supplierDTO - Экземпляр supplierDTO
     * @return - экземпляр supplierDTO
     */
    public SupplierDTO create(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setCompanyName(supplierDTO.getCompanyName());
        supplier.setPhone(supplierDTO.getPhone());
        supplierRepository.create(supplier);
        Supplier supplierCheck = supplierRepository.getOrderWithInstance(supplier.getCompanyName());
        return createSupplierDTO(supplierCheck);
    }

    /**
     * Обновление екземпляра supplier и передача на запись в БД
     *
     * @param supplier - Экземпляр supplier
     * @return - Экземпляр supplierDTO
     */
    public SupplierDTO update(Supplier supplier) {
        supplier.setCompanyName(supplier.getCompanyName() + "+" + generateRandomWord());
        supplierRepository.update(supplier);
        Supplier supplierCheck = supplierRepository.getOrderWithInstance(supplier.getCompanyName());
        return createSupplierDTO(supplierCheck);
    }

    /**
     * Обновление екземпляра supplier и передача на запись в БД
     *
     * @param id       - id экземпляра supplier в базе, который необходимо изменить
     * @param supplierDTO - экземпляр supplierDTO, который необходимо изменить
     * @return - Экземпляр supplierDTO
     */
    public SupplierDTO updateSupplierWithId(int id, SupplierDTO supplierDTO) {
        Supplier updateSupplier = supplierRepository.getSupplier(id);
        log.debug("updatesupplierWithId() Объект supplierDTO передан на обновление: {} ", supplierDTO);
        updateSupplier.setCompanyName(supplierDTO.getCompanyName());
        updateSupplier.setPhone(supplierDTO.getPhone());
        log.info("updatesupplierWithId() Объект supplier успешно обновлен: {} ", updateSupplier);
        supplierRepository.update(updateSupplier);
        Supplier supplierCheck = supplierRepository.getOrderWithInstance(updateSupplier.getCompanyName());
        return createSupplierDTO(supplierCheck);
    }

    /**
     * Получение CustomerDTO из базы
     *
     * @param id - id Customer, которое необходимло получить
     * @return - CustomerDTO созданный из полченного Customer
     */
    public SupplierDTO getSupplierr(int id) {
        Supplier supplier = supplierRepository.getSupplier(id);
        return createSupplierDTO(supplier);
    }

    /**
     * Получение всех CustomerDTO из базы
     *
     * @return - CustomerDTO созданный из полученного Customer
     */
    public List<SupplierDTO> getAllSupplier() {
        List<Supplier> customerList = supplierRepository.getAllSupplier();
        List<SupplierDTO> customerDTOList = new ArrayList<>();
        for (Supplier supplier : customerList) {
            customerDTOList.add(createSupplierDTO(supplier));
        }
        return customerDTOList;
    }

    /**
     * Удаление Customer из базы по id
     *
     * @param id - id Customer для удаления
     */
    public void deleteSupplierWithId(int id) {
        supplierRepository.deleteSupplierWithId(id);
    }

    /**
     * Создание SupplierDTO из supplier
     *
     * @param supplier - исходный supplier
     * @return - полученный SupplierDTO
     */
    public SupplierDTO createSupplierDTO(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setSupplierId(supplier.getSupplierId());
        supplierDTO.setCompanyName(supplier.getCompanyName());
        supplierDTO.setPhone(supplier.getPhone());
        return supplierDTO;
    }

    /**
     * Генерация случайного слова
     *
     * @return - случайное слово
     */
    public static String generateRandomWord() {
        Random random = new Random();
        char[] word = new char[random.nextInt(2) + 3];
        for (int j = 0; j < word.length; j++) {
            word[j] = (char) ('a' + random.nextInt(26));
        }
        return new String(word);
    }

    /**
     * Генерация случайного числа в заданном диапазоне
     *
     * @return - случайное число
     */
    public String getRandomNumber() {
        return Integer.toString(1 + (int) (Math.random() * 10000));
    }
}