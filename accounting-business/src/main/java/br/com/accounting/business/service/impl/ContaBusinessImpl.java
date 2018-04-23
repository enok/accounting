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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.core.util.Utils.getDoubleFromString;
import static br.com.accounting.core.util.Utils.isMonthChanged;
import static java.lang.String.format;
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
    public void adicionarCredito(final ContaDTO dto, final String credito) throws BusinessException {
        try {
            Conta entity = criarEntity(dto);
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
            Conta entity = criarEntity(dto);
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
            Double saldoOrigem = getDoubleFromString(origemDTO.saldo());
            Double valorTransferencia = getDoubleFromString(valor);

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
    public void atualizarContas() throws BusinessException {
        try {
            List<Conta> entitiesBuscadas = service.buscarCumulativas();
            for (Conta entity : entitiesBuscadas) {
                LocalDate dataAtualizacao = entity.dataAtualizacao();
                if (isMonthChanged(dataAtualizacao)) {
                    Double novoSaldo = entity.valorDefault() + entity.saldo();
                    entity.saldo(novoSaldo);
                    service.atualizar(entity);
                }
            }
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar as contas.";
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
        if (isBlank(dto.valorDefault())) {
            erros.add(format(msg, "valorDefault"));
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
    public Conta criarEntity(final ContaDTO dto) {
        ContaFactory factory = ContaFactory
                .begin();
        preenderFactory(factory, dto);
        return factory
                .build();
    }

    @Override
    protected Conta criarEntity(ContaDTO dto, Conta entityBuscado) {
        ContaFactory factory = ContaFactory
                .begin()
                .preencherCamposBuscados(entityBuscado);
        preenderFactory(factory, dto);
        return factory
                .build();
    }

    private void preenderFactory(final ContaFactory factory, final ContaDTO dto) {
        factory
                .withCodigo(dto.codigo())
                .withNome(dto.nome())
                .withDescricao(dto.descricao())
                .withValorDefault(dto.valorDefault())
                .withSaldo(dto.saldo())
                .withCumulativo(dto.cumulativo())
                .withDataAtualizacao(dto.dataAtualizacao());
    }
}
