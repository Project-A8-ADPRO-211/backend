package com.adpro211.a8.tugaskelompok.order.repository;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(int id);

    Iterable<Order> findAllByAccount(Account account);

    Iterable<Order> findAllByNameLike(String name);
}
