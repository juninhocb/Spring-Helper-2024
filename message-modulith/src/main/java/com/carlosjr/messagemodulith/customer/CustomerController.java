package com.carlosjr.messagemodulith.customer;

import com.carlosjr.messagemodulith.product.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void orderABuy(@RequestBody Customer customer,
                          @RequestParam(value = "type") char c){

        customerService.orderABuy(customer, ProductType.getTypeFromChar(c));

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Exception> handleThisException(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(ex);
    }
}
