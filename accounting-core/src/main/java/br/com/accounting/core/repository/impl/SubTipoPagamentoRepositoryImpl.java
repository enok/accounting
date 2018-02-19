package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.factory.SubTipoPagamentoFactory;
import br.com.accounting.core.repository.SubTipoPagamentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class SubTipoPagamentoRepositoryImpl extends GenericRepository<SubTipoPagamento> implements SubTipoPagamentoRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SubTipoPagamentoRepositoryImpl.class);

    private static final String ARQUIVO_SUBTIPO_PAGAMENTOS_CONTAGEM = DIRETORIO + "\\subTipoPagamentos-contagem.txt";
    private static final String ARQUIVO_SUBTIPO_PAGAMENTOS = DIRETORIO + "\\subTipoPagamentos.csv";

    @Override
    public String getArquivoContagem() {
        return ARQUIVO_SUBTIPO_PAGAMENTOS_CONTAGEM;
    }

    @Override
    public String getArquivo() {
        return ARQUIVO_SUBTIPO_PAGAMENTOS;
    }

    @Override
    public String criarLinha(SubTipoPagamento subTipoPagamento) {

        StringBuilder builder = new StringBuilder()
                .append(subTipoPagamento.getCodigo()).append(SEPARADOR)
                .append(subTipoPagamento.getDescricao()).append("\n");

        return builder.toString();
    }

    @Override
    public List<SubTipoPagamento> criarRegistros(List<String> linhas) {
        LOG.info("[ criarRegistros ] linhas: " + linhas);

        List<SubTipoPagamento> subTipoPagamentoList = new ArrayList<>();

        for (String linha : linhas) {
            SubTipoPagamento subTipoPagamento = criarSubTipoPagamento(linha);
            LOG.debug("subTipoPagamento: " + subTipoPagamento);
            subTipoPagamentoList.add(subTipoPagamento);
        }

        return subTipoPagamentoList;
    }

    private SubTipoPagamento criarSubTipoPagamento(String linha) {
        LOG.debug("[ criarSubTipoPagamento ] linha: " + linha);

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
