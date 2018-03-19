package br.com.accounting.business.service.impl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.core.util.Utils.createDouble;
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

        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(contaDTO, erros);

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
            String message = "Não foi possível criar a conta";
            log.error(message, e);
            throw new BusinessException(message, e);
        }
    }

    @Override
    public ContaDTO buscarContaPorId(final Long codigo) throws BusinessException {
        log.info("[ buscarContas ]");
        log.info("codigo: {}", codigo);

        try {
            Conta conta = contaService.buscarPorCodigo(codigo);
            return criarConta(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar a conta por id.";
            log.error(message, e);
            throw new BusinessException(message, e);
        }
    }

    @Override
    public List<ContaDTO> buscarContas() throws BusinessException {
        log.info("[ buscarContas ]");

        List<ContaDTO> contasDTO;
        try {
            List<Conta> contas = contaService.buscarTodas();
            contasDTO = criarListaContasDTO(contas);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar as contas.";
            log.error(message, e);
            throw new BusinessException(message, e);
        }

        return contasDTO;
    }

    @Override
    public void adicionarCredito(final ContaDTO contaDTO, final String credito) throws BusinessException {
        log.info("[ adicionarCredito ]");
        log.info("contaDTO: {}", contaDTO);
        log.info("credito: {}", credito);

        try {
            Conta conta = criarConta(contaDTO);
            Double saldo = createDouble(credito);
            contaService.atualizarSaldo(conta, saldo);
        }
        catch (Exception e) {
            String message = "Não foi possível adicionar crédito à conta.";
            log.error(message, e);
            throw new BusinessException(message, e);
        }
    }

    @Override
    public void adicionarDebito(final ContaDTO contaDTO, final String debito) throws BusinessException {
        log.info("[ adicionarDebito ]");
        log.info("contaDTO: {}", contaDTO);
        log.info("debito: {}", debito);

        try {
            Conta conta = criarConta(contaDTO);
            Double saldo = createDouble(debito);
            contaService.atualizarSaldo(conta, -saldo);
        }
        catch (Exception e) {
            String message = "Não foi possível adicionar débito à conta.";
            log.error(message, e);
            throw new BusinessException(message, e);
        }
    }

    @Override
    public void excluir(final ContaDTO contaDTO) throws BusinessException {
        log.info("[ excluir ]");
        log.info("contaDTO: {}", contaDTO);

        try {
            Conta conta = criarConta(contaDTO);
            contaService.deletar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível excluir a conta.";
            log.error(message, e);
            throw new BusinessException(message, e);
        }
    }

    @Override
    public void transferir(final ContaDTO contaOrigemDTO, final ContaDTO contaDestinoDTO, final String valor) throws BusinessException {
        log.info("[ transferir ]");
        log.info("contaOrigemDTO: {}", contaOrigemDTO);
        log.info("contaDestinoDTO: {}", contaDestinoDTO);
        log.info("valor: {}", valor);

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
            log.error(message, e);
            throw new BusinessException(message, e);
        }
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

    private void validaRegistroDuplicado(Conta conta) throws ServiceException, DuplicatedRegistryException {
        Conta contaBuscada = contaService.buscarPorNomeDescricao(conta.nome(), conta.descricao());

        if (conta.equals(contaBuscada)) {
            throw new DuplicatedRegistryException("Conta duplicada.");
        }
    }

    private List<ContaDTO> criarListaContasDTO(List<Conta> contas) {
        List<ContaDTO> contasDTO = new ArrayList<>();
        for (Conta conta : contas) {
            contasDTO.add(criarConta(conta));
        }
        return contasDTO;
    }

    private Conta criarConta(ContaDTO contaDTO) throws ParseException {
        return ContaFactory
                .begin()
                .withCodigo(contaDTO.codigo())
                .withNome(contaDTO.nome())
                .withDescricao(contaDTO.descricao())
                .withSaldo(contaDTO.saldo())
                .build();
    }

    private ContaDTO criarConta(Conta conta) {
        return ContaDTOFactory
                .begin()
                .preencherCampos(conta)
                .build();
    }
}
