package com.cube.order.repositories;

import com.cube.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserIdAndId(String userId, Long id);

    List<Order> findAllByUserId(String userId);

}
