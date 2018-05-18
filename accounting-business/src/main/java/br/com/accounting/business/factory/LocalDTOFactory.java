package br.com.accounting.business.factory;

import br.com.accounting.business.dto.LocalDTO;
import br.com.accounting.commons.factory.GenericDTOFactory;
import br.com.accounting.core.entity.Local;

import static br.com.accounting.commons.util.Utils.isBlankOrNull;

public final class LocalDTOFactory extends GenericDTOFactory<LocalDTO, Local> {
    private static LocalDTOFactory factory;

    private LocalDTO dto;

    private LocalDTOFactory() {
        dto = new LocalDTO();
    }

    public static LocalDTOFactory create() {
        return new LocalDTOFactory();
    }

    @Override
    public LocalDTOFactory begin() {
        factory = new LocalDTOFactory();
        return factory;
    }

    @Override
    public LocalDTOFactory preencherCampos(Local local) {
        withCodigo(local.codigo());
        withNome(local.nome());
        return this;
    }

    @Override
    public LocalDTO build() {
        return dto;
    }

    public LocalDTOFactory withCodigo(Long codigo) {
        if (codigo != null) {
            dto.codigo(codigo.toString());
        }
        return this;
    }

    public LocalDTOFactory withNome(String nome) {
        if (!isBlankOrNull(nome)) {
            dto.nome(nome);
        }
        return this;
    }
}
