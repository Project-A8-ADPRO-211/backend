package com.adpro211.a8.tugaskelompok.auths.models.account;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("3")
@Data
@NoArgsConstructor
public class Administrator extends Account{
}
