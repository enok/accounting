package br.com.accounting.core.repository;

import br.com.accounting.core.entity.Cartao;

import java.util.List;

public interface CartaoRepository extends GenericRepository<Cartao> {
    Cartao filtrarCodigo(List<Cartao> cartoes, String numero);
}
