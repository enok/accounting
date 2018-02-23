package br.com.accounting.core.filter;

import br.com.accounting.core.entity.Grupo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CampoFiltroGrupoDescricaoSubGrupoDescricao implements CampoFiltro<Grupo, Grupo> {
    private static final Logger LOG = LoggerFactory.getLogger(CampoFiltroGrupoDescricaoSubGrupoDescricao.class);

    private String descricaoGrupo;
    private String descricaoSubGrupo;

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
}
