package br.com.accounting.grupo.service.impl;

import br.com.accounting.commons.entity.Grupo;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.impl.GenericAbstractService;
import br.com.accounting.grupo.repository.GrupoRepository;
import br.com.accounting.grupo.service.GrupoService;
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
