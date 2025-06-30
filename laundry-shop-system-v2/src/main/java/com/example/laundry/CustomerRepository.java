package com.example.laundry;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用於存取 Customer 實體資料的 Repository
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * 根據會員帳號 (name) 查詢會員
     *
     * @param name 會員帳號
     * @return Optional 包含找到的 Customer
     */
    Optional<Customer> findByName(String name);

    /**
     * 根據會員電話 (phone) 查詢會員 (若密碼存放於 phone 欄位示例)
     *
     * @param phone 電話字串
     * @return Optional 包含找到的 Customer
     */
    Optional<Customer> findByPhone(String phone);
}
