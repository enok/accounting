package br.com.accounting.core.service;

import br.com.accounting.core.entity.GrupoEntity;

import java.util.List;

public interface GrupoService {
    Long salvarGrupo(GrupoEntity grupoEntity);

    List<GrupoEntity> buscarGrupos();
}
