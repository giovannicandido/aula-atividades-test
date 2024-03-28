package org.example.chatgpt.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Message {
    private Role role;
    private String content;
}
