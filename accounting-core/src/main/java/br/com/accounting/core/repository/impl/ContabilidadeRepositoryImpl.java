package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Parcelamento;
import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.factory.ContabilidadeFactory;
import br.com.accounting.core.repository.ContabilidadeRepository;
import br.com.accounting.core.service.impl.ContabilidadeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.accounting.core.util.Utils.SEPARADOR;

@Repository
public class ContabilidadeRepositoryImpl extends GenericRepository<Contabilidade> implements ContabilidadeRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ContabilidadeServiceImpl.class);

    @Autowired
    private String diretorio;

    @Override
    public String getArquivoContagem() {
        return diretorio + "\\contabilidades-contagem.txt";
    }

    @Override
    public String getArquivo() {
        return diretorio + "\\contabilidades.csv";
    }

    @Override
    public String criarLinha(Contabilidade contabilidade) {
        LOG.info("[ criarLinha ]");
        LOG.debug("contabilidade: " + contabilidade);

        Parcelamento parcelamento = contabilidade.getParcelamento();
        Integer parcela = null;
        Integer parcelas = null;
        Long codigoParcelamentoPai = null;
        if (parcelamento != null) {
            parcela = parcelamento.getParcela();
            parcelas = parcelamento.getParcelas();
            codigoParcelamentoPai = parcelamento.getCodigoPai();
        }

        SubTipoPagamento subTipoPagamento = contabilidade.getSubTipoPagamento();
        String subTipoPagamentoDescricao = null;
        if (subTipoPagamento != null) {
            subTipoPagamentoDescricao = subTipoPagamento.getDescricao();
        }

        StringBuilder builder = new StringBuilder()
                .append(contabilidade.getCodigo()).append(SEPARADOR)
                .append(contabilidade.getDataLancamentoFormatada()).append(SEPARADOR)
                .append(contabilidade.getVencimentoFormatado()).append(SEPARADOR)
                .append(contabilidade.getTipoPagamento()).append(SEPARADOR)
                .append(subTipoPagamentoDescricao).append(SEPARADOR)
                .append(contabilidade.getTipo()).append(SEPARADOR)
                .append(contabilidade.getGrupo().getDescricao()).append(SEPARADOR)
                .append(contabilidade.getGrupo().getSubGrupo().getDescricao()).append(SEPARADOR)
                .append(contabilidade.getDescricao()).append(SEPARADOR)
                .append(parcela).append(SEPARADOR)
                .append(parcelas).append(SEPARADOR)
                .append(codigoParcelamentoPai).append(SEPARADOR)
                .append(contabilidade.getCategoria()).append(SEPARADOR)
                .append(contabilidade.getValor()).append(SEPARADOR)
                .append(contabilidade.getStatus());

        return builder.toString();
    }

    @Override
    public List<Contabilidade> criarRegistros(List<String> linhas) throws ParseException {
        LOG.info("[ criarRegistros ]");
        LOG.debug("linhas: " + linhas);

        List<Contabilidade> contabilidadeList = new ArrayList<>();

        for (String linha : linhas) {
            Contabilidade contabilidade = criarContabilidade(linha);
            LOG.debug("contabilidade: " + contabilidade);
            contabilidadeList.add(contabilidade);
        }

        return contabilidadeList;
    }

    private Contabilidade criarContabilidade(String linha) throws ParseException {
        LOG.debug("[ criarContabilidade ]");
        LOG.debug("linha: " + linha);

        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        return ContabilidadeFactory
                .begin()
                .withCodigo(registro.get(0))
                .withDataLancamento(registro.get(1))
                .withVencimento(registro.get(2))
                .withTipoPagamento(registro.get(3))
                .withSubTipoPagamento(registro.get(4))
                .withTipo(registro.get(5))
                .withGrupoSubGrupo(registro.get(6), registro.get(7))
                .withDescricao(registro.get(8))
                .withParcelamento(registro.get(9), registro.get(10), registro.get(11))
                .withCategoria(registro.get(12))
                .withValor(registro.get(13))
                .withStatus(registro.get(14))
                .build();
    }
}
