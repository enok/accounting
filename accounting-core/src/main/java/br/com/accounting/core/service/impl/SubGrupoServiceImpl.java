package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.SubGrupoRepository;
import br.com.accounting.core.service.SubGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubGrupoServiceImpl implements SubGrupoService {
    @Autowired
    private SubGrupoRepository repository;

    @Override
    public Long salvar(final SubGrupo entity) throws ServiceException {
        try {
            setarProximoCodigo(repository, entity);
            repository.salvar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar o subGrupo.";
            throw new ServiceException(message, e);
        }
        return entity.codigo();
    }

    @Override
    public void atualizar(final SubGrupo entity) throws ServiceException {
        try {
            repository.atualizar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o subGrupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void deletar(final SubGrupo entity) throws ServiceException {
        try {
            repository.deletar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar o subGrupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public SubGrupo buscarPorCodigo(final Long codigo) throws ServiceException {
        return buscarPorCodigo(repository, codigo);
    }

    @Override
    public SubGrupo buscarPorNome(final String nome) throws ServiceException {
        try {
            List<SubGrupo> entities = repository.buscarRegistros();
            return repository.filtrarPorNome(entities, nome);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o subGrupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<SubGrupo> buscarTodos() throws ServiceException {
        try {
            List<SubGrupo> entities = repository.buscarRegistros();
            repository.ordenarPorNome(entities);
            return entities;
        }
        catch (Exception e) {
            String message = "Não foi possível buscar os subGrupos.";
            throw new ServiceException(message, e);
        }
    }
}
