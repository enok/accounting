package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.repository.SubGrupoRepository;
import br.com.accounting.core.service.SubGrupoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubGrupoServiceImpl implements SubGrupoService {
    private static final Logger LOG = LoggerFactory.getLogger(SubGrupoServiceImpl.class);

    @Autowired
    private SubGrupoRepository subGrupoRepository;

    @Override
    public void salvar(SubGrupo subGrupo) throws ServiceException {
        LOG.info("[ salvar ]");
        LOG.debug("subGrupo: " + subGrupo);

        try {
            subGrupoRepository.salvar(subGrupo);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o subGrupo: " + subGrupo;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> buscarRegistros() throws ServiceException {
        LOG.info("[ buscarRegistros ]");

        try {
            return subGrupoRepository.buscarRegistros();
        } catch (Exception e) {
            String mensagem = "Nao foi possivel buscar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> filtrar(CampoFiltro campoFiltro, List<SubGrupo> subGrupos) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("subGrupos: " + subGrupos);

        try {
            return campoFiltro.filtrar(subGrupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> filtrar(CampoFiltro campoFiltro) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);

        try {
            List<SubGrupo> subGrupos = subGrupoRepository.buscarRegistros();
            return filtrar(campoFiltro, subGrupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenar(CampoFiltro campoFiltro, List<SubGrupo> subGrupos, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("subGrupos: " + subGrupos);
        LOG.debug("order: " + order);

        try {
            return campoFiltro.ordenar(subGrupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenar(CampoFiltro campoFiltro, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("order: " + order);

        try {
            List<SubGrupo> subGrupos = subGrupoRepository.buscarRegistros();
            return ordenar(campoFiltro, subGrupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
