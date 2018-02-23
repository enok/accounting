package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.repository.SubTipoPagamentoRepository;
import br.com.accounting.core.service.SubTipoPagamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SubTipoPagamentoServiceImpl extends GenericService<SubTipoPagamento> implements SubTipoPagamentoService {

    public SubTipoPagamentoServiceImpl(SubTipoPagamentoRepository subTipoPagamentoRepository) {
        super(subTipoPagamentoRepository);
    }
}
