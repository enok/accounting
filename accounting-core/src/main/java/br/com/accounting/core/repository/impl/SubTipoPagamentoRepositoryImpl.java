package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.SubTipoPagamento;
import br.com.accounting.core.repository.SubTipoPagamentoRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SubTipoPagamentoRepositoryImpl extends GenericRepository<SubTipoPagamento> implements SubTipoPagamentoRepository {
    private static final String ARQUIVO_SUBTIPO_PAGAMENTOS_CONTAGEM = DIRETORIO + "\\subTipoPagamentos-contagem.txt";
    private static final String ARQUIVO_SUBTIPO_PAGAMENTOS = DIRETORIO + "\\subTipoPagamentos.csv";

    @Override
    public String getArquivoContagem() {
        return ARQUIVO_SUBTIPO_PAGAMENTOS_CONTAGEM;
    }

    @Override
    protected String getArquivo() {
        return ARQUIVO_SUBTIPO_PAGAMENTOS;
    }

    @Override
    protected String criarLinha(SubTipoPagamento subTipoPagamento) {

        StringBuilder builder = new StringBuilder()
                .append(subTipoPagamento.getCodigo()).append(SEPARADOR)
                .append(subTipoPagamento.getDescricao()).append("\n");

        return builder.toString();
    }
}
