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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public List<SubTipoPagamento> filtrar(CampoFiltro campoFiltro, List<SubTipoPagamento> subTipoPagamentos) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("subTipoPagamentos: " + subTipoPagamentos);

        try {
            return campoFiltro.filtrar(subTipoPagamentos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> filtrar(CampoFiltro campoFiltro) throws ServiceException {
        LOG.info("[ filtrar ]");
        LOG.debug("campoFiltro: " + campoFiltro);

        try {
            List<SubTipoPagamento> subTipoPagamentos = subTipoPagamentoRepository.buscarRegistros();
            return filtrar(campoFiltro, subTipoPagamentos);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> ordenar(CampoFiltro campoFiltro, List<SubTipoPagamento> subTipoPagamentos, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("subTipoPagamentos: " + subTipoPagamentos);
        LOG.debug("order: " + order);

        try {
            return campoFiltro.ordenar(subTipoPagamentos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<SubTipoPagamento> ordenar(CampoFiltro campoFiltro, Order order) throws ServiceException {
        LOG.info("[ ordenar ]");
        LOG.debug("campoFiltro: " + campoFiltro);
        LOG.debug("order: " + order);

        try {
            List<SubTipoPagamento> subTipoPagamentos = subTipoPagamentoRepository.buscarRegistros();
            return ordenar(campoFiltro, subTipoPagamentos, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
