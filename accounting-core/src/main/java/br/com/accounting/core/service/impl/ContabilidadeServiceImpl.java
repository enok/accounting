package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.repository.ContabilidadeRepository;
import br.com.accounting.core.service.ContabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContabilidadeServiceImpl extends GenericService<Contabilidade> implements ContabilidadeService {

    @Autowired
    public ContabilidadeServiceImpl(ContabilidadeRepository contabilidadeRepository) {
        super(contabilidadeRepository);
    }
}
