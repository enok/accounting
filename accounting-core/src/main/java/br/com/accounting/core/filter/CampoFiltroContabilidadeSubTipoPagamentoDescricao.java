package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.SubTipoPagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroContabilidadeSubTipoPagamentoDescricao implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeSubTipoPagamentoDescricao.class);

    private String descricao;

    public CampoFiltroContabilidadeSubTipoPagamentoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> descricaoEhIgual(c))
                .collect(Collectors.toList());
    }

    private boolean descricaoEhIgual(Contabilidade c) {
        SubTipoPagamento subTipoPagamento = c.getSubTipoPagamento();
        if (subTipoPagamento == null) {
            return false;
        }
        return subTipoPagamento.getDescricao().equals(descricao);
    }
}
