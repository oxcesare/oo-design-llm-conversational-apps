package ux.com.edu.prompteng.strategies.prompt.impl;

import ux.com.edu.prompteng.builders.PromptBuilder;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.strategies.prompt.PromptGenerationStrategy;

/**
 * Estrategia FEW_SHOT: incluye ejemplos entrada/salida antes de la consulta real.
 */
public class FewShotPromptStrategy implements PromptGenerationStrategy {

    @Override
    public String buildPrompt(PromptConfig config) {
        PromptBuilder builder = new PromptBuilder()
                .conRol(config.getRol())
                .conInstrucciones(config.getInstrucciones());

        if (config.getEjemplos() != null) {
            for (String[] ejemplo : config.getEjemplos()) {
                builder.agregarEjemplo(ejemplo[0], ejemplo[1]);
            }
        }

        return builder.conEntrada(config.getEntrada()).build();
    }
}

