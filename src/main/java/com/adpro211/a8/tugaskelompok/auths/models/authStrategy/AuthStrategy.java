package com.adpro211.a8.tugaskelompok.auths.models.authStrategy;

import com.adpro211.a8.tugaskelompok.auths.models.account.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public abstract class AuthStrategy {

    public abstract String getStrategyName();

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Account assignedUser;

    public abstract boolean authenticate(Map<String, Object> requestBody);

}
