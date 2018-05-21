package br.com.accounting.subgrupo.factory;

import br.com.accounting.subgrupo.dto.SubGrupoDTO;
import br.com.accounting.commons.factory.GenericDTOFactory;
import br.com.accounting.commons.entity.SubGrupo;

import static br.com.accounting.commons.util.Utils.isBlankOrNull;

public final class SubGrupoDTOFactory extends GenericDTOFactory<SubGrupoDTO, SubGrupo> {
    private static SubGrupoDTOFactory factory;

    private SubGrupoDTO dto;

    private SubGrupoDTOFactory() {
        dto = new SubGrupoDTO();
    }

    public static SubGrupoDTOFactory create() {
        return new SubGrupoDTOFactory();
    }

    @Override
    public GenericDTOFactory begin() {
        factory = new SubGrupoDTOFactory();
        return factory;
    }

    @Override
    public GenericDTOFactory preencherCampos(SubGrupo entity) {
        withCodigo(entity.codigo());
        withNome(entity.nome());
        withDescricao(entity.descricao());
        return this;
    }

    @Override
    public SubGrupoDTO build() {
        return dto;
    }

    public SubGrupoDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            dto.codigo(codigo.toString());
        }
        return this;
    }

    public SubGrupoDTOFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            dto.nome(nome);
        }
        return this;
    }

    public SubGrupoDTOFactory withDescricao(String descricao) {
        if (!isBlankOrNull(descricao)) {
            dto.descricao(descricao);
        }
        return this;
    }
}
