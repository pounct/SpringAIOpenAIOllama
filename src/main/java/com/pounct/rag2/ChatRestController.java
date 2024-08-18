package com.pounct.rag2;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatRestController {
    private ChatClient chatClient;

    public ChatRestController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    @GetMapping(value = "/chat",produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(String question){
        String response = chatClient.prompt()
                .system("")
                .user(question)
                .call().content();
        return response;
    }

    @GetMapping(value = "/chatStream",produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chaStream(String question){
        Flux<String> response = chatClient.prompt()
                .system("")
                .user(question)
                .stream().content();
        return response;
    }
}
