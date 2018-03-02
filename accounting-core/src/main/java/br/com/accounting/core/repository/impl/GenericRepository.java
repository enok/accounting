package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public abstract class GenericRepository<T> {
    private static final Logger LOG = LoggerFactory.getLogger(GenericRepository.class);

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
        LOG.info("[ incrementarCodigo ] proximoCodigo: " + proximoCodigo);

        try {
            String linha = String.valueOf(++proximoCodigo);
            Files.write(Paths.get(getArquivoContagem()), linha.getBytes(), CREATE);
        } catch (Exception e) {
            String message = "Nao foi possivel incrementar o codigo: " + proximoCodigo;
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    public Long salvar(T entity) throws RepositoryException {
        LOG.info("[ salvar ] entity: " + entity);

        String linha = null;

        try {
            setaProximoCodigo((Entity) entity);
            linha = criarLinha(entity);
            String caminhoArquivo = getArquivo();
            Files.write(Paths.get(caminhoArquivo), Arrays.asList(linha), StandardCharsets.UTF_8, APPEND, CREATE);
            return ((Entity) entity).getCodigo();
        } catch (Exception e) {
            String message = "Nao foi possivel salvar a linha: " + linha;
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    public List<T> buscarRegistros() throws RepositoryException {
        LOG.info("[ buscarRegistros ]");

        try {
            List<String> linhas = Files.readAllLines(Paths.get(getArquivo()));
            LOG.debug("linhas: " + linhas);
            return criarRegistros(linhas);
        } catch (Exception e) {
            String message = "Nao foi possivel buscar os registros";
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    private void setaProximoCodigo(Entity entity) throws RepositoryException {
        LOG.debug("[ setaProximoCodigo ] entity: " + entity);

        Long proximoCodigo = proximoCodigo();
        incrementarCodigo(proximoCodigo);
        entity.withCodigo(proximoCodigo);
    }

    public abstract String getArquivoContagem();

    public abstract String getArquivo();

    public abstract String criarLinha(T entity);

    public abstract List<T> criarRegistros(List<String> linhas) throws ParseException;
}
