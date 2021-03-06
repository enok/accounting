package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Local;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.repository.LocalRepository;
import br.com.accounting.core.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class LocalServiceImpl extends GenericAbstractService<Local> implements LocalService {
    private LocalRepository repository;

    @Autowired
    public LocalServiceImpl(LocalRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Local buscarPorNome(final String nome) throws StoreException, ParseException {
        try {
            List<Local> entities = repository.buscarRegistros();
            return repository.filtrarPorNome(entities, nome);
        }
        catch (StoreException e) {
            throw e;
        }
    }

    @Override
    public void ordenarTodas(final List<Local> entities) {
        repository.ordenarPorNome(entities);
    }
}
