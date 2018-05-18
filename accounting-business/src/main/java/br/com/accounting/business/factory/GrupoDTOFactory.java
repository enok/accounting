package br.com.accounting.business.factory;

import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.commons.factory.GenericDTOFactory;
import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.entity.SubGrupo;

import java.util.List;

import static br.com.accounting.commons.util.Utils.isBlankOrNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class GrupoDTOFactory extends GenericDTOFactory<GrupoDTO, Grupo> {
    private static GrupoDTOFactory factory;

    private GrupoDTO dto;

    private GrupoDTOFactory() {
        dto = new GrupoDTO();
    }

    public static GrupoDTOFactory create() {
        return new GrupoDTOFactory();
    }

    @Override
    public GenericDTOFactory begin() {
        factory = new GrupoDTOFactory();
        return factory;
    }

    @Override
    public GenericDTOFactory preencherCampos(Grupo entity) {
        withCodigo(entity.codigo());
        withNome(entity.nome());
        withDescricao(entity.descricao());
        withSubGrupos(entity.subGrupos());
        return this;
    }

    @Override
    public GrupoDTO build() {
        return dto;
    }

    public GrupoDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            dto.codigo(codigo.toString());
        }
        return this;
    }

    public GrupoDTOFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            dto.nome(nome);
        }
        return this;
    }

    public GrupoDTOFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            dto.descricao(descricao);
        }
        return this;
    }

    public GrupoDTOFactory withSubGrupos(List<SubGrupo> subGrupos) {
        if (!isEmpty(subGrupos)) {
            for (SubGrupo subGrupo : subGrupos) {
                withSubGrupo(subGrupo);
            }
        }
        return this;
    }

    public GrupoDTOFactory withSubGrupo(String subGrupo) {
        if (!isBlankOrNull(subGrupo)) {
            dto.addSubGrupo(subGrupo);
        }
        return this;
    }

    private GrupoDTOFactory withSubGrupo(SubGrupo subGrupo) {
        if (subGrupo != null) {
            withSubGrupo(subGrupo.nome());
        }
        return this;
    }
}
