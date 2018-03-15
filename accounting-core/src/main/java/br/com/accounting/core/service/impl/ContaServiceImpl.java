package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContaServiceImpl implements ContaService {
    @Override
    public Long salvar(Conta conta) {
        log.debug("[ salvar ]");
        log.debug("conta: {}", conta);

        return 1L;
    }
}
