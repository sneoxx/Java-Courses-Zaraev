package com.zaraev.epam.javacourses.repository;

import com.zaraev.epam.javacourses.domain.entity.Product;
import com.zaraev.epam.javacourses.repository.impl.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@Profile("!local")
@Component
@Slf4j
public class ProductRepository implements IProductRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * Запись в БД екземпляра Product
     *
     * @param product - экземпляр supplier для занесения в Product
     * @return - вернет занесенный экземпляр Product
     */
    @Override
    public Product create(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(product);
            transaction.commit();
            entityManager.close();
            log.info("createProduct() Объект Product создан и занесен в БД: {}", product);
            return product;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Изменение в БД экземпляра product
     *
     * @param product - экземпляр product, который необходимо изменить
     */
    @Override
    public void update(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            log.debug("updateProduct() Объект product передан на обновление: {} ", product);
            transaction.begin();
            entityManager.merge(product);
            transaction.commit();
            entityManager.close();
            log.info("updateProduct() Объект product успешно обновлен: {} ", product);
        } catch (Exception e) {
            log.error("updateProduct() Ошибка обновления объекта product: ", e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Получение из БД объекта Product
     *
     * @param id - id объекта Product который необходимо получить
     * @return - объект Product из БД
     */
    @Override
    public Product getProduct(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Product product = null;
        try {
            product = entityManager.find(Product.class, id);
            log.info("getProduct() Объект product успешно получен из БД {}", product);
            entityManager.close();
            return product;
        } catch (Exception e) {
            log.error("getProduct() Ошибка получения из БД объекта product: ", e);
        } finally {
            entityManager.close();
        }
        return product;
    }

    /**
     * Получение из БД объекта Product по полю orderNumber Product
     *
     * @param productName - id объекта Order который необходимо получить
     * @return - объект Order из БД
     */
    @Override
    public Product getProductWithInstance(String productName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Product product = null;
        try {
            TypedQuery<Product> query = entityManager.createQuery(
                    "SELECT u FROM Product u WHERE u.productName = :productName", Product.class);
            product = query.setParameter("productName", productName)
                    .getSingleResult();
            log.info("getCustomer() Объект product успешно получен из БД {}", product);
            entityManager.close();
            return product;
        } catch (Exception e) {
            log.error("getCustomer() Ошибка получения из БД объекта product: ", e);
        } finally {
            entityManager.close();
        }
        return product;
    }

    /**
     * Получение из БД всех объектов Product
     *
     * @return - Коллекция List всех объектов Product из БД
     */
    @Override
    public List<Product> getAllProduct() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Product> productList = null;
        try {
            String query = "SELECT p FROM Product p";
            productList = entityManager
                    .createQuery(query, Product.class)
                    .getResultList();
            entityManager.close();
            log.info("getAllProduct() Выведен список всех Product: {}", productList);
            return productList;
        } catch (Exception e) {
            log.error("getAllProduct() Ошибка получения из БД объектов product: ", e);
        } finally {
            entityManager.close();
        }
        return productList;
    }

    /**
     * Удаление объекта product из БД
     *
     * @param product - удаляемый объект
     */
    @Override
    public void deleteProduct(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Product tempProduct = entityManager.find(Product.class, product.getProductId());
            transaction.begin();
            log.debug("deleteProduct() Объект supplier передан на удаление: {}", tempProduct);
            if (entityManager.contains(tempProduct)) {
                entityManager.remove(tempProduct);
                transaction.commit();
                log.info("deleteProduct() Объект supplier успешно удален: {}", tempProduct);
            }
            entityManager.close();
        } catch (Exception e) {
            log.error("deleteProduct() Ошибка удаления объекта product: ", e);
        } finally {
            entityManager.close();
        }
    }

    /**
     * Удаление объекта Product из БД по id
     *
     * @param id- id удаляемого Product
     */
    @Override
    public void deleteProductWithId(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Product tempProduct = entityManager.find(Product.class, id);
            transaction.begin();
            log.debug("deleteProductWithId Объект product передан на удаление: {}", tempProduct);
            if (entityManager.contains(tempProduct)) {
                entityManager.remove(tempProduct);
                transaction.commit();
                log.info("deleteProductWithId Объект order успешно удален: {}", tempProduct);
            }
            entityManager.close();
        } catch (Exception e) {
            log.error("deleteProductWithId() Ошибка удаления объекта product: ", e);
        } finally {
            entityManager.close();
        }
    }
}