package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.repository.CartaoRepository;
import br.com.accounting.core.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class CartaoServiceImpl extends GenericAbstractService<Cartao> implements CartaoService {
    private CartaoRepository repository;

    @Autowired
    public CartaoServiceImpl(CartaoRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Cartao buscarPorNumero(final String numero) throws ParseException, StoreException {
        try {
            List<Cartao> cartoes = repository.buscarRegistros();
            return repository.filtrarCodigo(cartoes, numero);
        }
        catch (StoreException e) {
            throw e;
        }
    }

    @Override
    public void ordenarTodas(final List<Cartao> entities) {
        repository.ordenarPorNumero(entities);
    }
}
