package org.example.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.example.client.Client;
import org.example.item.Item;

import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Client owner;
    @ManyToMany(targetEntity=Client.class, mappedBy = "favorites")
    @JsonIgnore
    private List<Client> inFavoritesClients;
    @OneToMany(targetEntity= Item.class, mappedBy="product")
    @JsonIgnore
    private List<Item> items;
}
