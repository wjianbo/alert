package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findById(long id);
    void deleteById(Long id);
}