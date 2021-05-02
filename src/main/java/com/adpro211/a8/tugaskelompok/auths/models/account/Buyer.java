package com.adpro211.a8.tugaskelompok.auths.models.account;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("buyer")
@Setter
@Getter
@NoArgsConstructor
public class Buyer extends Account{

    private String alamat;

}
