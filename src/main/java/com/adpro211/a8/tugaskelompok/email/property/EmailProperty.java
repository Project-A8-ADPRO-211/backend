package com.adpro211.a8.tugaskelompok.email.property;

import com.sun.istack.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "email")
@Validated
public class EmailProperty {

    @NotNull
    private String domain = "mg.naradhipabhary.com";

    @NotNull
    private String apiKey = "";

    @NotNull
    private String fromName = "Kantin Virtual";

    @NotNull
    private String fromEmail = "no-reply@adpro.naradhipabhary.com";

}
