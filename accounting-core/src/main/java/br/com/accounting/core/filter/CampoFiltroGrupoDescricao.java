package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampoFiltroGrupoDescricao implements CampoFiltro<Grupo, Grupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroGrupoDescricao.class);

    private String descricao;

    public CampoFiltroGrupoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<Grupo> filtrar(List<Grupo> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        Set<Grupo> grupos = entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .collect(Collectors.toSet());

        return new ArrayList<>(grupos);
    }
}
