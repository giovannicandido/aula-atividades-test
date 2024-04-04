package org.example.geminiai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GeminiWordLoader {
    private static String GEMINI_PROMPT_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String APPLICATION_JSON = "application/json";
    private static final String TOKEN = "AIzaSyA0qE4kEK58ianCgeQVxxhyrmIpReb4BK0";
    private HttpClient httpClient = HttpClient.newBuilder().build();
    private ObjectMapper objectMapper = new ObjectMapper();

    // { "contents":[
    //      { "parts":[{"text": "Write a story about a magic backpack"}]}
    //    ]
    //  }
    public List<String> load(int listSize, int wordSize) {

        String jsonPost = "";
        URI uri = null;
        String prompt = """
                {
                  "contents":[
                     {
                       "role": "user",
                       "parts":[{"text": "Retorne uma lista de %s palavras com %s letras uma por linha, crie de forma randomica"}]
                     }
                  ]
                }
                """.formatted(listSize, wordSize);
        jsonPost = prompt;
        try {
            uri = new URIBuilder(GEMINI_PROMPT_URL)
                    .addParameter("key", TOKEN)
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header(HEADER_CONTENT_TYPE, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPost))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JsonNode responseJson = objectMapper.readTree(response.body());

            String responseText = responseJson
                    .at("/candidates/0/content/parts/0/text")
                    .asText();
            System.out.println(responseText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }
}
