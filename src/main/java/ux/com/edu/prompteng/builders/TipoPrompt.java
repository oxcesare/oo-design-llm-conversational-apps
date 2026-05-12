package ux.com.edu.prompteng.builders;

/**
 * Enum que representa los tipos de prompt soportados por el sistema.
 * Reemplaza el uso de Strings mágicos en toda la aplicación.
 *
 * @author cesarruiz
 * @version 1.0
 * @since 17
 */
public enum TipoPrompt {

    ZERO_SHOT("zero-shot"),
    FEW_SHOT("few-shot"),
    CHAIN_OF_THOUGHT("chain-of-thought"),
    META_PROMPTING("meta-prompting"),
    ROLE_BASED("role-based"),
    DELIMITERS("delimiters");

    private final String valor;

    TipoPrompt(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    /**
     * Convierte un String al enum correspondiente.
     * Si no coincide ninguno, retorna ZERO_SHOT como fallback.
     */
    public static TipoPrompt from(String valor) {
        if (valor == null || valor.isBlank()) return ZERO_SHOT;
        for (TipoPrompt t : values()) {
            if (t.valor.equalsIgnoreCase(valor)) return t;
        }
        return ZERO_SHOT;
    }
}

