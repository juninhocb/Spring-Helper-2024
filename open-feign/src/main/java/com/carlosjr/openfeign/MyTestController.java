package com.carlosjr.openfeign;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyTestController {

    private final FetchAddress fetchAddress;

    @GetMapping("/endereco")
    public ResponseEntity<Address> getAdress(@RequestParam String cep){
        Address address =  fetchAddress.buscaEnderecoCep(cep);
        return ResponseEntity.ok(address);
    }
}
