package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampoFiltroGrupoDescricaoSubGrupoDescricao implements CampoFiltro<Grupo, Grupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroGrupoDescricaoSubGrupoDescricao.class);

    private String descricaoGrupo;
    private String descricaoSubGrupo;

    public CampoFiltroGrupoDescricaoSubGrupoDescricao() {
    }

    public CampoFiltroGrupoDescricaoSubGrupoDescricao(String descricaoGrupo, String descricaoSubGrupo) {
        this.descricaoGrupo = descricaoGrupo;
        this.descricaoSubGrupo = descricaoSubGrupo;
    }

    @Override
    public List<Grupo> filtrar(List<Grupo> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        Set<Grupo> subTipoPagamentos = entities
                .stream()
                .filter(c -> descricaoEhIgual(c))
                .collect(Collectors.toSet());

        return new ArrayList<>(subTipoPagamentos);
    }

    private boolean descricaoEhIgual(Grupo c) {
        return c.getDescricao().equals(descricaoGrupo) &&
                c.getSubGrupo().getDescricao().equals(descricaoSubGrupo);
    }

    @Override
    public List<Grupo> ordenar(List<Grupo> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getDescricao).reversed()
                                .thenComparing(Comparator.comparing(Grupo::getSubGrupoDescricao).reversed()))
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getDescricao)
                                .thenComparing(Comparator.comparing(Grupo::getSubGrupoDescricao)))
                        .collect(Collectors.toList());
        }
    }
}
