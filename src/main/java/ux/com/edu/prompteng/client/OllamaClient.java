package ux.com.edu.prompteng.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class OllamaClient {

    private static final Logger log = LoggerFactory.getLogger(OllamaClient.class);

    private static final String URL_API = "http://localhost:11434/api/generate";

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String enviarPeticion(String modelo, String promptEstructurado) {


        String jsonBody = """
                {"model": "%s", "prompt": "%s", "stream": false}
                """.formatted(
                modelo,
                promptEstructurado.replace("\"", "\\\"").replace("\n", "\\n")
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("La solicitud fue interrumpida: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error al enviar petición a Ollama: {}", e.getMessage());
            return e.getMessage();
        }
        return null;
    }
}