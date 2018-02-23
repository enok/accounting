package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Contabilidade;
import br.com.accounting.core.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroContabilidadeDescricao implements CampoFiltro<Contabilidade, Contabilidade> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroContabilidadeDescricao.class);

    private String descricao;

    public CampoFiltroContabilidadeDescricao() {
    }

    public CampoFiltroContabilidadeDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<Contabilidade> filtrar(List<Contabilidade> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contabilidade> ordenar(List<Contabilidade> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getDescricao).reversed())
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Contabilidade::getDescricao))
                        .collect(Collectors.toList());
        }
    }
}
