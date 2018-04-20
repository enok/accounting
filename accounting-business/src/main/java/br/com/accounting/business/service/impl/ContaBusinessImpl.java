package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.factory.ContaDTOFactory;
import br.com.accounting.business.service.ContaBusiness;
import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContaFactory;
import br.com.accounting.core.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.core.util.Utils.getDoubleFromString;
import static br.com.accounting.core.util.Utils.getStringFromCurrentDate;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ContaBusinessImpl extends GenericAbstractBusiness<ContaDTO, Conta> implements ContaBusiness {
    private ContaService service;

    @Autowired
    public ContaBusinessImpl(final ContaService service) {
        super(service, ContaDTOFactory.create());
        this.service = service;
    }

    @History
    @Override
    public List<Long> criar(ContaDTO dto) throws BusinessException {
        dto.dataAtualizacao(getStringFromCurrentDate());
        return super.criar(dto);
    }

    @History
    @Override
    public void atualizar(ContaDTO dto) throws BusinessException {
        dto.dataAtualizacao(getStringFromCurrentDate());
        super.atualizar(dto);
    }

    @History
    @Override
    public void adicionarCredito(final ContaDTO dto, final String credito) throws BusinessException {
        try {
            Conta entity = criarEntities(dto).get(0);
            Double saldo = getDoubleFromString(credito);
            entity.dataAtualizacao(LocalDate.now());
            service.atualizarSaldo(entity, saldo);
        }
        catch (Exception e) {
            String message = "Não foi possível adicionar crédito à conta.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void adicionarDebito(final ContaDTO dto, final String debito) throws BusinessException {
        try {
            Conta entity = criarEntities(dto).get(0);
            Double saldo = getDoubleFromString(debito);
            entity.dataAtualizacao(LocalDate.now());
            service.atualizarSaldo(entity, -saldo);
        }
        catch (Exception e) {
            String message = "Não foi possível adicionar débito à conta.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void transferir(final ContaDTO origemDTO, final ContaDTO destinoDTO, final String valor) throws BusinessException {
        try {
            Double saldoOrigem = Double.parseDouble(origemDTO.saldo());
            Double valorTransferencia = Double.parseDouble(valor);

            if (saldoOrigem < valorTransferencia) {
                throw new InsufficientFundsException("Saldo insuficiente.");
            }

            adicionarDebito(origemDTO, valor);
            adicionarCredito(destinoDTO, valor);
        }
        catch (Exception e) {
            String message = "Não foi possível tranferir o valor entre as contas.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public void validarEntrada(final ContaDTO dto, final List<String> erros) throws MissingFieldException {
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        if (isBlank(dto.cumulativo())) {
            erros.add(format(msg, "cumulativo"));
        }
        conferirErrosCamposObrigatorios(erros);
    }

    @Override
    public void validarEntradaUpdate(final ContaDTO dto, final Conta entity, final List<String> erros) throws MissingFieldException, UpdateException {
        conferirCodigo(dto, erros);
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirCodigoAlterado(dto, entity, errosUpdate);
        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final Conta conta) throws ServiceException, DuplicatedRegistryException {
        Conta contaBuscada = service.buscarPorNome(conta.nome());

        if (conta.equals(contaBuscada)) {
            throw new DuplicatedRegistryException("Conta duplicada.");
        }
    }

    @Override
    public List<Conta> criarEntities(final ContaDTO dto) throws ParseException {
        return asList(ContaFactory
                .begin()
                .withCodigo(dto.codigo())
                .withNome(dto.nome())
                .withDescricao(dto.descricao())
                .withSaldo(dto.saldo())
                .withCumulativo(dto.cumulativo())
                .withDataAtualizacao(dto.dataAtualizacao())
                .build());
    }
}
