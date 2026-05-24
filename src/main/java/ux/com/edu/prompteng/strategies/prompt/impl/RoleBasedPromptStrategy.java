package ux.com.edu.prompteng.strategies.prompt.impl;

import ux.com.edu.prompteng.builders.PromptBuilder;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.strategies.prompt.PromptGenerationStrategy;

/**
 * Estrategia ROLE_BASED: asigna un rol especializado al modelo
 * para condicionar el estilo y el enfoque de la respuesta.
 */
public class RoleBasedPromptStrategy implements PromptGenerationStrategy {

    @Override
    public String buildPrompt(PromptConfig config) {
        return new PromptBuilder()
                .conRol(config.getRol())
                .conInstrucciones(config.getInstrucciones())
                .conEntrada(config.getEntrada())
                .build();
    }
}

