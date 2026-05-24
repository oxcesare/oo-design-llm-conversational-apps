package ux.com.edu.prompteng.strategies.prompt;

import ux.com.edu.prompteng.builders.TipoPrompt;
import ux.com.edu.prompteng.strategies.prompt.impl.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * Registro central que asocia cada {@link TipoPrompt} con su
 * {@link PromptGenerationStrategy} correspondiente.
 *
 * <p>Para agregar un nuevo tipo de prompt:</p>
 * <ol>
 *   <li>Añade el valor en {@link TipoPrompt}.</li>
 *   <li>Crea una clase en {@code strategies/prompt/impl/} que implemente {@link PromptGenerationStrategy}.</li>
 *   <li>Registra la nueva clase en el constructor de esta clase.</li>
 * </ol>
 *
 * @author cesarruiz
 * @version 1.0
 */
public class PromptStrategyRegistry {

    private final Map<TipoPrompt, PromptGenerationStrategy> registro = new EnumMap<>(TipoPrompt.class);

    public PromptStrategyRegistry() {
        registro.put(TipoPrompt.ZERO_SHOT,       new ZeroShotPromptStrategy());
        registro.put(TipoPrompt.FEW_SHOT,        new FewShotPromptStrategy());
        registro.put(TipoPrompt.CHAIN_OF_THOUGHT,new ChainOfThoughtPromptStrategy());
        registro.put(TipoPrompt.META_PROMPTING,  new MetaPromptingStrategy());
        registro.put(TipoPrompt.ROLE_BASED,      new RoleBasedPromptStrategy());
        registro.put(TipoPrompt.DELIMITERS,      new DelimitersPromptStrategy());
    }

    /**
     * Obtiene la estrategia registrada para el tipo dado.
     * Si el tipo no tiene estrategia registrada, devuelve ZERO_SHOT como fallback.
     *
     * @param tipo el tipo de prompt a resolver
     * @return la estrategia correspondiente, nunca null
     */
    public PromptGenerationStrategy obtener(TipoPrompt tipo) {
        return registro.getOrDefault(tipo, registro.get(TipoPrompt.ZERO_SHOT));
    }
}

