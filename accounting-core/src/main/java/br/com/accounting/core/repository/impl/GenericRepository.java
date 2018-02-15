package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.entity.Registro;
import br.com.accounting.core.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public abstract class GenericRepository<T> {
    private static final Logger LOG = LoggerFactory.getLogger(GenericRepository.class);

    protected static final String SEPARADOR = ";";
    public static final String DIRETORIO = "arquivos";

    public Long proximoCodigo() {
        LOG.info("[ proximoCodigo ]");

        Long proximoCodigo = 1L;

        try {
            List<String> lines = Files.readAllLines(Paths.get(getArquivoContagem()));
            proximoCodigo = Long.valueOf(lines.get(0));
        } catch (Exception e) {
            String message = "Nao foi possivel ler as linhas do arquivo: " + getArquivoContagem();
            LOG.warn(message, e);
        }

        return proximoCodigo;
    }

    public void incrementarCodigo(Long proximoCodigo) throws RepositoryException {
        LOG.info("[ incrementarCodigo ]");

        try {
            String linha = String.valueOf(++proximoCodigo);
            Files.write(Paths.get(getArquivoContagem()), linha.getBytes(), CREATE);
        } catch (Exception e) {
            String message = "Nao foi possivel incrementar o codigo: " + proximoCodigo;
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    public void salvar(T entity) throws RepositoryException {
        LOG.info("[ salvar ] entity: " + entity);

        String linha = null;

        try {
            setaProximoCodigo((Entity) entity);
            linha = criarLinha(entity);
            String caminhoArquivo = getArquivo();
            Files.write(Paths.get(caminhoArquivo), linha.getBytes(), APPEND, CREATE);
        } catch (Exception e) {
            String message = "Nao foi possivel salvar a linha: " + linha;
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    private void setaProximoCodigo(Entity entity) throws RepositoryException {
        Long proximoCodigo = proximoCodigo();
        incrementarCodigo(proximoCodigo);
        entity.withCodigo(proximoCodigo);
    }

    protected abstract String getArquivoContagem();

    protected abstract String getArquivo();

    protected abstract String criarLinha(T entity);
}
