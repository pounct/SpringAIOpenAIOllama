package com.pounct.rag2;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class ChatRestController {
    private ChatClient chatClient;
    @Value("classpath:/prompts/system-message.st")
    private String systemeMessageResource;
    /*@Value("classpath:/images/image.jpg")
    private Resource image;*/

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

    @GetMapping(value = "/imagenDesc",produces = MediaType.TEXT_PLAIN_VALUE)
    public  String imagenDesc(String revieu) throws IOException {

        //Resource image = new ClassPathResource("classpath:/images/image.jpg");
        byte[] data = new ClassPathResource("classpath:/images/image.jpg").getContentAsByteArray();

        /*String systemMessage = """
                Ton role est de faire la reconaissance optique du texte
                dans l'image fournie
                
                """;*/
        String userMessageText = """
                Ton role est de faire la reconaissance optique du texte
                dans l'image fournie.
                le resultat en json.
                on ajoute les orientation...              
                """;
        UserMessage userMessage = new UserMessage(userMessageText, List.of(
                new Media(new MimeType("IMAGE_JPEG"), data)
        ));
        Prompt prompt = new Prompt(userMessage);

        return chatClient.prompt(prompt).call().content();// on peut utiliser entity
    }
}
