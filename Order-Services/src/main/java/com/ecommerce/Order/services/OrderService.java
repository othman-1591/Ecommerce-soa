package com.ecommerce.Order.services;

import com.ecommerce.Order.models.Order;
import com.ecommerce.Order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order addOrder(Order order) {
       return orderRepository.saveOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    public boolean updateOrder(Long id, Order order) {
    return orderRepository.updateOrder(id, order);
}
    public Boolean deleteOrder(Long id) {
       return orderRepository.deleteOrder(id);
    }

    public List<Order> getOrdersByClientId(Long clientId) {
        return (List<Order>) orderRepository.findOrdersByClientId(clientId);
    }

}
