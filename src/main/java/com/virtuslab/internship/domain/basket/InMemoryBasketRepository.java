package com.virtuslab.internship.domain.basket;

import static java.util.Objects.isNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public class InMemoryBasketRepository implements CrudRepository<Basket, UUID> {

    Map<UUID, Basket> baskets = new ConcurrentHashMap<>();

    @Override
    public <S extends Basket> S save(S source) {
        if (isNull(source.getUuid())) {
            var uuid = UUID.randomUUID();
            source.setUuid(uuid);
            baskets.put(uuid, source);
        } else {
            var entity = baskets.get(source.getUuid());
            updateProducts(source, entity);
        }

        return source;
    }

    private void updateProducts(Basket source, Basket entity) {
        if (isNull(entity)) {
            throw new NullPointerException(String.format("Basket with uuid doesn't exists: %s", source.getUuid()));
        }
        entity.addProducts(source.getProducts()
                .stream()
                .filter(product -> entity.getProducts().stream().noneMatch(product::equals))
                .toList());
    }

    @Override
    public <S extends Basket> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();
        entities.forEach(entity -> savedEntities.add(save(entity)));
        return savedEntities;
    }

    @Override
    public Optional<Basket> findById(UUID id) {
        return Optional.of(baskets.get(id));
    }

    @Override
    public boolean existsById(UUID id) {
        return !isNull(baskets.get(id));
    }

    @Override
    public Iterable<Basket> findAll() {
        return baskets.values();
    }

    @Override
    public Iterable<Basket> findAllById(Iterable<UUID> id) {
        List<UUID> uuidsCopy = new ArrayList<>();
        id.forEach(uuidsCopy::add);
        return baskets.values()
                .stream()
                .filter(basket -> uuidsCopy.stream().anyMatch(uuid -> uuid.equals(basket.getUuid())))
                .collect(Collectors.toSet());
    }

    @Override
    public long count() {
        return baskets.size();
    }

    @Override
    public void deleteById(UUID uuid) {
        baskets.remove(uuid);
    }

    @Override
    public void delete(Basket entity) {
        baskets.remove(entity.getUuid(), entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {
        List<UUID> uuidsCopy = new ArrayList<>();
        uuids.forEach(uuidsCopy::add);
        baskets.keySet().forEach(key -> {
            if (uuidsCopy.stream().anyMatch(uuid -> uuid.equals(key))) {
                baskets.remove(key);
            }
        });
    }

    @Override
    public void deleteAll(Iterable<? extends Basket> entities) {
        entities.forEach(entity -> baskets.remove(entity.getUuid()));
    }

    @Override
    public void deleteAll() {
        baskets.keySet().forEach(baskets::remove);
    }
}
