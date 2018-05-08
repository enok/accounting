package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.repository.GrupoRepository;
import br.com.accounting.core.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class GrupoServiceImpl extends GenericAbstractService<Grupo> implements GrupoService {
    private GrupoRepository repository;

    @Autowired
    public GrupoServiceImpl(GrupoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Grupo buscarPorNome(final String nome) throws StoreException, ParseException {
        try {
            List<Grupo> entities = repository.buscarRegistros();
            return repository.filtrarPorNome(entities, nome);
        }
        catch (StoreException e) {
            throw e;
        }
    }

    @Override
    public void ordenarTodas(List<Grupo> entities) {
        repository.ordenarPorNome(entities);
    }
}
