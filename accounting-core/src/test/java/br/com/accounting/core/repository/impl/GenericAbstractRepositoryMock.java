package br.com.accounting.core.repository.impl;

import java.text.ParseException;
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
    public Object criarEntity(String linha) throws ParseException {
        return null;
    }

    @Override
    public List criarRegistros(List linhas) throws ParseException {
        return null;
    }
}
