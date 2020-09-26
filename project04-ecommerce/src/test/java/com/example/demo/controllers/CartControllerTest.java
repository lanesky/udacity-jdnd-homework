package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private  CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);

        when(userRepository.findByUsername("keyun")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(createItem()));
    }

    @Test
    public void testAddCart() {
        ModifyCartRequest r = createCartRequest1();
        ResponseEntity<Cart> response = cartController.addTocart(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart c = response.getBody();
        assertNotNull(c);
        assertEquals("keyun", c.getUser().getUsername());
    }

    @Test
    public void testAddCartUserNotExisted() {
        ModifyCartRequest r = createCartRequestUserNotExisted();
        ResponseEntity<Cart> response = cartController.addTocart(r);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testAddCartItemNotExisted() {
        ModifyCartRequest r = createCartRequestItemNotExisted();
        ResponseEntity<Cart> response = cartController.addTocart(r);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testRemoveCart() {
        ModifyCartRequest r = createCartRequest1();
        ResponseEntity<Cart> response = cartController.removeFromcart(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart c = response.getBody();
        assertNotNull(c);
        assertEquals("keyun", c.getUser().getUsername());
    }

    @Test
    public void testRemoveCartUserNotExisted() {
        ModifyCartRequest r = createCartRequestUserNotExisted();
        ResponseEntity<Cart> response = cartController.removeFromcart(r);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testRemoveCartItemNotExisted() {
        ModifyCartRequest r = createCartRequestItemNotExisted();
        ResponseEntity<Cart> response = cartController.removeFromcart(r);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    private User createUser() {
        User u = new User();
        u.setId(1L);
        u.setUsername("keyun");
        Cart c = new Cart();
        c.setUser(u);
        u.setCart(c);
        return u;
    }

    private Item createItem() {
        Item i = new Item();
        i.setId(1L);
        i.setName("test");
        i.setPrice(BigDecimal.valueOf(100));
        return i;
    }

    private ModifyCartRequest createCartRequest1() {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("keyun");
        r.setItemId(1L);
        r.setQuantity(100);
        return r;
    }

    private ModifyCartRequest createCartRequestUserNotExisted() {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("keyunnotexisted");
        r.setItemId(1L);
        r.setQuantity(100);
        return r;
    }

    private ModifyCartRequest createCartRequestItemNotExisted() {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("keyun");
        r.setItemId(9999L);
        r.setQuantity(100);
        return r;
    }

}
