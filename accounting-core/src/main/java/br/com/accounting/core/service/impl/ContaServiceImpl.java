package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.ContaRepository;
import br.com.accounting.core.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContaServiceImpl implements ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Override
    public Long salvar(final Conta conta) throws ServiceException {
        log.debug("[ salvar ]");
        log.debug("conta: {}", conta);

        try {
            setaProximoCodigo(conta);
            contaRepository.salvar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar a conta.";
            log.error(message, e);
            throw new ServiceException(message, e);
        }

        return conta.codigo();
    }

    @Override
    public Conta buscarPorCodigo(final Long codigo) throws ServiceException {
        log.debug("[ buscarPorCodigo ]");
        log.debug("codigo: {}", codigo);

        try {
            List<Conta> contas = contaRepository.buscarRegistros();
            return contaRepository.filtrarCodigo(contas, codigo);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar a conta por código.";
            log.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Conta buscarPorNomeDescricao(final String nome, final String descricao) throws ServiceException {
        log.debug("[ buscarPorNomeDescricao ]");
        log.debug("nome: {}", nome);
        log.debug("descricao: {}", descricao);

        try {
            List<Conta> contas = contaRepository.buscarRegistros();
            return contaRepository.filtrarPorNomeDescricao(contas, nome, descricao);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar a conta.";
            log.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<Conta> buscarTodas() throws ServiceException {
        log.debug("[ buscarTodas ]");

        try {
            return contaRepository.buscarRegistros();
        }
        catch (Exception e) {
            String message = "Não foi possível buscar as contas.";
            log.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void atualizarSaldo(final Conta conta, final Double saldo) throws ServiceException {
        log.debug("[ atualizarSaldo ]");
        log.debug("conta: {}", conta);
        log.debug("saldo: {}", saldo);

        try {
            Conta contaAtualizada = conta.clone();
            Double novoSaldo = buscarSaldo(conta) + saldo;
            contaAtualizada.saldo(novoSaldo);
            contaRepository.atualizar(conta, contaAtualizada);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar a conta.";
            log.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    private Double buscarSaldo(Conta conta) {
        return (conta.saldo() == null) ? 0.0 : conta.saldo();
    }

    @Override
    public void deletar(final Conta conta) throws ServiceException {
        log.debug("[ deletar ]");
        log.debug("conta: {}", conta);

        try {
            contaRepository.deletar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar a conta.";
            log.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    private void setaProximoCodigo(Conta conta) throws RepositoryException {
        log.trace("[ setaProximoCodigo ]");
        log.trace("entity: {}", conta);

        Long proximoCodigo = contaRepository.proximoCodigo();
        contaRepository.incrementarCodigo(proximoCodigo);
        conta.codigo(proximoCodigo);
    }
}
