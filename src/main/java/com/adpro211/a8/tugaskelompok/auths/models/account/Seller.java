package com.adpro211.a8.tugaskelompok.auths.models.account;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("seller")
@Setter
@Getter
@NoArgsConstructor
public class Seller extends Account {

    @OneToMany(mappedBy = "orderSeller")
    @JsonIgnore
    private List<Order> orderList;
}
