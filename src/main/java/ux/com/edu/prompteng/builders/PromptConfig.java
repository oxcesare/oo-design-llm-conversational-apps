package ux.com.edu.prompteng.builders;

import java.util.ArrayList;
import java.util.List;

public class PromptConfig {

    private String rol;
    private String instrucciones;
    private String entrada;
    private String tipoPrompt;
    private List<String[]> ejemplos;


    public PromptConfig(String rol, String instrucciones, String entrada, String tipoPrompt, List<String[]> ejemplos) {
        this.rol = rol;
        this.instrucciones = instrucciones;
        this.entrada = entrada;
        this.tipoPrompt = tipoPrompt;
        this.ejemplos = ejemplos == null ? new ArrayList<>() : new ArrayList<>(ejemplos);
    }

    public List<String[]> getEjemplos() {
        return ejemplos;
    }

    public void setEjemplos(List<String[]> ejemplos) {
        this.ejemplos = ejemplos;
    }

    // Getters
    public String getRol() { return rol; }
    public String getInstrucciones() { return instrucciones; }
    public String getEntrada() { return entrada; }

    public String getTipoPrompt() { return tipoPrompt; }

    public void setTipoPrompt(String tipoPrompt) {
        this.tipoPrompt = tipoPrompt;
    }


    /***
     * Metodo toString()
     */
    @Override
    public String toString() {
        return "PromptConfig{" +
                "rol='" + rol + '\'' +
                ", instrucciones='" + instrucciones + '\'' +
                ", entrada='" + entrada + '\'' +
                ", tipoPrompt='" + tipoPrompt + '\'' +
                ", ejemplos=" + ejemplos +
                '}';
    }
}
