package com.carlosjr.sfsocket.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    static final String FIND_ALL = "SELECT ( id, name, age ) FROM customer";
    static final String FIND_BY_ID = "SELECT ( id, name, age ) FROM customer WHERE id = ? ";
    static final String INSERT_INTO = "INSERT INTO customer ( name, age ) VALUES ( ? , ? ) ";
    static final String DELETE_BY_ID = "DELETE FROM customer WHERE id = ? ";

    private final JdbcTemplate jdbcTemplate;

    private final CustomerRowMapper customerRowMapper;

    List<Customer> getAll(){
        return jdbcTemplate.query(FIND_ALL, customerRowMapper);
    }

    Optional<Customer> getById(Integer id){
        return Optional
                .ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, customerRowMapper, id));
    }

    Customer create(Customer customer){

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(INSERT_INTO, keyHolder, customer.name(), customer.age());

        if ( keyHolder.getKey() instanceof Integer id){

            var customerOptional = getById(id);

            if ( customerOptional.isPresent()){
                return customerOptional.get();
            }

        }

        throw new CustomerRepositoryException("Cannot create customer ::: " + customer);
    }
    void deleteById(Integer id){

        jdbcTemplate.update(DELETE_BY_ID, id);

    }







}
