package br.com.accounting.core.service.impl;

import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.service.impl.GenericAbstractService;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.repository.SubGrupoRepository;
import br.com.accounting.core.service.SubGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class SubGrupoServiceImpl extends GenericAbstractService<SubGrupo> implements SubGrupoService {
    private SubGrupoRepository repository;

    @Autowired
    public SubGrupoServiceImpl(SubGrupoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SubGrupo buscarPorNome(final String nome) throws StoreException, ParseException {
        try {
            List<SubGrupo> entities = repository.buscarRegistros();
            return repository.filtrarPorNome(entities, nome);
        }
        catch (StoreException e) {
            throw e;
        }
    }

    @Override
    public void ordenarTodas(List<SubGrupo> entities) {
        repository.ordenarPorNome(entities);
    }
}
