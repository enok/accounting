package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.Conta;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.repository.ContaRepository;
import br.com.accounting.core.service.ContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContaServiceImpl implements ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Override
    public Long salvar(final Conta conta) throws ServiceException {
        log.debug("[ salvar ]");
        log.debug("conta: {}", conta);

        try {
            setaProximoCodigo(conta);
            contaRepository.salvar(conta);
        }
        catch (RepositoryException e) {
            String message = "Não foi possível salvar: a conta";
            log.error(message, e);
            throw new ServiceException(message, e);
        }

        return conta.codigo();
    }

    @Override
    public Conta buscarPorNomeDescricao(final String nome, final String descricao) throws ServiceException {
        log.debug("[ buscarPorNomeDescricao ]");
        log.debug("nome: {}", nome);
        log.debug("descricao: {}", descricao);

        Conta conta = null;
        try {
            List<Conta> contas = contaRepository.buscarRegistros();
            conta = contaRepository.filtrarPorNomeDescricao(contas, nome, descricao);
        }
        catch (RepositoryException e) {
            String message = "Não foi possível buscar a conta.";
            log.error(message, e);
            throw new ServiceException(message, e);
        }

        return conta;
    }

    private void setaProximoCodigo(Conta conta) throws RepositoryException {
        log.trace("[ setaProximoCodigo ]");
        log.trace("entity: {}", conta);

        Long proximoCodigo = contaRepository.proximoCodigo();
        contaRepository.incrementarCodigo(proximoCodigo);
        conta.codigo(proximoCodigo);
    }
}
