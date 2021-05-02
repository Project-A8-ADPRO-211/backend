package com.adpro211.a8.tugaskelompok.auths.models.account;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

import com.adpro211.a8.tugaskelompok.order.model.order.Order;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("buyer")
@Setter
@Getter
@NoArgsConstructor
public class Buyer extends Account {

    private String alamat;

    @OneToMany(mappedBy = "orderBuyer")
    @JsonIgnore
    private List<Order> orderList;

}
