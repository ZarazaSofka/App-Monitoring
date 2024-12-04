package org.example.client;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.example.item.Item;
import org.example.product.Product;

import java.util.List;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @OneToMany(targetEntity=Product.class, mappedBy="owner")
    private List<Product> products;
    @OneToMany(targetEntity=Item.class, mappedBy="client")
    private List<Item> items;
    @ManyToMany(targetEntity=Product.class)
    private List<Product> favorites;

}
