package br.com.accounting.business.factory;

import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.core.entity.SubGrupo;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class SubGrupoDTOFactory extends GenericDTOFactory<SubGrupoDTO, SubGrupo> {
    private static SubGrupoDTOFactory subGrupoDTOFactory;

    private SubGrupoDTO subGrupoDTO;

    private SubGrupoDTOFactory() {
        subGrupoDTO = new SubGrupoDTO();
    }

    public static SubGrupoDTOFactory create() {
        return new SubGrupoDTOFactory();
    }

    @Override
    public GenericDTOFactory begin() {
        subGrupoDTOFactory = new SubGrupoDTOFactory();
        return subGrupoDTOFactory;
    }

    @Override
    public GenericDTOFactory preencherCampos(SubGrupo entity) {
        if (entity == null) {
            subGrupoDTO = null;
            return this;
        }
        withCodigo(entity.codigo());
        withNome(entity.nome());
        withDescricao(entity.descricao());
        return this;
    }

    @Override
    public SubGrupoDTO build() {
        return subGrupoDTO;
    }

    public SubGrupoDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            subGrupoDTO.codigo(codigo.toString());
        }
        return this;
    }

    public SubGrupoDTOFactory withNome(String nome) {
        if (!isBlank(nome)) {
            subGrupoDTO.nome(nome);
        }
        return this;
    }

    public SubGrupoDTOFactory withDescricao(String descricao) {
        if (!isBlank(descricao)) {
            subGrupoDTO.descricao(descricao);
        }
        return this;
    }
}
