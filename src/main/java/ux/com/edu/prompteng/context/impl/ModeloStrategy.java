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

    private final OllamaClient cliente = new OllamaClient();
    private final String nombreModeloOllama;
    private final String nombreVisual;

    public ModeloStrategy(String nombreModeloOllama, String nombreVisual) {
        this.nombreModeloOllama = nombreModeloOllama;
        this.nombreVisual = nombreVisual;
    }

    @Override
    public String generarRespuesta(PromptConfig config) {
        String promptSeleccionado;

        switch (config.getTipoPrompt()) {
            case "zero-shot":
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones())
                        .conEntrada(config.getEntrada())
                        .build();
                break;
            case "few-shot":
                PromptBuilder builder = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones());
                if (config.getEjemplos() != null) {
                    for (String[] ejemplo : config.getEjemplos()) {
                        builder.agregarEjemplo(ejemplo[0], ejemplo[1]);
                    }
                }
                builder.conEntrada(config.getEntrada());
                promptSeleccionado = builder.build();
                break;
            case "chain-of-thought":
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones() + "\\nAnaliza el problema paso a paso antes de dar la respuesta final.")
                        .conEntrada(config.getEntrada())
                        .build();
                break;
            case "meta-prompting":
                promptSeleccionado = new PromptBuilder()
                        .conRol("Experto en Ingenieria de Prompts")
                        .conInstrucciones("Tu tarea es disenar un prompt profesional y optimizado basado en los requisitos del usuario.")
                        .conEntrada(config.getInstrucciones())
                        .build();
                break;
            case "role-based":
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones())
                        .conEntrada(config.getEntrada())
                        .build();
                break;
            case "delimiters":
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones())
                        .conEntrada(config.getEntrada())
                        .buildConDelimitadores(config.getContratoSalida());
                break;
            default:
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones())
                        .conEntrada(config.getEntrada())
                        .build();
                break;
        }

        String jsonRespuesta = cliente.enviarPeticion(nombreModeloOllama, promptSeleccionado);
        return "Respuesta de " + nombreVisual + ": " + jsonRespuesta;
    }

    @Override
    public String getNombreModelo() {
        return nombreVisual;
    }
}

