package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
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
}
