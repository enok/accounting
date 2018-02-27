package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.Filtro;
import br.com.accounting.core.filter.FiltroSubTipoPagamentoDescricao;
import br.com.accounting.core.ordering.CampoOrdem;
import br.com.accounting.core.ordering.CampoOrdemSubTipoPagamentoDescricao;
import br.com.accounting.core.repository.SubTipoPagamentoRepository;
import br.com.accounting.core.service.SubTipoPagamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.accounting.core.filter.Duplicates.REMOVE;

@Service
public class SubTipoPagamentoServiceImpl extends GenericService<SubTipoPagamento> implements SubTipoPagamentoService {
    private static final Logger LOG = LoggerFactory.getLogger(SubTipoPagamentoServiceImpl.class);

    public SubTipoPagamentoServiceImpl(SubTipoPagamentoRepository subTipoPagamentoRepository) {
        super(subTipoPagamentoRepository);
    }

    @Override
    public List<SubTipoPagamento> filtrarPorDescricao(String descricao, List<SubTipoPagamento> subTipoPagamentos) throws ServiceException {
        LOG.info("[ filtrarPorDescricao ]");
        LOG.debug("descricao: " + descricao);
        LOG.debug("subTipoPagamentos: " + subTipoPagamentos);

        try {
            Filtro filtro = new FiltroSubTipoPagamentoDescricao(descricao);
            return filtrar(filtro, REMOVE, subTipoPagamentos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> filtrarPorDescricao(String descricao) throws ServiceException {
        LOG.info("[ filtrarPorDescricao ]");
        LOG.debug("descricao: " + descricao);

        try {
            Filtro filtro = new FiltroSubTipoPagamentoDescricao(descricao);
            return filtrar(filtro, REMOVE);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> ordenarPorDescricao(Order order, List<SubTipoPagamento> subTipoPagamentos) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);
        LOG.debug("subTipoPagamentos: " + subTipoPagamentos);

        try {
            CampoOrdem campoOrdem = new CampoOrdemSubTipoPagamentoDescricao();
            return ordenar(campoOrdem, order, subTipoPagamentos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> ordenarPorDescricao(Order order) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemSubTipoPagamentoDescricao();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
