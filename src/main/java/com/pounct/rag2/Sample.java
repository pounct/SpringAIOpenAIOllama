package com.pounct.rag2;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

import java.util.List;

public class Sample {

    public static void main(String[] args) {
        // Récupérer la clé API de manière sécurisée
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("La clé API OpenAI est manquante.");
            return;
        }

        try {
            // Interagir avec un LLM
            OpenAiApi openAiApi = new OpenAiApi(apiKey);
            OpenAiChatModel openAiChatModel = new OpenAiChatModel(openAiApi, OpenAiChatOptions
                    .builder()
                    .withModel("gpt-3.5-turbo")
                    .withTemperature(0f)
                    .withMaxTokens(300)
                    .build());

            String sysMessText = """
                    Vous êtes un assistant spécialisé dans l'analyse des textes :
                    votre tâche est d'extraire les sens possibles d'un texte.
                    Le résultat attendu sera au format JSON avec les champs nécessaires pour bien décrire les sens extraits.
                    """;
            SystemMessage systemMessage = new SystemMessage(sysMessText);

            String userInputMessage = "Ici c'est un message utilisateur final ...";

            UserMessage userMessage = new UserMessage(userInputMessage);

            String userInputMessage1 = "Ici c'est un message utilisateur.";

            UserMessage userMessage1 = new UserMessage(userInputMessage1);

            String response1 = """
                    {
                    "message".......
                    }
                    """;

            AssistantMessage assistantMessage1 = new AssistantMessage(response1);

//            Prompt zeroShotprompt = new Prompt(List.of(systemMessage, userMessage));
            Prompt fewShotprompt = new Prompt(List.of(systemMessage,userMessage1,assistantMessage1, userMessage));

            ChatResponse chatResponse = openAiChatModel.call(fewShotprompt);

            System.out.println(chatResponse.getResult().getOutput());

        } catch (Exception e) {
            System.err.println("Une erreur s'est produite lors de l'appel à l'API OpenAI : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
