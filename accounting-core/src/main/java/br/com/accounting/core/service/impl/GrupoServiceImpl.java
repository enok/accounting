package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.repository.GrupoRepository;
import br.com.accounting.core.service.GrupoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoServiceImpl implements GrupoService {
    private static final Logger LOG = LoggerFactory.getLogger(GrupoServiceImpl.class);

    @Autowired
    private GrupoRepository grupoRepository;

    @Override
    public void salvar(Grupo grupo) throws ServiceException {
        LOG.info("[ salvar ]");
        LOG.debug("grupo: " + grupo);

        try {
            grupoRepository.salvar(grupo);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o grupo: " + grupo;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> buscarRegistros() throws ServiceException {
        LOG.info("[ buscarRegistros ]");

        try {
            return grupoRepository.buscarRegistros();
        } catch (Exception e) {
            String mensagem = "Nao foi possivel buscar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> filtrar(CampoFiltro campoFiltro, List<Grupo> grupos) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("grupos: " + grupos);

        try {
            return campoFiltro.filtrar(grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
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
    public List<Grupo> filtrar(CampoFiltro campoFiltro) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);

        try {
            List<Grupo> grupos = grupoRepository.buscarRegistros();
            return filtrar(campoFiltro, grupos);
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
            List<Grupo> grupos = grupoRepository.buscarRegistros();
            return campoFiltro.filtrar(grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> ordenar(CampoFiltro campoFiltro, List<Grupo> grupos, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("grupos: " + grupos);
        LOG.debug("order: " + order);

        try {
            return campoFiltro.ordenar(grupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarSubGrupos(CampoFiltro campoFiltro, List<Grupo> grupos, Order order) throws ServiceException {
        LOG.info("[ ordenarSubGrupos ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("grupos: " + grupos);
        LOG.debug("order: " + order);

        try {
            return campoFiltro.ordenar(grupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> ordenar(CampoFiltro campoFiltro, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("order: " + order);

        try {
            List<Grupo> grupos = grupoRepository.buscarRegistros();
            return ordenar(campoFiltro, grupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarSubGrupos(CampoFiltro campoFiltro, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("order: " + order);

        try {
            List<Grupo> grupos = grupoRepository.buscarRegistros();
            return ordenarSubGrupos(campoFiltro, grupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
