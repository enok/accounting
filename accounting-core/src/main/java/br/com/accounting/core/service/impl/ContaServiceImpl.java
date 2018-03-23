package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.ContaRepository;
import br.com.accounting.core.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaServiceImpl implements ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Override
    public Long salvar(final Conta conta) throws ServiceException {
        try {
            setarProximoCodigo(contaRepository, conta);
            contaRepository.salvar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar a conta.";
            throw new ServiceException(message, e);
        }
        return conta.codigo();
    }

    @Override
    public void atualizar(final Conta conta) throws ServiceException {
        try {
            contaRepository.atualizar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar a conta.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void atualizarSaldo(final Conta conta, final Double saldo) throws ServiceException {
        try {
            Conta contaAtualizada = conta.clone();
            Double novoSaldo = buscarSaldo(conta) + saldo;
            contaAtualizada.saldo(novoSaldo);

            atualizar(contaAtualizada);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o saldo da conta.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void deletar(final Conta conta) throws ServiceException {
        try {
            contaRepository.deletar(conta);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar a conta.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Conta buscarPorCodigo(final Long codigo) throws ServiceException {
        return buscarPorCodigo(contaRepository, codigo);
    }

    @Override
    public Conta buscarPorNomeDescricao(final String nome, final String descricao) throws ServiceException {
        try {
            List<Conta> contas = contaRepository.buscarRegistros();
            return contaRepository.filtrarPorNomeDescricao(contas, nome, descricao);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar a conta.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<Conta> buscarTodas() throws ServiceException {
        try {
            return contaRepository.buscarRegistros();
        }
        catch (Exception e) {
            String message = "Não foi possível buscar as contas.";
            throw new ServiceException(message, e);
        }
    }

    private Double buscarSaldo(Conta conta) {
        return (conta.saldo() == null) ? 0.0 : conta.saldo();
    }
}
