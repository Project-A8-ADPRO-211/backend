package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class AuthStrategy {

    public abstract String getStrategyName();

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @Setter
    private Account assignedUser;

    public abstract boolean authenticate(Map<String, Object> requestBody);

}
