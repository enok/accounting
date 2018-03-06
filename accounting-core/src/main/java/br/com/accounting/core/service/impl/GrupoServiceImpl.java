package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.Filtro;
import br.com.accounting.core.filter.FiltroGrupoDescricao;
import br.com.accounting.core.filter.FiltroGrupoDescricaoSubGrupo;
import br.com.accounting.core.filter.FiltroGrupoDescricaoSubGrupoDescricao;
import br.com.accounting.core.ordering.*;
import br.com.accounting.core.ordering.OrdemGrupoDescricaoSubGrupoDescricao;
import br.com.accounting.core.ordering.Ordem;
import br.com.accounting.core.repository.GrupoRepository;
import br.com.accounting.core.service.GrupoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.accounting.core.filter.Duplicates.REMOVE;

@Service
public class GrupoServiceImpl extends GenericAbstractService<Grupo> implements GrupoService {
    private static final Logger LOG = LoggerFactory.getLogger(GrupoServiceImpl.class);

    @Autowired
    public GrupoServiceImpl(GrupoRepository repository) {
        super(repository);
    }

    @Override
    public List<Grupo> filtrarPorDescricao(String descricao, List<Grupo> grupos) throws ServiceException {
        LOG.info("[ filtrarSubGruposPorGrupoDescricao ]");
        LOG.debug("descricao: " + descricao);
        LOG.debug("grupos: " + grupos);

        try {
            Filtro filtro = new FiltroGrupoDescricao(descricao);
            return filtrar(filtro, REMOVE, grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> filtrarPorDescricao(String descricao) throws ServiceException {
        LOG.info("[ filtrarPorDescricao ]");
        LOG.debug("descricao: " + descricao);

        try {
            Filtro filtro = new FiltroGrupoDescricao(descricao);
            return filtrar(filtro, REMOVE);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> filtrarPorDescricaoESubGrupo(String descricaoGrupo, String descricaoSubGrupo, List<Grupo> grupos) throws ServiceException {
        LOG.info("[ filtrarPorDescricaoESubGrupo ]");
        LOG.debug("descricaoGrupo: " + descricaoGrupo);
        LOG.debug("descricaoSubGrupo: " + descricaoSubGrupo);
        LOG.debug("grupos: " + grupos);

        try {
            Filtro filtro = new FiltroGrupoDescricaoSubGrupoDescricao(descricaoGrupo, descricaoSubGrupo);
            return filtrar(filtro, REMOVE);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> filtrarPorDescricaoESubGrupo(String descricaoGrupo, String descricaoSubGrupo) throws ServiceException {
        LOG.info("[ filtrarPorDescricaoESubGrupo ]");
        LOG.debug("descricaoGrupo: " + descricaoGrupo);
        LOG.debug("descricaoSubGrupo: " + descricaoSubGrupo);

        try {
            Filtro filtro = new FiltroGrupoDescricaoSubGrupoDescricao(descricaoGrupo, descricaoSubGrupo);
            return filtrar(filtro, REMOVE);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> filtrarSubGruposPorGrupoDescricao(String descricaoGrupo, List<Grupo> grupos) throws ServiceException {
        LOG.info("[ filtrarSubGruposPorGrupoDescricao ]");
        LOG.debug("descricaoGrupo: " + descricaoGrupo);
        LOG.debug("grupos: " + grupos);

        try {
            Filtro filtro = new FiltroGrupoDescricaoSubGrupo(descricaoGrupo);
            return filtro.filtrar(grupos, REMOVE);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os subgrupo por descricao de grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> filtrarSubGruposPorGrupoDescricao(String descricaoGrupo) throws ServiceException {
        LOG.info("[ filtrarSubGruposPorGrupoDescricao ]");
        LOG.debug("descricaoGrupo: " + descricaoGrupo);

        try {
            List<Grupo> grupos = buscarRegistros();
            return filtrarSubGruposPorGrupoDescricao(descricaoGrupo, grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os subgrupo por descricao de grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> ordenarPorDescricao(Order order, List<Grupo> grupos) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);
        LOG.debug("grupos: " + grupos);

        try {
            Ordem ordem = new OrdemGrupoDescricao();
            return ordenar(ordem, order, grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> ordenarPorDescricao(Order order) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);

        try {
            Ordem ordem = new OrdemGrupoDescricao();
            return ordenar(ordem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> ordenarPorDescricaoESubGrupo(Order order, List<Grupo> grupos) throws ServiceException {
        LOG.info("[ ordenarPorDescricaoESubGrupo ]");
        LOG.debug("order: " + order);
        LOG.debug("grupos: " + grupos);

        try {
            Ordem ordem = new OrdemGrupoDescricaoSubGrupoDescricao();
            return ordenar(ordem, order, grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Grupo> ordenarPorDescricaoESubGrupo(Order order) throws ServiceException {
        LOG.info("[ ordenarPorDescricaoESubGrupo ]");
        LOG.debug("order: " + order);

        try {
            Ordem ordem = new OrdemGrupoDescricaoSubGrupoDescricao();
            return ordenar(ordem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarSubGruposPorGrupoDescricao(Order order, List<Grupo> grupos) throws ServiceException {
        LOG.info("[ ordenarSubGruposPorGrupoDescricao ]");
        LOG.debug("order: " + order);
        LOG.debug("grupos: " + grupos);

        try {
            Ordem ordem = new OrdemGrupoDescricaoSubGrupo();
            return ordem.ordenar(grupos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os subgrupo por descricao de grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarSubGruposPorGrupoDescricao(Order order) throws ServiceException {
        LOG.info("[ ordenarSubGruposPorGrupoDescricao ]");
        LOG.debug("order: " + order);

        try {
            List<Grupo> grupos = buscarRegistros();
            return ordenarSubGruposPorGrupoDescricao(order, grupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os subgrupo por descricao de grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
