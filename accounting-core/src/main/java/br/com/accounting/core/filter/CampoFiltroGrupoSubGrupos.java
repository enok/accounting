package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.Order;
import br.com.accounting.core.entity.SubGrupo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CampoFiltroGrupoSubGrupos implements CampoFiltro<SubGrupo, Grupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroSubGrupo.class);

    private String descricao;

    public CampoFiltroGrupoSubGrupos() {
    }

    public CampoFiltroGrupoSubGrupos(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<SubGrupo> filtrar(List<Grupo> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        return entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .map(c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubGrupo> ordenar(List<Grupo> entities, Order order) {
        LOG.info("[ ordenar ]");
        LOG.debug("entities: " + entities);
        LOG.debug("order: " + order);

        switch (order) {
            case DESC:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getSubGrupoDescricao).reversed())
                        .map(c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao()))
                        .collect(Collectors.toList());
            case ASC:
            default:
                return entities
                        .stream()
                        .sorted(Comparator.comparing(Grupo::getSubGrupoDescricao))
                        .map(c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao()))
                        .collect(Collectors.toList());
        }
    }
}
