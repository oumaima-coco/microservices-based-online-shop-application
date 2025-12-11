package com.ouma.microservices.order.service.repository;

import com.ouma.microservices.order.service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
