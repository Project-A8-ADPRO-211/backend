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

    @NotNull
    private String SsoProviderUrl = "http://sso.ui.ac.id/cas2";

    @NotNull
    private String CasCalbackUrl = "http://localhost:8080/login?strategy=sso-ui";


}
