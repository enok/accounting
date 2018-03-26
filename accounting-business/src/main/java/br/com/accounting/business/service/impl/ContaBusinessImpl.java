package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.InsufficientFundsException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.ContaDTOFactory;
import br.com.accounting.business.service.ContaBusiness;
import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.ContaFactory;
import br.com.accounting.core.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.core.util.Utils.createDouble;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ContaBusinessImpl implements ContaBusiness {
    @Autowired
    private ContaService contaService;

    @History
    @Override
    public Long criar(final ContaDTO contaDTO) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(contaDTO, erros, false);

            Conta conta = ContaFactory
                    .begin()
                    .withNome(contaDTO.nome())
                    .withDescricao(contaDTO.descricao())
                    .withSaldo(0.0)
                    .build();

            validaRegistroDuplicado(conta);

            return contaService.salvar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível criar a conta.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void atualizar(final ContaDTO contaDTO) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(contaDTO, erros, true);

            Conta conta = ContaFactory
                    .begin()
                    .withCodigo(contaDTO.codigo())
                    .withNome(contaDTO.nome())
                    .withDescricao(contaDTO.descricao())
                    .withSaldo(contaDTO.saldo())
                    .build();

            contaService.atualizar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar a conta.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void adicionarCredito(final ContaDTO contaDTO, final String credito) throws BusinessException {
        try {
            Conta conta = criarEntity(contaDTO);
            Double saldo = createDouble(credito);
            contaService.atualizarSaldo(conta, saldo);
        }
        catch (Exception e) {
            String message = "Não foi possível adicionar crédito à conta.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void adicionarDebito(final ContaDTO contaDTO, final String debito) throws BusinessException {
        try {
            Conta conta = criarEntity(contaDTO);
            Double saldo = createDouble(debito);
            contaService.atualizarSaldo(conta, -saldo);
        }
        catch (Exception e) {
            String message = "Não foi possível adicionar débito à conta.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void transferir(final ContaDTO contaOrigemDTO, final ContaDTO contaDestinoDTO, final String valor) throws BusinessException {
        try {
            Double saldoOrigem = Double.parseDouble(contaOrigemDTO.saldo());
            Double valorTransferencia = Double.parseDouble(valor);

            if (saldoOrigem < valorTransferencia) {
                throw new InsufficientFundsException("Saldo insuficiente.");
            }

            adicionarDebito(contaOrigemDTO, valor);
            adicionarCredito(contaDestinoDTO, valor);
        }
        catch (Exception e) {
            String message = "Não foi possível tranferir o valor entre as contas.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void excluir(final ContaDTO contaDTO) throws BusinessException {
        try {
            Conta conta = criarEntity(contaDTO);
            contaService.deletar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível excluir a conta.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public ContaDTO buscarContaPorId(final Long codigo) throws BusinessException {
        try {
            Conta conta = contaService.buscarPorCodigo(codigo);
            return criarDTOEntity(ContaDTOFactory.create(), conta);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar a conta por id.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public List<ContaDTO> buscarContas() throws BusinessException {
        List<ContaDTO> contasDTO;
        try {
            List<Conta> contas = contaService.buscarTodas();
            contasDTO = criarListaEntitiesDTO(ContaDTOFactory.create(), contas);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar as contas.";
            throw new BusinessException(message, e);
        }

        return contasDTO;
    }

    @Override
    public void validarEntrada(final ContaDTO dto, final List<String> erros, final boolean atualizacao) throws MissingFieldException {
        String msg = "O campo %s é obrigatório.";

        if (atualizacao) {
            if (isBlank(dto.codigo())) {
                erros.add(format(msg, "código"));
            }
        }
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }

        conferirErros(erros);
    }

    @Override
    public void validaRegistroDuplicado(final Conta conta) throws ServiceException, DuplicatedRegistryException {
        Conta contaBuscada = contaService.buscarPorNome(conta.nome());

        if (conta.equals(contaBuscada)) {
            throw new DuplicatedRegistryException("Conta duplicada.");
        }
    }

    @Override
    public Conta criarEntity(final ContaDTO entity) throws ParseException {
        return ContaFactory
                .begin()
                .withCodigo(entity.codigo())
                .withNome(entity.nome())
                .withDescricao(entity.descricao())
                .withSaldo(entity.saldo())
                .build();
    }
}
