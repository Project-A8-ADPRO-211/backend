package com.adpro211.a8.tugaskelompok.commons.configuration;

import com.adpro211.a8.tugaskelompok.auths.resolver.AdminAccountFromTokenResolver;
import com.adpro211.a8.tugaskelompok.auths.resolver.BaseAccountFromTokenResolver;
import com.adpro211.a8.tugaskelompok.auths.resolver.BuyerAccountFromTokenResolver;
import com.adpro211.a8.tugaskelompok.auths.resolver.SellerAccountFromTokenResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebMvc
class WebMvcContext extends WebMvcConfigurerAdapter {

    @Autowired
    private BaseAccountFromTokenResolver baseAccountFromTokenResolver;

    @Autowired
    private AdminAccountFromTokenResolver adminAccountFromTokenResolver;

    @Autowired
    private BuyerAccountFromTokenResolver buyerAccountFromTokenResolver;

    @Autowired
    private SellerAccountFromTokenResolver sellerAccountFromTokenResolver;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(adminAccountFromTokenResolver);
        argumentResolvers.add(sellerAccountFromTokenResolver);
        argumentResolvers.add(buyerAccountFromTokenResolver);
        argumentResolvers.add(baseAccountFromTokenResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}