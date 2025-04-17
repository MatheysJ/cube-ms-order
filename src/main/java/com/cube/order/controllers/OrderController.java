package com.cube.order.controllers;

import com.cube.order.dtos.request.SubmitOrderDTO;
import com.cube.order.dtos.response.ResponseOrderDTO;
import com.cube.order.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {

    private OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<ResponseOrderDTO>> getOrdersByUser(@RequestHeader("user") String user) {
        log.info("Getting all user orders");
        List<ResponseOrderDTO> order = orderService.getOrdersByUser(user);

        log.info("Successfully got all user orders");
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrderDTO> getOrderByUserAndId(
            @PathVariable("id") Long id,
            @RequestHeader("user") String user
    ) {
        log.info("Getting a user's order by id");
        ResponseOrderDTO order = orderService.getOrderByUserAndId(user, id);

        log.info("Successfully got a user's order by id");
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PostMapping()
    public ResponseEntity<ResponseOrderDTO> submitOrder(
            @Valid @RequestBody SubmitOrderDTO body,
            @RequestHeader("user") String user
    ) {
        log.info("Creating user's order");
        ResponseOrderDTO order = orderService.submitOrder(body, user);

        log.info("Successfully created user's order");
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
