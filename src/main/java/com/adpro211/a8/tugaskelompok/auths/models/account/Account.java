package com.adpro211.a8.tugaskelompok.auths.models.account;

import com.adpro211.a8.tugaskelompok.auths.models.authStrategy.AuthStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "account_tbl")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="account_type", discriminatorType = DiscriminatorType.STRING)
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

    @Column(name="account_type", nullable=false, insertable = false, updatable = false)
    private String accountType;

}
