package com.cube.order.repositories;

import com.cube.order.models.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "items")
    Optional<Order> findByUserIdAndId(String userId, Long id);

    @EntityGraph(attributePaths = "items")
    List<Order> findAllByUserId(String userId);

    @Override
    @EntityGraph(attributePaths = "items")
    Optional<Order> findById(Long id);

    @EntityGraph(attributePaths = "items")
    Optional<Order> findByAsaasOrderId(String asaasOrderId);

    @Override
    @EntityGraph(attributePaths = "items")
    List<Order> findAll();
}
