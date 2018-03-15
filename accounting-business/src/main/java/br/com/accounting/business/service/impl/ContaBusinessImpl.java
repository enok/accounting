package br.com.accounting.business.service.impl;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.service.ContaBusiness;
import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.factory.ContaFactory;
import br.com.accounting.core.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
public class ContaBusinessImpl implements ContaBusiness {
    @Autowired
    private ContaService contaService;

    @Override
    public Long criar(final ContaDTO contaDTO) throws BusinessException {
        log.info("[ criar ]");
        log.info("contaDTO: {}", contaDTO);

        final List<String> erros = new ArrayList<>();

        validarEntrada(contaDTO, erros);

        Conta conta = ContaFactory
                .begin()
                .withNome(contaDTO.nome())
                .withDescricao(contaDTO.descricao())
                .withSaldo(0.0)
                .build();

        return contaService.salvar(conta);
    }

    private void validarEntrada(final ContaDTO contaDTO, final List<String> erros) throws MissingFieldException {
        String msg = "O campo %s é obrigatório.";

        if (isBlank(contaDTO.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(contaDTO.descricao())) {
            erros.add(format(msg, "descrição"));
        }

        log.trace("erros: {}", erros);

        if (!isEmpty(erros)) {
            throw new MissingFieldException(erros);
        }
    }
}
