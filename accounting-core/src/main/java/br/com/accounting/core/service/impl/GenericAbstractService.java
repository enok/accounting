package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.Duplicates;
import br.com.accounting.core.filter.Filtro;
import br.com.accounting.core.ordering.Ordem;
import br.com.accounting.core.repository.Repository;
import br.com.accounting.core.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static br.com.accounting.core.filter.Duplicates.KEEP;

public abstract class GenericAbstractService<T> implements GenericService<T> {
    private static final Logger LOG = LoggerFactory.getLogger(GenericAbstractService.class);

    protected Repository repository;

    public GenericAbstractService(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Long salvar(T entity) throws ServiceException {
        LOG.info("[ salvar ]");
        LOG.debug("entity: " + entity);

        try {
            return repository.salvar(entity);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o registro: " + entity;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public void atualizar(List<T> oldEntitiesList, List<T> newEntitiesList) throws ServiceException {
        LOG.info("[ atualizar ]");
        LOG.debug("oldEntitiesList: " + oldEntitiesList);
        LOG.debug("newEntitiesList: " + newEntitiesList);

        try {
            repository.atualizar(oldEntitiesList, newEntitiesList);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel atualizar os registros: " + oldEntitiesList;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<T> buscarRegistros() throws ServiceException {
        LOG.info("[ buscarRegistros ]");

        try {
            return repository.buscarRegistros();
        } catch (Exception e) {
            String mensagem = "Nao foi possivel buscar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<T> filtrar(Filtro filtro, Duplicates duplicates, List<T> entitys) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("filtro: " + filtro);
        LOG.debug("duplicates: " + duplicates);
        LOG.debug("entitys: " + entitys);

        try {
            return filtro.filtrar(entitys, duplicates);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<T> filtrar(Filtro filtro, List<T> entitys) throws ServiceException {
        return filtrar(filtro, KEEP, entitys);
    }

    @Override
    public List<T> filtrar(Filtro filtro, Duplicates duplicates) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("filtro: " + filtro);
        LOG.debug("duplicates: " + duplicates);

        try {
            List<T> entitys = repository.buscarRegistros();
            return filtrar(filtro, duplicates, entitys);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<T> filtrar(Filtro filtro) throws ServiceException {
        return filtrar(filtro, KEEP);
    }

    @Override
    public T filtrarSingle(Filtro filtro, List<T> entitys) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("filtro: " + filtro);
        LOG.debug("entitys: " + entitys);

        try {
            return (T) filtro.filtrarSingle(entitys);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar o registro";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<T> ordenar(Ordem ordem, Order order, List<T> entitys) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("ordem: " + ordem);
        LOG.debug("order: " + order);
        LOG.debug("entitys: " + entitys);

        try {
            return ordem.ordenar(entitys, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<T> ordenar(Ordem ordem, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("ordem: " + ordem);
        LOG.debug("order: " + order);

        try {
            List<T> entitys = repository.buscarRegistros();
            return ordenar(ordem, order, entitys);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
