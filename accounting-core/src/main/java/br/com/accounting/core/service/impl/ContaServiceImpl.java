package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.repository.ContaRepository;
import br.com.accounting.core.service.ContaService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ContaServiceImpl extends GenericAbstractService<Conta> implements ContaService {
    private ContaRepository repository;

    @Autowired
    public ContaServiceImpl(ContaRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void atualizarSaldo(final Conta conta, final Double saldo) throws StoreException, ServiceException {
        try {
            Conta contaAtualizada = SerializationUtils.clone(conta);
            Double novoSaldo = buscarSaldo(conta) + saldo;
            contaAtualizada.saldo(novoSaldo);

            atualizar(contaAtualizada);
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o saldo da conta.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Conta buscarPorNome(final String nome) throws StoreException, ParseException {
        try {
            List<Conta> contas = repository.buscarRegistros();
            return repository.filtrarPorNome(contas, nome);
        }
        catch (StoreException e) {
            throw e;
        }
    }

    @Override
    public List<Conta> buscarCumulativas() throws StoreException, ParseException {
        try {
            List<Conta> entities = repository.buscarRegistros();
            return repository.filtrarCumulativas(entities);
        }
        catch (StoreException e) {
            throw e;
        }
    }

    private Double buscarSaldo(Conta conta) {
        return (conta.saldo() == null) ? 0.0 : conta.saldo();
    }

    @Override
    public void ordenarTodas(final List<Conta> entities) {
        repository.ordenarPorNome(entities);
    }
}
