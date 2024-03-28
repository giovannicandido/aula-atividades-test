package org.example.chatgpt.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder
public class RequestModel {
    public static final String GPT_3_MODEL = "gpt-3.5-turbo";
    private String model;
    private List<Message> messages;
    private float temperature;
}
