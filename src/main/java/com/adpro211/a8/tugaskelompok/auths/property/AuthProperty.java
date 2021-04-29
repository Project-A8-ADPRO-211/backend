package com.adpro211.a8.tugaskelompok.auths.property;


import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "auth")
@Validated
public class AuthProperty {

    @NotNull
    private String jwtSecretKey = "secretDefault";

    @NotNull
    private String jwtIssuer = "NaradhipaBhary";

}
