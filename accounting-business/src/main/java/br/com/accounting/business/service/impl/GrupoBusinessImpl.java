package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.GrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.GrupoDTOFactory;
import br.com.accounting.business.service.GrupoBusiness;
import br.com.accounting.core.entity.Grupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.GrupoFactory;
import br.com.accounting.core.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.*;

@Service
public class GrupoBusinessImpl implements GrupoBusiness {
    @Autowired
    private GrupoService service;

    @History
    @Override
    public Long criar(final GrupoDTO dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros, false);

            Grupo entity = GrupoFactory
                    .begin()
                    .withNome(dto.nome())
                    .withDescricao(dto.descricao())
                    .withSubGrupos(dto.subGrupos())
                    .build();

            validaRegistroDuplicado(entity);

            return service.salvar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível criar o grupo.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void atualizar(final GrupoDTO dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros, true);

            Grupo entity = GrupoFactory
                    .begin()
                    .withCodigo(dto.codigo())
                    .withNome(dto.nome())
                    .withDescricao(dto.descricao())
                    .withSubGrupos(dto.subGrupos())
                    .build();

            service.atualizar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o grupo.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void excluir(final GrupoDTO dto) throws BusinessException {
        try {
            Grupo entity = criarEntity(dto);
            service.deletar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível excluir o grupo.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public GrupoDTO buscarPorId(final Long codigo) throws BusinessException {
        try {
            Grupo grupo = service.buscarPorCodigo(codigo);
            return criarDTOEntity(GrupoDTOFactory.create(), grupo);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o grupo por id.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public List<GrupoDTO> buscarTodos() throws BusinessException {
        List<GrupoDTO> entitiesDTO;
        try {
            List<Grupo> entities = service.buscarTodos();
            entitiesDTO = criarListaEntitiesDTO(GrupoDTOFactory.create(), entities);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar os grupos.";
            throw new BusinessException(message, e);
        }
        return entitiesDTO;
    }

    @Override
    public void validarEntrada(final GrupoDTO dto, final List<String> erros, final boolean atualizacao) throws MissingFieldException {
        String msg = "O campo %s é obrigatório.";

        if (atualizacao) {
            if (isBlank(dto.codigo())) {
                erros.add(format(msg, "código"));
            }
        }
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        if (isEmpty(dto.subGrupos())) {
            erros.add(format(msg, "subGrupos"));
        }

        conferirErros(erros);
    }

    @Override
    public void validaRegistroDuplicado(final Grupo entity) throws ServiceException, DuplicatedRegistryException {
        Grupo grupoBuscada = service.buscarPorNome(entity.nome());

        if (entity.equals(grupoBuscada)) {
            throw new DuplicatedRegistryException("Grupo duplicado.");
        }
    }

    @Override
    public Grupo criarEntity(final GrupoDTO entity) {
        return GrupoFactory
                .begin()
                .withCodigo(entity.codigo())
                .withNome(entity.nome())
                .withDescricao(entity.descricao())
                .withSubGrupos(entity.subGrupos())
                .build();
    }
}
