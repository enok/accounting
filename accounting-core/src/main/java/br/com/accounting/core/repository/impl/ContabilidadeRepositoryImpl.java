package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.*;
import br.com.accounting.core.repository.ContabilidadeRepository;
import br.com.accounting.core.service.impl.ContabilidadeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ContabilidadeRepositoryImpl extends GenericRepository<Contabilidade> implements ContabilidadeRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ContabilidadeServiceImpl.class);

    private static final String ARQUIVO_REGISTROS_CONTAGEM = DIRETORIO + "\\registros-contagem.csv";
    private static final String ARQUIVO_REGISTROS = DIRETORIO + "\\registros.csv";

    @Override
    public String getArquivoContagem() {
        return ARQUIVO_REGISTROS_CONTAGEM;
    }

    @Override
    public String getArquivo() {
        return ARQUIVO_REGISTROS;
    }

    @Override
    public String criarLinha(Contabilidade contabilidade) {
        LOG.info("[ criarLinha ] contabilidade: " + contabilidade);

        Parcelamento parcelamento = contabilidade.getParcelamento();
        Integer parcela = null;
        Integer parcelas = null;
        if (parcelamento != null) {
            parcela = parcelamento.getParcela();
            parcelas = parcelamento.getParcelas();
        }

        SubTipoPagamento subTipoPagamento = contabilidade.getSubTipoPagamento();
        String subTipoPagamentoDescricao = null;
        if (subTipoPagamento != null) {
            subTipoPagamentoDescricao = subTipoPagamento.getDescricao();
        }

        StringBuilder builder = new StringBuilder()
                .append(contabilidade.getCodigo()).append(SEPARADOR)
                .append(contabilidade.getVencimentoFormatado()).append(SEPARADOR)
                .append(contabilidade.getTipoPagamento()).append(SEPARADOR)
                .append(subTipoPagamentoDescricao).append(SEPARADOR)
                .append(contabilidade.getTipo()).append(SEPARADOR)
                .append(contabilidade.getGrupo()).append(SEPARADOR)
                .append(contabilidade.getSubGrupo()).append(SEPARADOR)
                .append(contabilidade.getDescricao()).append(SEPARADOR)
                .append(parcela).append(SEPARADOR)
                .append(parcelas).append(SEPARADOR)
                .append(contabilidade.getCategoria()).append(SEPARADOR)
                .append(contabilidade.getValor()).append(SEPARADOR)
                .append(contabilidade.getStatus()).append("\n");

        return builder.toString();
    }

    @Override
    public List<Contabilidade> criarRegistros(List<String> linhas) {
        LOG.info("[ criarRegistros ] linhas: " + linhas);

        List<Contabilidade> contabilidadeList = new ArrayList<>();

        for (String linha : linhas) {
            Contabilidade contabilidade = criarContabilidade(linha);
            LOG.debug("contabilidade: " + contabilidade);
            contabilidadeList.add(contabilidade);
        }

        return contabilidadeList;
    }

    private Contabilidade criarContabilidade(String linha) {
        List<String> registro = Stream
                .of(linha)
                .map(w -> w.split(SEPARADOR)).flatMap(Arrays::stream)
                .collect(Collectors.toList());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Long codigo = Long.valueOf(registro.get(0));
        LocalDate vencimento = LocalDate.parse(registro.get(1), formatter);
        TipoPagamento tipoPagamento = TipoPagamento.valueOf(registro.get(2));

        String subTipoPagamentoDescricao = registro.get(3);
        SubTipoPagamento subTipoPagamento = null;
        if ((subTipoPagamentoDescricao != null) && !subTipoPagamentoDescricao.equals("null")) {
            subTipoPagamento = new SubTipoPagamento()
                    .withDescricao(subTipoPagamentoDescricao);
        }

        Tipo tipo = Tipo
                .valueOf(registro.get(4));
        String grupo = registro.get(5);
        String subGrupo = registro.get(6);
        String descricao = registro.get(7);

        Parcelamento parcelamento = null;
        String parcela = registro.get(8);
        if ((parcela != null) && !parcela.equals("null")) {
            parcelamento = new Parcelamento()
                    .withParcela(Integer.valueOf(parcela))
                    .withParcelas(Integer.valueOf(registro.get(9)));
        }
        Categoria categoria = Categoria
                .valueOf(registro.get(10));
        Double valor = Double.valueOf(registro.get(11));
        Status status = Status
                .valueOf(registro.get(12));

        Contabilidade contabilidade = new Contabilidade()
                .withCodigo(codigo)
                .withVencimento(vencimento)
                .withTipoPagamento(tipoPagamento)
                .withSubTipoPagamento(subTipoPagamento)
                .withTipo(tipo)
                .withGrupo(grupo)
                .withSubGrupo(subGrupo)
                .withDescricao(descricao)
                .withParcelamento(parcelamento)
                .withCategoria(categoria)
                .withValor(valor)
                .withStatus(status);

        return contabilidade;
    }

}
