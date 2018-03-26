package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Cartao;
import br.com.accounting.core.factory.CartaoFactory;
import br.com.accounting.core.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
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
    public Cartao filtrarCodigo(final List<Cartao> cartoes, final String numero) {
        List<Cartao> cartoesFiltrados = cartoes
                .stream()
                .filter(c -> (c.numero().equals(numero)))
                .collect(Collectors.toList());
        if (isEmpty(cartoesFiltrados)) {
            return null;
        }
        return cartoesFiltrados.get(0);
    }

    @Override
    public void ordenarPorNumero(final List<Cartao> cartoes) {
        cartoes.sort(Comparator.comparing(Cartao::numero));
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
                .append(getDoubleFormatted(entity.limite()));
        return builder.toString();
    }

    @Override
    public List<Cartao> criarRegistros(final List<String> linhas) throws ParseException {
        List<Cartao> cartoes = new ArrayList<>();
        for (String linha : linhas) {
            Cartao cartao = criarCartao(linha);
            cartoes.add(cartao);
        }
        return cartoes;
    }

    private Cartao criarCartao(final String linha) throws ParseException {
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