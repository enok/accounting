package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.ordering.CampoOrdem;
import br.com.accounting.core.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class GenericService<T> {
    private static final Logger LOG = LoggerFactory.getLogger(GenericService.class);

    protected Repository repository;

    public GenericService(Repository repository) {
        this.repository = repository;
    }

    public void salvar(T entity) throws ServiceException {
        LOG.info("[ salvar ]");
        LOG.debug("entity: " + entity);

        try {
            repository.salvar(entity);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o registro: " + entity;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

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

    public List<T> filtrar(CampoFiltro campoFiltro, List<T> entitys) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("entitys: " + entitys);

        try {
            return campoFiltro.filtrar(entitys);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    public List<T> filtrar(CampoFiltro campoFiltro) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);

        try {
            List<T> entitys = repository.buscarRegistros();
            return filtrar(campoFiltro, entitys);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    public List<T> ordenar(CampoOrdem campoOrdem, List<T> entitys, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoOrdem);
        LOG.debug("entitys: " + entitys);
        LOG.debug("order: " + order);

        try {
            return campoOrdem.ordenar(entitys, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    public List<T> ordenar(CampoOrdem campoOrdem, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoOrdem);
        LOG.debug("order: " + order);

        try {
            List<T> entitys = repository.buscarRegistros();
            return ordenar(campoOrdem, entitys, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
