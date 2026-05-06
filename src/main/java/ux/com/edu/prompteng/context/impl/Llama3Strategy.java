package ux.com.edu.prompteng.context.impl;

import ux.com.edu.prompteng.client.OllamaClient;
import ux.com.edu.prompteng.implementation.builders.PromptBuilder;
import ux.com.edu.prompteng.implementation.builders.PromptConfig;
import ux.com.edu.prompteng.implementation.strategies.InteligenciaArtificialStrategy;

public class Llama3Strategy implements InteligenciaArtificialStrategy {

    private final OllamaClient cliente = new OllamaClient();

    @Override
    public String generarRespuesta(PromptConfig config) {

        //Determinar el Prompt seleccionado
        String promptSeleccionado = "";

        //Swtich para determinar el tipo de prompt de acuerdo al valor del objeto config
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
                /*
                * Para este tipo de prompt, podríamos agregar
                  instrucciones específicas para que el modelo piense paso a paso
                */
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones() + "\\nAnaliza el problema paso a paso antes de dar la respuesta final.")
                        .conEntrada(config.getEntrada())
                        .build();
                break;
            case "meta-prompting":
                promptSeleccionado = new PromptBuilder()
                        .conRol("Experto en Ingeniería de Prompts")
                        .conInstrucciones("Tu tarea es diseñar un prompt profesional y optimizado basado en los requisitos del usuario.")
                        .conEntrada(config.getInstrucciones()) // Aquí usamos las instrucciones como base para crear el nuevo prompt
                        .build();
                break;

            case "role-based":
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones())
                        .conEntrada(config.getEntrada())
                        .build();
                break;
            default:
                // Si no se especifica un tipo válido, usamos zero-shot por defecto
                promptSeleccionado = new PromptBuilder()
                        .conRol(config.getRol())
                        .conInstrucciones(config.getInstrucciones())
                        .conEntrada(config.getEntrada())
                        .build();
        }


        // 2. Enviamos la petición real al modelo Llama3 instalado
        String jsonRespuesta = cliente.enviarPeticion("llama3", promptSeleccionado);

        // TODO implementar la logica necesaria para encapsular el response mediante
        // Jackson (Json)
        return "Respuesta de Ollama: " + jsonRespuesta;
    }

    @Override
    public String getNombreModelo() {
        return "Llama3-Local-M4";
    }
}