package com.example.laundry.controller;

import com.example.laundry.Customer;
import com.example.laundry.CustomerRepository;
import com.example.laundry.Order;
import com.example.laundry.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 1. 註冊會員
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody Customer customer) {
        customer.setMember(true);
        Customer saved = customerRepository.save(customer);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("customerId", saved.getId());
        return ResponseEntity.created(URI.create("/api/customers/" + saved.getId())).body(res);
    }

    // 2. 會員登入
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> creds) {
        String username = creds.get("username");
        String password = creds.get("password");
        Optional<Customer> cust = customerRepository.findByName(username);
        Map<String, Object> res = new HashMap<>();
        if (cust.isPresent()) {
            // 假設密碼存於 phone 欄位或另存 password
            if (password.equals(cust.get().getPhone())) {
                res.put("success", true);
                res.put("username", username);
                return ResponseEntity.ok(res);
            }
        }
        res.put("success", false);
        res.put("message", "查無會員或密碼錯誤");
        return ResponseEntity.status(401).body(res);
    }

    // 3. 新增訂單 (需要先建立 Order Entity)
    @PostMapping("/orders")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Order order) {
        Order saved = orderRepository.save(order);
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("orderId", saved.getId());
        return ResponseEntity.created(URI.create("/api/orders/" + saved.getId())).body(res);
    }

    // 4. 查詢訂單
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
