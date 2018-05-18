package br.com.accounting.commons.service.impl;

import br.com.accounting.commons.repository.GenericRepository;

import java.util.List;

public class GenericAbstractServiceMock extends GenericAbstractService {
    public GenericAbstractServiceMock(GenericRepository repository) {
        super(repository);
    }

    @Override
    public void ordenarTodas(List entities) {
    }
}
