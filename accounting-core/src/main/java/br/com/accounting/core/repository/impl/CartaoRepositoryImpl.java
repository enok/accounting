package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.factory.CartaoFactory;
import br.com.accounting.core.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.*;
import static org.springframework.util.CollectionUtils.isEmpty;

@Repository
public class CartaoRepositoryImpl extends GenericAbstractRepository<Cartao> implements CartaoRepository {
    @Autowired
    private String diretorio;

    @Override
    public Cartao filtrarCodigo(final List<Cartao> entities, final String numero) {
        List<Cartao> cartoesFiltrados = entities
                .stream()
                .filter(c -> (c.numero().equals(numero)))
                .collect(Collectors.toList());
        if (isEmpty(cartoesFiltrados)) {
            return null;
        }
        return cartoesFiltrados.get(0);
    }

    @Override
    public void ordenarPorNumero(final List<Cartao> entities) {
        entities.sort(Comparator.comparing(Cartao::numero));
    }

    @Override
    public String getArquivo() {
        return diretorio + File.separator + "cartoes.csv";
    }

    @Override
    public String getArquivoContagem() {
        return diretorio + File.separator + "cartoes-contagem.txt";
    }

    @Override
    public String criarLinha(final Cartao entity) {
        StringBuilder builder = new StringBuilder()
                .append(entity.codigo()).append(SEPARADOR)
                .append(entity.numero()).append(SEPARADOR)
                .append(getStringFromDate(entity.vencimento())).append(SEPARADOR)
                .append(getStringFromDate(entity.diaMelhorCompra())).append(SEPARADOR)
                .append(entity.portador()).append(SEPARADOR)
                .append(entity.tipo()).append(SEPARADOR)
                .append(getStringFromDouble(entity.limite()));
        return builder.toString();
    }

    @Override
    public Cartao criarEntity(final String linha) {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        return CartaoFactory
                .begin()
                .withCodigo(registro.get(0))
                .withNumero(registro.get(1))
                .withVencimento(registro.get(2))
                .withDiaMelhorCompra(registro.get(3))
                .withPortador(registro.get(4))
                .withTipo(registro.get(5))
                .withLimite(registro.get(6))
                .build();
    }
}
