package com.example.peking.repository;

import com.example.peking.entity.ProductLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLockRepository extends JpaRepository<ProductLock, Integer> {
}
