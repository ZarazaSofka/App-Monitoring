package org.example.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void addItem(Item Item) {
        itemRepository.save(Item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItem(Long id) {
        return itemRepository.findById(id);
    }

    public void updateItem(Item Item) {
        itemRepository.save(Item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
