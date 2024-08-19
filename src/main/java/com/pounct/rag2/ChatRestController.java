package com.pounct.rag2;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatRestController {
    private ChatClient chatClient;
    @Value("classpath:/prompts/system-message.st")
    private String systemeMessageResource;

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

    // with SM : system message

    // with specific result
    @GetMapping(value = "/resultatSM") // resultat en format json
    public  ResultatSM resultatSM(String revieu){
        return chatClient.prompt().system(systemeMessageResource).user(revieu).call().entity(ResultatSM.class);
    }
    @GetMapping(value = "/resultatSMSimple",produces = MediaType.TEXT_PLAIN_VALUE)
    public  String resultatSMSimple(String revieu){
        return chatClient.prompt().system(systemeMessageResource).user(revieu).call().content();
    }
}
