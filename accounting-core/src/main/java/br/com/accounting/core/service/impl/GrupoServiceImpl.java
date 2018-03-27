package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.GrupoRepository;
import br.com.accounting.core.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoServiceImpl implements GrupoService {
    @Autowired
    private GrupoRepository repository;

    @Override
    public Long salvar(final Grupo entity) throws ServiceException {
        try {
            setarProximoCodigo(repository, entity);
            repository.salvar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar o grupo.";
            throw new ServiceException(message, e);
        }
        return entity.codigo();
    }

    @Override
    public void atualizar(final Grupo entity) throws ServiceException {
        try {
            repository.atualizar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o grupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void deletar(final Grupo entity) throws ServiceException {
        try {
            repository.deletar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar o grupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Grupo buscarPorCodigo(final Long codigo) throws ServiceException {
        return buscarPorCodigo(repository, codigo);
    }

    @Override
    public Grupo buscarPorNome(final String nome) throws ServiceException {
        try {
            List<Grupo> entities = repository.buscarRegistros();
            return repository.filtrarPorNome(entities, nome);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o grupo por nome.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<Grupo> buscarTodos() throws ServiceException {
        try {
            List<Grupo> entities = repository.buscarRegistros();
            repository.ordenarPorNome(entities);
            return entities;
        }
        catch (Exception e) {
            String message = "Não foi possível buscar os grupos.";
            throw new ServiceException(message, e);
        }
    }
}
