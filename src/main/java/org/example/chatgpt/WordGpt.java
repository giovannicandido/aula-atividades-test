package org.example.chatgpt;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.HttpConstants;
import org.example.chatgpt.model.Message;
import org.example.chatgpt.model.RequestModel;
import org.example.chatgpt.model.ResponseMessage;
import org.example.chatgpt.model.Role;

public class WordGpt {
    private static String CHAT_GPT_URL = "https://api.openai.com/v1/chat/completions";
    private final ObjectMapper objectMapper;
    private static final String PROMPT = "retorne uma lista de %s palavras com %s letras uma por linha";
    private HttpClient httpClient = HttpClient.newBuilder().build();

    public WordGpt() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<String> load(int listSize, int wordSize) {
        Message message = Message.builder()
                .role(Role.USER)
                .content(PROMPT.formatted(listSize, wordSize))
                .build();
        RequestModel request = RequestModel.builder()
                .messages(List.of(message))
                .model(RequestModel.GPT_3_MODEL)
                .temperature(0.7f)
                .build();
        String requestBody = null;
        try {
            requestBody = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(CHAT_GPT_URL))
                .header(HttpConstants.HEADER_CONTENT_TYPE, HttpConstants.APPLICATION_JSON)
                .header(HttpConstants.HEADER_AUTHORIZATION, "Bearer " + HttpConstants.TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            ResponseMessage responseMessage = objectMapper.readValue(response.body(), ResponseMessage.class);
            return parseWordsFromChatGptResponse(responseMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> parseWordsFromChatGptResponse(ResponseMessage responseMessage) {
        return responseMessage.getChoices()
                .stream()
                .map(ResponseMessage.Choice::getMessage)
                .map(Message::getContent)
                .map(content -> List.of(content.split("[\\r\\n]+")))
                .flatMap(Collection::stream)
                .toList();

    }
}
