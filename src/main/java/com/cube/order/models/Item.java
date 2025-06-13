package com.cube.order.models;

import com.cube.order.utils.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
@Entity(name = "items")
@EqualsAndHashCode(of = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    private String id;

    private String name;

    private String image;

    private String summary;

    private String description;

    private String categoryId;

    private Double price;

    private Integer quantity;

    @Convert(converter = MapToJsonConverter.class)
    @Column(columnDefinition = "CLOB")
    private Map<String, Object> options;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
