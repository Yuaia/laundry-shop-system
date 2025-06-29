package com.example.laundry.controller;

import com.example.laundry.Customer;
import com.example.laundry.CustomerRepository;
import com.example.laundry.Order;
import com.example.laundry.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class FormController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    // 處理會員登入表單
    @PostMapping("/login.html")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttrs) {
        Optional<Customer> opt = customerRepository.findByName(username);
        if (opt.isPresent() && password.equals(opt.get().getPhone())) {
            // 登入成功，導回首頁
            return "redirect:/index.html";
        }
        // 登入失敗，顯示錯誤訊息並導向註冊
        redirectAttrs.addFlashAttribute("error", "查無會員或密碼錯誤");
        return "redirect:/signup.html";
    }

    // 處理註冊表單
    @PostMapping("/signup.html")
    public String processSignup(
            @ModelAttribute Customer customer,
            RedirectAttributes redirectAttrs) {
        customer.setMember(true);
        customerRepository.save(customer);
        redirectAttrs.addFlashAttribute("message", "註冊成功，請登入");
        return "redirect:/login.html";
    }

    // 處理新增訂單表單
    @PostMapping("/order-form.html")
    public String processOrder(
            @RequestParam String orderNumber,
            @RequestParam String type,
            @RequestParam String category,
            @RequestParam Integer quantity,
            @RequestParam String color,
            RedirectAttributes redirectAttrs) {
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setType(type);
        order.setCategory(category);
        order.setQuantity(quantity);
        order.setColor(color);
        orderRepository.save(order);
        redirectAttrs.addFlashAttribute("message", "訂單已建立，編號：" + order.getOrderNumber());
        return "redirect:/order-search.html";
    }
}
