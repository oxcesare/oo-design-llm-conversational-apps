package ux.com.edu.prompteng.strategies.prompt.impl;

import ux.com.edu.prompteng.builders.PromptBuilder;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.strategies.prompt.PromptGenerationStrategy;

/**
 * Estrategia CHAIN_OF_THOUGHT: obliga al modelo a razonar paso a paso
 * antes de dar la respuesta final.
 */
public class ChainOfThoughtPromptStrategy implements PromptGenerationStrategy {

    private static final String SUFIJO_COT = "\nAnaliza el problema paso a paso antes de dar la respuesta final.";

    @Override
    public String buildPrompt(PromptConfig config) {
        return new PromptBuilder()
                .conRol(config.getRol())
                .conInstrucciones(config.getInstrucciones() + SUFIJO_COT)
                .conEntrada(config.getEntrada())
                .build();
    }
}

