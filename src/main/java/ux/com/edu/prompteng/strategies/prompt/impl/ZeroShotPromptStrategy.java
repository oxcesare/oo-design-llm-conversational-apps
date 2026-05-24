package ux.com.edu.prompteng.strategies.prompt.impl;

import ux.com.edu.prompteng.builders.PromptBuilder;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.strategies.prompt.PromptGenerationStrategy;

/**
 * Estrategia ZERO_SHOT: sin ejemplos, instrucción directa al modelo.
 */
public class ZeroShotPromptStrategy implements PromptGenerationStrategy {

    @Override
    public String buildPrompt(PromptConfig config) {
        return new PromptBuilder()
                .conRol(config.getRol())
                .conInstrucciones(config.getInstrucciones())
                .conEntrada(config.getEntrada())
                .build();
    }
}

