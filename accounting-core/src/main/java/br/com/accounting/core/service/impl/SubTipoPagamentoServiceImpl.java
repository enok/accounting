package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.CampoFiltro;
import br.com.accounting.core.repository.SubTipoPagamentoRepository;
import br.com.accounting.core.service.SubTipoPagamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubTipoPagamentoServiceImpl implements SubTipoPagamentoService {
    private static final Logger LOG = LoggerFactory.getLogger(SubTipoPagamentoServiceImpl.class);

    @Autowired
    private SubTipoPagamentoRepository subTipoPagamentoRepository;

    @Override
    public void salvar(SubTipoPagamento subTipoPagamento) throws ServiceException {
        LOG.info("[ salvar ]");
        LOG.debug("subTipoPagamento: " + subTipoPagamento);

        try {
            subTipoPagamentoRepository.salvar(subTipoPagamento);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel salvar o subTipoPagamento: " + subTipoPagamento;
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> buscarRegistros() throws ServiceException {
        LOG.info("[ buscarRegistros ]");

        try {
            return subTipoPagamentoRepository.buscarRegistros();
        } catch (Exception e) {
            String mensagem = "Nao foi possivel buscar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> filtrar(CampoFiltro campoFiltro, List<SubTipoPagamento> entities) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("entities: " + entities);

        return null;
    }

    @Override
    public List<SubTipoPagamento> filtrar(CampoFiltro campoFiltro) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);

        return null;
    }

    @Override
    public List<SubTipoPagamento> ordenar(CampoFiltro campoFiltro, List<SubTipoPagamento> entities, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        return null;
    }

    @Override
    public List<SubTipoPagamento> ordenar(CampoFiltro campoFiltro, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("order: " + order);

        return null;
    }
}
