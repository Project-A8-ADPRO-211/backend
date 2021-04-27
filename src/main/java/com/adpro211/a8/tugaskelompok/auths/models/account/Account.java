package com.adpro211.a8.tugaskelompok.auths.models.account;

import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "AccountTbl")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="account_type", discriminatorType = DiscriminatorType.INTEGER)
@Data
public abstract class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "assignedUser")
    @JsonIgnore
    private Set<AuthStrategy> authStrategies;

}
