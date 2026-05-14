package ux.com.edu.prompteng.context.impl;

import ux.com.edu.prompteng.builders.PromptBuilder;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.client.OllamaClient;
import ux.com.edu.prompteng.strategies.InteligenciaArtificialStrategy;

/**
 * Estrategia parametrizable para reutilizar la misma logica de construccion
 * de prompts con distintos modelos de Ollama.
 */
public class ModeloStrategy implements InteligenciaArtificialStrategy {

    private final OllamaClient cliente;

    private final String nombreModeloOllama;

    private final String nombreVisual;

    public ModeloStrategy(String nombreModeloOllama, String nombreVisual, OllamaClient client) {
        this.nombreModeloOllama = nombreModeloOllama;
        this.cliente = client;
        this.nombreVisual = nombreVisual;
    }

    /**
     *
     * @param config la configuración del prompt que contiene los parámetros
     *               necesarios para construir y enviar la solicitud al modelo
     * @return
     */
    @Override
    public String generarRespuesta(PromptConfig config) {
        String promptSeleccionado = switch (config.getTipoPrompt()) {

            case FEW_SHOT -> {
                PromptBuilder builder = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones());
                if (config.getEjemplos() != null) {
                    for (String[] ejemplo : config.getEjemplos()) {
                        builder.agregarEjemplo(ejemplo[0], ejemplo[1]);
                    }
                }
                yield builder.conEntrada(config.getEntrada()).build();
            }

            case CHAIN_OF_THOUGHT -> new PromptBuilder()
                    .conRol(config.getRol())
                    .conInstrucciones(config.getInstrucciones() + "\nAnaliza el problema paso a paso antes de dar la respuesta final.")
                    .conEntrada(config.getEntrada())
                    .build();

            case META_PROMPTING -> new PromptBuilder()
                    .conRol("Experto en Ingeniería de Prompts")
                    .conInstrucciones("Tu tarea es diseñar un prompt profesional y optimizado basado en los requisitos del usuario.")
                    .conEntrada(config.getInstrucciones())
                    .build();

            case ROLE_BASED -> new PromptBuilder()
                    .conRol(config.getRol())
                    .conInstrucciones(config.getInstrucciones())
                    .conEntrada(config.getEntrada())
                    .build();

            case DELIMITERS -> new PromptBuilder()
                    .conRol(config.getRol())
                    .conInstrucciones(config.getInstrucciones())
                    .conEntrada(config.getEntrada())
                    .buildConDelimitadores(config.getContratoSalida());

            default -> new PromptBuilder()
                    .conRol(config.getRol())
                    .conInstrucciones(config.getInstrucciones())
                    .conEntrada(config.getEntrada())
                    .build();
        };

        String jsonRespuesta = cliente.enviarPeticion(nombreModeloOllama, promptSeleccionado);
        return "Respuesta de " + nombreVisual + ": " + jsonRespuesta;
    }

    @Override
    public String getNombreModelo() {
        return nombreVisual;
    }
}

