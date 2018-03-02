package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.factory.SubTipoPagamentoFactory;
import br.com.accounting.core.repository.SubTipoPagamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.SEPARADOR;

@Repository
public class SubTipoPagamentoRepositoryImpl extends GenericRepository<SubTipoPagamento> implements SubTipoPagamentoRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SubTipoPagamentoRepositoryImpl.class);

    @Autowired
    private String diretorio;

    @Override
    public String getArquivoContagem() {
        return diretorio + "\\subtTipoPagamentos-contagem.txt";
    }

    @Override
    public String getArquivo() {
        return diretorio + "\\subTipoPagamentos.csv";
    }

    @Override
    public String criarLinha(SubTipoPagamento subTipoPagamento) {
        LOG.info("[ criarLinha ]");
        LOG.debug("subTipoPagamento: " + subTipoPagamento);

        StringBuilder builder = new StringBuilder()
                .append(subTipoPagamento.getCodigo()).append(SEPARADOR)
                .append(subTipoPagamento.getDescricao());

        return builder.toString();
    }

    @Override
    public List<SubTipoPagamento> criarRegistros(List<String> linhas) {
        LOG.info("[ criarRegistros ]");
        LOG.debug("linhas: " + linhas);

        List<SubTipoPagamento> subTipoPagamentoList = new ArrayList<>();

        for (String linha : linhas) {
            SubTipoPagamento subTipoPagamento = criarSubTipoPagamento(linha);
            LOG.debug("subTipoPagamento: " + subTipoPagamento);
            subTipoPagamentoList.add(subTipoPagamento);
        }

        return subTipoPagamentoList;
    }

    private SubTipoPagamento criarSubTipoPagamento(String linha) {
        LOG.debug("[ criarSubTipoPagamento ]");
        LOG.debug("linha: " + linha);

        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        return SubTipoPagamentoFactory
                .begin()
                .withCodigo(registro.get(0))
                .withDescricao(registro.get(1))
                .build();
    }
}
