package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Parcelamento;
import br.com.accounting.core.entity.Registro;
import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.repository.RegistroRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RegistroRepositoryImpl extends GenericRepository<Registro> implements RegistroRepository {
    private static final String ARQUIVO_REGISTROS_CONTAGEM = DIRETORIO + "\\registros-contagem.csv";
    private static final String ARQUIVO_REGISTROS = DIRETORIO + "\\registros.csv";

    @Override
    public String getArquivoContagem() {
        return ARQUIVO_REGISTROS_CONTAGEM;
    }

    @Override
    protected String getArquivo() {
        return ARQUIVO_REGISTROS;
    }

    @Override
    protected String criarLinha(Registro registro) {

        Parcelamento parcelamento = registro.getParcelamento();
        Integer parcela = null;
        Integer parcelas = null;
        if (parcelamento != null) {
            parcela = parcelamento.getParcela();
            parcelas = parcelamento.getParcelas();
        }

        SubTipoPagamento subTipoPagamento = registro.getSubTipoPagamento();
        String subTipoPagamentoDescricao = null;
        if (subTipoPagamento != null) {
            subTipoPagamentoDescricao = subTipoPagamento.getDescricao();
        }

        StringBuilder builder = new StringBuilder()
                .append(registro.getCodigo()).append(SEPARADOR)
                .append(registro.getVencimentoFormatado()).append(SEPARADOR)
                .append(registro.getTipoPagamento()).append(SEPARADOR)
                .append(subTipoPagamentoDescricao).append(SEPARADOR)
                .append(registro.getTipo()).append(SEPARADOR)
                .append(registro.getGrupo()).append(SEPARADOR)
                .append(registro.getSubGrupo()).append(SEPARADOR)
                .append(registro.getDescricao()).append(SEPARADOR)
                .append(parcela).append(SEPARADOR)
                .append(parcelas).append(SEPARADOR)
                .append(registro.getCategoria()).append(SEPARADOR)
                .append(registro.getValor()).append(SEPARADOR)
                .append(registro.getStatus()).append("\n");

        return builder.toString();
    }
}
