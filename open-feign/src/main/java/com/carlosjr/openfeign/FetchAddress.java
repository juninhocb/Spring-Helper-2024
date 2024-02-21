package com.carlosjr.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface FetchAddress {

    @GetMapping("{cep}/json")
    Address buscaEnderecoCep(@PathVariable("cep") String cep);

}
