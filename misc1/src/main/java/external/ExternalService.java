package external;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalService {
    public String doMagic(){
        return UUID.randomUUID().toString();
    }
}
