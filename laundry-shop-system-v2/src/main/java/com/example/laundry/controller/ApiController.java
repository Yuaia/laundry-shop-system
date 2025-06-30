package com.example.laundry.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.laundry.Customer;
import com.example.laundry.CustomerRepository;
import com.example.laundry.Order;
import com.example.laundry.OrderRepository;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody Customer customer) {
        customer.setMember(true);
        Customer saved = customerRepository.save(customer);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("customerId", saved.getId());
        return ResponseEntity.created(URI.create("/api/customers/" + saved.getId())).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> creds) {
        String username = creds.get("username");
        String password = creds.get("password");
        Optional<Customer> cust = customerRepository.findByName(username);
        Map<String, Object> res = new HashMap<>();
        if (cust.isPresent() && password.equals(cust.get().getPhone())) {
            res.put("success", true);
            res.put("username", username);
            return ResponseEntity.ok(res);
        }
        res.put("success", false);
        res.put("message", "查無會員或密碼錯誤");
        return ResponseEntity.status(401).body(res);
    }

    @PostMapping("/orders")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {
        order.setOrderTime(LocalDateTime.now());
        Order saved = orderRepository.save(order);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("orderId", saved.getId());
        return ResponseEntity.created(URI.create("/api/orders/" + saved.getId())).body(res);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAllByOrderByOrderTimeAsc();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
