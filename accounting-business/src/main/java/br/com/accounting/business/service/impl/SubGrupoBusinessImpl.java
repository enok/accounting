package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.dto.SubGrupoDTO;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.DuplicatedRegistryException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.factory.ContaDTOFactory;
import br.com.accounting.business.factory.SubGrupoDTOFactory;
import br.com.accounting.business.service.SubGrupoBusiness;
import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.factory.SubGrupoFactory;
import br.com.accounting.core.service.SubGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SubGrupoBusinessImpl implements SubGrupoBusiness {
    @Autowired
    private SubGrupoService service;

    @History
    @Override
    public Long criar(final SubGrupoDTO dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros, false);

            SubGrupo entity = SubGrupoFactory
                    .begin()
                    .withNome(dto.nome())
                    .withDescricao(dto.descricao())
                    .build();

            validaRegistroDuplicado(entity);

            return service.salvar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível criar o subGrupo.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void atualizar(final SubGrupoDTO dto) throws BusinessException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros, true);

            SubGrupo entity = SubGrupoFactory
                    .begin()
                    .withCodigo(dto.codigo())
                    .withNome(dto.nome())
                    .withDescricao(dto.descricao())
                    .build();

            service.atualizar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o subGrupo.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void excluir(final SubGrupoDTO dto) throws BusinessException {
        try {
            SubGrupo entity = criarEntity(dto);
            service.deletar(entity);
        }
        catch (Exception e) {
            String message = "Não foi possível excluir o subGrupo.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public SubGrupoDTO buscarPorId(final Long codigo) throws BusinessException {
        try {
            SubGrupo subGrupo = service.buscarPorCodigo(codigo);
            return criarDTOEntity(SubGrupoDTOFactory.create(), subGrupo);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o subGrupo por id.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public List<SubGrupoDTO> buscarTodos() throws BusinessException {
        List<SubGrupoDTO> entitiesDTO;
        try {
            List<SubGrupo> entities = service.buscarTodos();
            entitiesDTO = criarListaEntitiesDTO(SubGrupoDTOFactory.create(), entities);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar os subGrupos.";
            throw new BusinessException(message, e);
        }
        return entitiesDTO;
    }

    @Override
    public void validarEntrada(final SubGrupoDTO dto, final List<String> erros, final boolean atualizacao) throws MissingFieldException {
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

        conferirErros(erros);
    }

    @Override
    public void validaRegistroDuplicado(final SubGrupo entity) throws ServiceException, DuplicatedRegistryException {
        SubGrupo subGrupoBuscada = service.buscarPorNome(entity.nome());

        if (entity.equals(subGrupoBuscada)) {
            throw new DuplicatedRegistryException("SubGrupo duplicado.");
        }
    }

    @Override
    public SubGrupo criarEntity(final SubGrupoDTO entity) {
        return SubGrupoFactory
                .begin()
                .withCodigo(entity.codigo())
                .withNome(entity.nome())
                .withDescricao(entity.descricao())
                .build();
    }
}
