package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.SubGrupo;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.SubGrupoRepository;
import br.com.accounting.core.service.SubGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubGrupoServiceImpl implements SubGrupoService {
    @Autowired
    private SubGrupoRepository subGrupoRepository;

    @Override
    public Long salvar(final SubGrupo subGrupo) throws ServiceException {
        try {
            setarProximoCodigo(subGrupoRepository, subGrupo);
            subGrupoRepository.salvar(subGrupo);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar o subGrupo.";
            throw new ServiceException(message, e);
        }
        return subGrupo.codigo();
    }

    @Override
    public void atualizar(final SubGrupo subGrupo) throws ServiceException {
        try {
            subGrupoRepository.atualizar(subGrupo);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar o subGrupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public void deletar(final SubGrupo subGrupo) throws ServiceException {
        try {
            subGrupoRepository.deletar(subGrupo);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar o subGrupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public SubGrupo buscarPorCodigo(final Long codigo) throws ServiceException {
        return buscarPorCodigo(subGrupoRepository, codigo);
    }

    @Override
    public SubGrupo buscarPorNome(final String nome) throws ServiceException {
        try {
            List<SubGrupo> subGrupos = subGrupoRepository.buscarRegistros();
            return subGrupoRepository.filtrarPorNome(subGrupos, nome);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar o subGrupo.";
            throw new ServiceException(message, e);
        }
    }

    @Override
    public List<SubGrupo> buscarTodos() throws ServiceException {
        try {
            List<SubGrupo> subGrupos = subGrupoRepository.buscarRegistros();
            subGrupoRepository.ordenarPorNome(subGrupos);
            return subGrupos;
        }
        catch (Exception e) {
            String message = "Não foi possível buscar os subGrupos.";
            throw new ServiceException(message, e);
        }
    }
}
