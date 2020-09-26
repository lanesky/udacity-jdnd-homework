package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
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

public class OrderControllerTest {

    private  OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

        User testUser = createUser();
        UserOrder testOrder = createOrder(testUser);
        ArrayList<UserOrder> orderList = new ArrayList<>();
        orderList.add(testOrder);
        when(userRepository.findByUsername("keyun")).thenReturn(testUser);
        when(orderRepository.findByUser(testUser)).thenReturn(orderList);
    }

    @Test
    public void testSubmitOrder() {
        ResponseEntity<UserOrder> response = orderController.submit("keyun");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder order = response.getBody();
        assertNotNull(order);
        assertEquals("keyun",order.getUser().getUsername());

        ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser("keyun");
        assertNotNull(response);
        assertEquals(200, ordersForUser.getStatusCodeValue());
        List<UserOrder> orders = ordersForUser.getBody();
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    public void testSumitOrderButUserNotExisted() {
        ResponseEntity<UserOrder> response = orderController.submit("notexisted");
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    public void testOrderUserNotFound() {
        ResponseEntity<List<UserOrder>> ordersForUser = orderController.getOrdersForUser("notexisted");
        assertNotNull(ordersForUser);
        assertEquals(HttpStatus.NOT_FOUND.value(), ordersForUser.getStatusCodeValue());
    }

    private User createUser() {
        User u = new User();
        u.setId(1L);
        u.setUsername("keyun");
        Cart c = new Cart();
        c.setUser(u);
        c.addItem(createItem());
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

    private UserOrder createOrder(User user) {
        UserOrder order = new UserOrder();
        order.setUser(user);
        return order;
    }

}
