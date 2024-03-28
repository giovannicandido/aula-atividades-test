package org.example.chatgpt.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder
public class ResponseMessage {

    private List<Choice> choices;
    @Value
    @Builder
    @Jacksonized
    public static class Choice {
        private Message message;
    }
}
