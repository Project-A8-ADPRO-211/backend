package com.adpro211.a8.tugaskelompok.auths.models.account;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("admin")
@Setter
@Getter
@NoArgsConstructor
public class Administrator extends Account{
}
