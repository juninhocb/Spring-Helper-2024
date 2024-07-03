package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MobileServiceTests {


    RestTemplate restTemplate    = new RestTemplate();
    final String ACCESS_TOKEN    = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub21lVXN1YXJpbyI6IlN5c3RlbSIsImRhdGV0aW1lIjoiMjAyMC0wMS0yMCAxNDoxNDo1NyIsInVzZXJfbmFtZSI6InNncGNsb3VkLnN5c3RlbSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdXRob3JpdGllcyI6WyJST0xFX1BFU1FVSVNBUl9FTVBSRUdBRE9SIiwiUk9MRV9SRU1PVkVSX0VNUFJFR0FET1IiLCJST0xFX1BFU1FVSVNBUl9VU1VBUklPIiwiUk9MRV9DQURBU1RSQVJfQ09MQUJPUkFET1IiLCJST0xFX1JFTU9WRVJfQ09MQUJPUkFET1IiLCJST0xFX1JFTU9WRVJfVVNVQVJJTyIsIlJPTEVfQ0FEQVNUUkFSX1VTVUFSSU8iLCJST0xFX0NBREFTVFJBUl9FTVBSRUdBRE9SIiwiUk9MRV9QRVNRVUlTQVJfQ09MQUJPUkFET1IiXSwianRpIjoiNzc1MmY1NmYtZDFiOC00YmYyLWFlOTQtNjQ2NjEwMWNlZjZmIiwiY2xpZW50X2lkIjoic2dwIn0.PyAgxmEIXkVNZ8sXXaJSbruv8dfpd9VYDpVE7aIWz_Q";
    final String SCHEMA          = "sgp111tes";
    final String MOBILE_BASE_URL = "https://sgpcloud.circuitec.com.br:8433";

    @Test
    void shouldGetEmpregadorMobileDto(){
        String identificador = "04.525.434/0001-66";
        String endpoint = String.format("%s/empregador?identificador=%s&url=%s", MOBILE_BASE_URL, identificador, SCHEMA);
        ResponseEntity<EmpregadorMobileDto> getResponse = restTemplate
                .exchange(endpoint,
                        HttpMethod.GET,
                        getHttpEntity(null, true),
                        EmpregadorMobileDto.class);

        assert getResponse != null;
        assert getResponse.getBody() != null;

        System.out.println(getResponse.getBody());
    }

    private HttpEntity getHttpEntity(Object obj, Boolean setBearer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if ( setBearer ){
            headers.set("Authorization", ACCESS_TOKEN);
        } else {

            headers.set("X-TenantId", SCHEMA);
        }


        if (obj != null){
            return new HttpEntity(obj, headers);
        }
        return new HttpEntity(headers);
    }
}

 class EmpregadorMobileDto {


        private Long id;
        private String nome;
        private String identificador;
        private String tipoIdentificador;
        private Integer timezone;
        private boolean permitePonto;
        private String url;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getIdentificador() {
            return identificador;
        }

        public void setIdentificador(String identificador) {
            this.identificador = identificador;
        }

        public String getTipoIdentificador() {
            return tipoIdentificador;
        }

        public void setTipoIdentificador(String tipoIdentificador) {
            this.tipoIdentificador = tipoIdentificador;
        }

        public Integer getTimezone() {
            return timezone;
        }

        public void setTimezone(Integer timezone) {
            this.timezone = timezone;
        }

        public boolean isPermitePonto() {
            return permitePonto;
        }

        public void setPermitePonto(boolean permitePonto) {
            this.permitePonto = permitePonto;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            EmpregadorMobileDto other = (EmpregadorMobileDto) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }


        @Override
        public String toString() {
            return "EmpregadorMobileDto{" + "id=" + id + ", nome=" + nome + ", identificador=" + identificador + ", tipoIdentificador=" + tipoIdentificador + ", timezone=" + timezone + ", permitePonto=" + permitePonto + ", url=" + url + '}';
        }


    }
