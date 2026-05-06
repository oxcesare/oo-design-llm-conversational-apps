package ux.com.edu.prompteng.implementation.builders;

import java.util.ArrayList;
import java.util.List;

public class PromptConfig {

    private String rol;
    private String instrucciones;
    private String entrada;
    private String tipoPrompt;
    private List<String[]> ejemplos;


    public PromptConfig(String rol, String instrucciones, String entrada,String tipoPrompt, List<String> ejemplos) {
        this.rol = rol;
        this.instrucciones = instrucciones;
        this.entrada = entrada;
        this.tipoPrompt = tipoPrompt;
        this.ejemplos= new ArrayList<String[]>();
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
}
