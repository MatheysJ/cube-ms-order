package com.cube.order.controllers;

import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/admin/order")
public class AdminOrderController {

    private OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<ResponseOrderDTO>> getAllOrders() {
        log.info("Getting all orders");
        List<ResponseOrderDTO> products = orderService.getAllOrders();

        log.info("Successfully got all orders");
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrderDTO> getOrderById(@PathVariable("id") Long id) {
        log.info("Getting an order by id");
        ResponseOrderDTO order = orderService.getOrderById(id);

        log.info("Successfully got an order by id");
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
