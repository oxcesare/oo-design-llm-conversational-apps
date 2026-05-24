package ux.com.edu.prompteng.context.impl;

import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.client.OllamaClient;
import ux.com.edu.prompteng.strategies.InteligenciaArtificialStrategy;
import ux.com.edu.prompteng.strategies.prompt.PromptStrategyRegistry;

/**
 * Estrategia parametrizable para reutilizar la misma logica de construccion
 * de prompts con distintos modelos de Ollama.
 *
 * <p>La construcción del prompt se delega a {@link PromptStrategyRegistry},
 * eliminando el switch interno. Para añadir un nuevo tipo de prompt
 * basta con registrarlo en el registry sin modificar esta clase.</p>
 */
public class ModeloStrategy implements InteligenciaArtificialStrategy {

    private final OllamaClient cliente;
    private final String nombreModeloOllama;
    private final String nombreVisual;
    private final PromptStrategyRegistry registry;

    public ModeloStrategy(String nombreModeloOllama, String nombreVisual, OllamaClient client) {
        this.nombreModeloOllama = nombreModeloOllama;
        this.cliente = client;
        this.nombreVisual = nombreVisual;
        this.registry = new PromptStrategyRegistry();
    }

    /**
     * @param config la configuración del prompt que contiene los parámetros
     *               necesarios para construir y enviar la solicitud al modelo
     * @return respuesta del modelo LLM
     */
    @Override
    public String generarRespuesta(PromptConfig config) {
        String promptSeleccionado = registry
                .obtener(config.getTipoPrompt())
                .buildPrompt(config);

        String jsonRespuesta = cliente.enviarPeticion(nombreModeloOllama, promptSeleccionado);
        return "Respuesta de " + nombreVisual + ": " + jsonRespuesta;
    }

    @Override
    public String getNombreModelo() {
        return nombreVisual;
    }
}

