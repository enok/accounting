package br.com.accounting.grupo.business.impl;

import br.com.accounting.commons.entity.Grupo;
import br.com.accounting.commons.exception.*;
import br.com.accounting.commons.service.impl.GenericAbstractBusiness;
import br.com.accounting.grupo.business.GrupoBusiness;
import br.com.accounting.commons.dto.GrupoDTO;
import br.com.accounting.grupo.factory.GrupoDTOFactory;
import br.com.accounting.grupo.factory.GrupoFactory;
import br.com.accounting.grupo.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class GrupoBusinessImpl extends GenericAbstractBusiness<GrupoDTO, Grupo> implements GrupoBusiness {
    private GrupoService service;

    @Autowired
    public GrupoBusinessImpl(final GrupoService service) {
        super(service, GrupoDTOFactory.create());
        this.service = service;
    }

    @Override
    public void validarEntrada(final GrupoDTO dto, final List<String> erros) throws MissingFieldException {
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        if (isEmpty(dto.subGrupos())) {
            erros.add(format(msg, "subGrupos"));
        }
        conferirErrosCamposObrigatorios(erros);
    }

    @Override
    public void validarEntradaUpdate(final GrupoDTO dto, final Grupo entity, final List<String> erros) throws ValidationException {
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final Grupo entity) throws StoreException, ParseException, DuplicatedRegistryException {
        Grupo entityBuscada = service.buscarPorNome(entity.nome());

        if (entity.equals(entityBuscada)) {
            throw new DuplicatedRegistryException("Grupo duplicado.");
        }
    }

    @Override
    public Grupo criarEntity(final GrupoDTO dto) {
        return GrupoFactory
                .begin()
                .withCodigo(dto.codigo())
                .withNome(dto.nome())
                .withDescricao(dto.descricao())
                .withSubGrupos(dto.subGrupos())
                .build();
    }

    @Override
    protected Grupo criarEntity(GrupoDTO dto, Grupo entityBuscado) {
        return criarEntity(dto);
    }

    @Override
    public GrupoDTO buscarPorNome(final String nome) throws StoreException, BusinessException, GenericException {
        try {
            Grupo entity = service.buscarPorNome(nome);
            if (entity == null) {
                throw new BusinessException("Registro inexistente.");
            }
            return criarDTO(dtoFactory, entity);
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao buscar por nome.", e);
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }
}
