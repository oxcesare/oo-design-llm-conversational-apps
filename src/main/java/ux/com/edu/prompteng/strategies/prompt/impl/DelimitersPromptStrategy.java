package ux.com.edu.prompteng.strategies.prompt.impl;

import ux.com.edu.prompteng.builders.PromptBuilder;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.strategies.prompt.PromptGenerationStrategy;

/**
 * Estrategia DELIMITERS: estructura el prompt con etiquetas XML
 * (role, task_description, output_contract, user_query) para mayor
 * claridad semántica hacia el modelo.
 */
public class DelimitersPromptStrategy implements PromptGenerationStrategy {

    @Override
    public String buildPrompt(PromptConfig config) {
        return new PromptBuilder()
                .conRol(config.getRol())
                .conInstrucciones(config.getInstrucciones())
                .conEntrada(config.getEntrada())
                .buildConDelimitadores(config.getContratoSalida());
    }
}

