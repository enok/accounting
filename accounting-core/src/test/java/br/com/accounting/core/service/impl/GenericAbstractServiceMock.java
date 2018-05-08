package br.com.accounting.core.service.impl;

import br.com.accounting.core.repository.GenericRepository;

import java.util.List;

public class GenericAbstractServiceMock extends GenericAbstractService {
    public GenericAbstractServiceMock(GenericRepository repository) {
        super(repository);
    }

    @Override
    public void ordenarTodas(List entities) {
    }
}
