package com.example.laundry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List; //import List
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repo;

    //insert one customer
    @PostMapping
    public String addCustomer(@RequestBody Customer customer) {
        repo.save(customer);
        return "Customer saved";
    }

    //insert batch customers
    @PostMapping("/batch")
    public String addCustomers(@RequestBody List<Customer> customers) {
    repo.saveAll(customers);
    return customers.size() + " customers saved";
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }
}
