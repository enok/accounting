package br.com.accounting.commons.repository.impl;

import java.util.List;

public class GenericAbstractRepositoryMock extends GenericAbstractRepository {
    @Override
    public String getArquivo() {
        return null;
    }

    @Override
    public String getArquivoContagem() {
        return null;
    }

    @Override
    public String criarLinha(Object entity) {
        return null;
    }

    @Override
    public Object criarEntity(String linha) {
        return null;
    }

    @Override
    public List criarRegistros(List linhas) {
        return null;
    }
}
