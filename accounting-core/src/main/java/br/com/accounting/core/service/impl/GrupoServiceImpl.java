package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.ordering.CampoOrdem;
import br.com.accounting.core.repository.GrupoRepository;
import br.com.accounting.core.service.GrupoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoServiceImpl extends GenericService<Grupo> implements GrupoService {
    private static final Logger LOG = LoggerFactory.getLogger(GrupoServiceImpl.class);

    @Autowired
    public GrupoServiceImpl(GrupoRepository repository) {
        super(repository);
    }

    @Override
    public List<SubGrupo> filtrarSubGrupos(CampoFiltro campoFiltro, List<Grupo> registros) throws ServiceException {
        LOG.info("[ filtrarSubGrupos ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("registros: " + registros);

        try {
            return campoFiltro.filtrar(registros);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> filtrarSubGrupos(CampoFiltro campoFiltro) throws ServiceException {
        LOG.info("[ filtrarSubGrupos ]");
        LOG.debug("campoFiltro: " + campoFiltro);

        try {
            List<Grupo> grupos = repository.buscarRegistros();
            return campoFiltro.filtrar(grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarSubGrupos(CampoOrdem campoOrdem, List<Grupo> grupos, Order order) throws ServiceException {
        LOG.info("[ ordenarSubGrupos ]");
        LOG.debug("campoOrdem: " + campoOrdem);
        LOG.debug("grupos: " + grupos);
        LOG.debug("order: " + order);

        try {
            return campoOrdem.ordenar(grupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarSubGrupos(CampoOrdem campoOrdem, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoOrdem: " + campoOrdem);
        LOG.debug("order: " + order);

        try {
            List<Grupo> grupos = repository.buscarRegistros();
            return ordenarSubGrupos(campoOrdem, grupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
