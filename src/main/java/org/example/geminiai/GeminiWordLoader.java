package org.example.geminiai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.net.URIBuilder;
import org.example.HttpConstants;
import org.example.wordloader.WordLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
public class GeminiWordLoader implements WordLoader {
    private static String GEMINI_PROMPT_URL = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent";
    private HttpClient httpClient = HttpClient.newBuilder().build();
    private ObjectMapper objectMapper = new ObjectMapper();
    private int listSize;
    private int wordSize;

    public GeminiWordLoader(int listSize, int wordSize) {
        this.listSize = listSize;
        this.wordSize = wordSize;
    }

    public GeminiWordLoader() {
    }

    // { "contents":[
    //      { "parts":[{"text": "Write a story about a magic backpack"}]}
    //    ]
    //  }
    public List<String> load(int listSize, int wordSize) {

        URI uri = null;
        String prompt = buildPrompt(
                "Retorne uma lista de %s palavras com %s letras em formato json onde a chave é words"
                        .formatted(listSize, wordSize)
        );

        try {
            uri = new URIBuilder(GEMINI_PROMPT_URL)
                    .addParameter("key", HttpConstants.TOKEN)
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header(HttpConstants.HEADER_CONTENT_TYPE, HttpConstants.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(prompt))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            JsonNode responseJson = objectMapper.readTree(response.body());

            String responseText = responseJson
                    .at("/candidates/0/content/parts/0/text")
                    .asText();

            responseText = responseText.replaceAll("```json", "");
            responseText = responseText.replaceAll("```", "");

            GeminiWordsResponse geminiWordsResponse = objectMapper.readValue(responseText, GeminiWordsResponse.class);

            return geminiWordsResponse.getWords();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildPrompt(String prompt) {
        return """
                {
                  "contents":[
                     {
                       "role": "user",
                       "parts":[{"text": "%s"}]
                     }
                  ]
                }
                """.formatted(prompt);
    }

    @Override
    public String load() {
        return load(this.listSize, this.wordSize).
                stream().findFirst().orElse("");
    }
}
