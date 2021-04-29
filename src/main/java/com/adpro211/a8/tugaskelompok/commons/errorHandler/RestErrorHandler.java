package com.adpro211.a8.tugaskelompok.commons.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;


public class RestErrorHandler extends DefaultResponseErrorHandler {
        @Override
        protected boolean hasError(HttpStatus statusCode) {
            return false;
        }
}
