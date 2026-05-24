package ux.com.edu.prompteng.strategies.prompt.impl;

import ux.com.edu.prompteng.builders.PromptBuilder;
import ux.com.edu.prompteng.builders.PromptConfig;
import ux.com.edu.prompteng.strategies.prompt.PromptGenerationStrategy;

/**
 * Estrategia META_PROMPTING: usa el modelo como generador de prompts
 * optimizados para otro modelo o tarea.
 */
public class MetaPromptingStrategy implements PromptGenerationStrategy {

    private static final String ROL_META      = "Experto en Ingeniería de Prompts";
    private static final String INSTRUCCION_META =
            "Tu tarea es diseñar un prompt profesional y optimizado basado en los requisitos del usuario.";

    @Override
    public String buildPrompt(PromptConfig config) {
        return new PromptBuilder()
                .conRol(ROL_META)
                .conInstrucciones(INSTRUCCION_META)
                .conEntrada(config.getInstrucciones())  // la entrada es la instrucción del usuario
                .build();
    }
}

