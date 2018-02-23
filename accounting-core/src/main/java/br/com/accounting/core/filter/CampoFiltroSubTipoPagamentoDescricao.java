package br.com.accounting.core.filter;

import br.com.accounting.core.entity.SubTipoPagamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampoFiltroSubTipoPagamentoDescricao implements CampoFiltro<SubTipoPagamento, SubTipoPagamento> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroSubTipoPagamentoDescricao.class);

    private String descricao;

    public CampoFiltroSubTipoPagamentoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<SubTipoPagamento> filtrar(List<SubTipoPagamento> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        Set<SubTipoPagamento> subTipoPagamentos = entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .collect(Collectors.toSet());

        return new ArrayList<>(subTipoPagamentos);
    }
}
