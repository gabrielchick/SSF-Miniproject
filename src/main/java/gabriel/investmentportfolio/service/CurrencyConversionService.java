package gabriel.investmentportfolio.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import gabriel.investmentportfolio.model.CurrencyApiResponse;

@Service
public class CurrencyConversionService {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/1354710cbe51226f3c34416b/latest/SGD"; // Replace with your API key
    private RestTemplate restTemplate;

    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double getConversionRate() {
        String url = API_URL;
        try {
            // Send the GET request and get detailed response
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String responseBody = responseEntity.getBody();

            // Manually deserialize the response
            if (responseBody != null && !responseBody.isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper();
                CurrencyApiResponse response = objectMapper.readValue(responseBody, CurrencyApiResponse.class);
                if (response != null && response.getConversion_rates() != null) {
                    return response.getConversion_rates().get("USD");
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching conversion rate: " + e.getMessage());
            e.printStackTrace();
        }
        return 1.0; // Default value
    }

}
