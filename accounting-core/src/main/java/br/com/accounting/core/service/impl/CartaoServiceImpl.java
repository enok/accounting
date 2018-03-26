package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.entity.Tipo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.CartaoRepository;
import br.com.accounting.core.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartaoServiceImpl implements CartaoService {
    @Autowired
    private CartaoRepository cartaoRepository;

    @Override
    public Long salvar(final Cartao cartao) throws ServiceException {
        try {
            setarProximoCodigo(cartaoRepository, cartao);
            cartaoRepository.salvar(cartao);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar o cartão.";
            throw new ServiceException(message, e);
        }
        return cartao.codigo();
    }

    @Override
    public void atualizar(final Cartao cartao) throws ServiceException {
        try {
            cartaoRepository.atualizar(cartao);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o cartão.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void deletar(final Cartao cartao) throws ServiceException {
        try {
            cartaoRepository.deletar(cartao);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar o cartão.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public Cartao buscarPorCodigo(final Long codigo) throws ServiceException {
        return buscarPorCodigo(cartaoRepository, codigo);
    }

    @Override
    public Cartao buscarPorNumero(final String numero) throws ServiceException {
        try {
            List<Cartao> cartoes = cartaoRepository.buscarRegistros();
            return cartaoRepository.filtrarCodigo(cartoes, numero);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o cartão por código.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<Cartao> buscarTodos() throws ServiceException {
        try {
            List<Cartao> cartoes = cartaoRepository.buscarRegistros();
            cartaoRepository.ordenarPorNumero(cartoes);
            return cartoes;
        }
        catch (Exception e) {
            String message = "Não foi possível buscar os cartões.";
            throw new ServiceException(message, e);
        }
    }
}
