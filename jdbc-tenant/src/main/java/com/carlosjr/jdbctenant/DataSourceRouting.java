package com.carlosjr.jdbctenant;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class DataSourceRouting extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication.getAuthorities().stream().anyMatch(a -> a.toString().equals("ocli"))){
            return 1;
        }
        if ( authentication.getAuthorities().stream().anyMatch(a -> a.toString().equals("tcli"))) {
            return 2;
        }

        return 0;
    }
}
