package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private  ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        when(itemRepository.findAll()).thenReturn(createItemList());
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(createItem()));
        when(itemRepository.findByName("test")).thenReturn(createItemList());
        when(itemRepository.findByName("empty")).thenReturn(new ArrayList<Item>());
    }

    @Test
    public void testRetrieveItems() {
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = response.getBody();
        assertNotNull(items);
        assertEquals(1, items.size());

        //retrieve one item
        ResponseEntity<Item> responseForItem = itemController.getItemById(1L);
        assertNotNull(responseForItem);
        assertEquals(200, responseForItem.getStatusCodeValue());


        final ResponseEntity<List<Item>> responseForItemName = itemController.getItemsByName("test");
        assertNotNull(response);
        assertEquals(200, responseForItemName.getStatusCodeValue());
        items = responseForItemName.getBody();
        assertNotNull(items);
        assertEquals(1, items.size());

    }

    @Test
    public void testItemNotFoundById() {
        ResponseEntity<Item> response = itemController.getItemById(2L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testItemNotFoundByName() {
        // null
        ResponseEntity<List<Item>> responseForItemName = itemController.getItemsByName("null");
        assertNotNull(responseForItemName);
        assertEquals(HttpStatus.NOT_FOUND.value(), responseForItemName.getStatusCodeValue());

        // empty
        responseForItemName = itemController.getItemsByName("empty");
        assertNotNull(responseForItemName);
        assertEquals(HttpStatus.NOT_FOUND.value(), responseForItemName.getStatusCodeValue());
    }

    private ArrayList<Item> createItemList() {
        ArrayList<Item> list = new ArrayList<Item>();
        list.add(createItem());
        return list;
    }

    private Item createItem() {
        Item i = new Item();
        i.setId(1L);
        i.setName("test");
        i.setPrice(BigDecimal.valueOf(100));
        return i;
    }
}
