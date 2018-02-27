package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.Filtro;
import br.com.accounting.core.filter.FiltroSubGrupoDescricao;
import br.com.accounting.core.ordering.CampoOrdem;
import br.com.accounting.core.ordering.CampoOrdemSubGrupoDescricao;
import br.com.accounting.core.repository.SubGrupoRepository;
import br.com.accounting.core.service.SubGrupoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.accounting.core.filter.Duplicates.REMOVE;

@Service
public class SubGrupoServiceImpl extends GenericService<SubGrupo> implements SubGrupoService {
    private static final Logger LOG = LoggerFactory.getLogger(SubGrupoServiceImpl.class);

    @Autowired
    public SubGrupoServiceImpl(SubGrupoRepository repository) {
        super(repository);
    }

    @Override
    public List<SubGrupo> filtrarPorDescricao(String descricao, List<SubGrupo> subGrupos) throws ServiceException {
        LOG.info("[ filtrarPorDescricao ]");
        LOG.debug("descricao: " + descricao);
        LOG.debug("subGrupos: " + subGrupos);

        try {
            Filtro filtro = new FiltroSubGrupoDescricao(descricao);
            return filtrar(filtro, REMOVE, subGrupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> filtrarPorDescricao(String descricao) throws ServiceException {
        LOG.info("[ filtrarPorDescricao ]");
        LOG.debug("descricao: " + descricao);

        try {
            Filtro filtro = new FiltroSubGrupoDescricao(descricao);
            return filtrar(filtro, REMOVE);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarPorDescricao(Order order, List<SubGrupo> subGrupos) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);
        LOG.debug("subGrupos: " + subGrupos);

        try {
            CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();
            return ordenar(campoOrdem, order, subGrupos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubGrupo> ordenarPorDescricao(Order order) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemSubGrupoDescricao();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
