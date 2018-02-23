package br.com.accounting.core.filter;

import br.com.accounting.core.entity.SubGrupo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampoFiltroSubGrupoDescricao implements CampoFiltro<SubGrupo, SubGrupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroSubGrupoDescricao.class);

    private String descricao;

    public CampoFiltroSubGrupoDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public List<SubGrupo> filtrar(List<SubGrupo> entities) {
        LOG.info("[ filtrar ]");
        LOG.debug("entities: " + entities);

        Set<SubGrupo> subGrupos = entities
                .stream()
                .filter(c -> c.getDescricao().equals(descricao))
                .collect(Collectors.toSet());

        return new ArrayList<>(subGrupos);
    }
}
