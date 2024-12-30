package desafio3.ms_event_manager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CepService {
    public Map<String, String> getCepInfo(String cep){
        String normalizedCep = cep.replaceAll("\\D", "");
        if(normalizedCep.length() != 8){
            throw new RuntimeException("Invalid CEP format");
        }
        String url = "https://viacep.com.br/ws/" + cep + "/json";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Map.class);
    }
}
