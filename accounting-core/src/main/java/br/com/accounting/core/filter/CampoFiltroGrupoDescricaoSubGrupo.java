package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampoFiltroGrupoDescricaoSubGrupo implements CampoFiltro<SubGrupo, Grupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroGrupoDescricaoSubGrupoDescricao.class);

    private String descricao;

    public CampoFiltroGrupoDescricaoSubGrupo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<SubGrupo> filtrar(List<Grupo> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        Set<SubGrupo> subGrupos = entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .map(c -> new SubGrupo(c.getSubGrupo().getCodigo(), c.getSubGrupo().getDescricao()))
                .collect(Collectors.toSet());

        return new ArrayList<>(subGrupos);
    }
}
