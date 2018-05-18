package br.com.accounting.cartao.repository;


import br.com.accounting.commons.entity.Cartao;
import br.com.accounting.commons.repository.GenericRepository;

import java.util.List;

public interface CartaoRepository extends GenericRepository<Cartao> {
    Cartao filtrarCodigo(List<Cartao> cartoes, String numero);

    void ordenarPorNumero(List<Cartao> cartoes);
}
