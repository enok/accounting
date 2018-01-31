package br.com.accounting.core.entity;

public class GrupoEntityFactory {
    public static GrupoEntity create() {
        GrupoEntity grupoEntity = new GrupoEntity("RENDA");
        return grupoEntity;
    }
}
