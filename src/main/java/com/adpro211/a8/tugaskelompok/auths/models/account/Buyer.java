package com.adpro211.a8.tugaskelompok.auths.models.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
@Data
@NoArgsConstructor
public class Buyer extends Account{

    private String alamat;

}
