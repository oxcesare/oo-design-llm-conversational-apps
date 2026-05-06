package ux.com.edu.prompteng.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class OllamaClient {

    private static final String URL_API = "http://localhost:11434/api/generate";

    public String enviarPeticion(String modelo, String promptEstructurado) {

        // Construcción del JSON manual para evitar dependencias externas iniciales
        String jsonBody = String.format(
                "{\"model\": \"%s\", \"prompt\": \"%s\", \"stream\": false}",
                modelo, promptEstructurado.replace("\"", "\\\"").replace("\n", "\\n")
        );

        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body(); // Aquí recibes el JSON completo de Ollama

        } catch (Exception e) {
            return "Error de conexión: " + e.getMessage();
        }
    }
}
