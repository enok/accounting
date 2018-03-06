package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public abstract class GenericRepository<T> implements Repository<T> {
    private static final Logger LOG = LoggerFactory.getLogger(GenericRepository.class);

    @Override
    public Long proximoCodigo() {
        LOG.info("[ proximoCodigo ]");

        Long proximoCodigo = 1L;

        try {
            List<String> lines = Files.readAllLines(Paths.get(getArquivoContagem()));
            proximoCodigo = Long.valueOf(lines.get(0));
        }
        catch (Exception e) {
            String message = "Nao foi possivel ler as linhas do arquivo: " + getArquivoContagem();
            LOG.warn(message, e);
        }

        return proximoCodigo;
    }

    @Override
    public void incrementarCodigo(Long proximoCodigo) throws RepositoryException {
        LOG.info("[ incrementarCodigo ]");
        LOG.debug("proximoCodigo: " + proximoCodigo);

        try {
            String linha = String.valueOf(++proximoCodigo);
            Files.write(Paths.get(getArquivoContagem()), linha.getBytes(), CREATE);
        }
        catch (Exception e) {
            String message = "Nao foi possivel incrementar o codigo: " + proximoCodigo;
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public Long salvar(T entity) throws RepositoryException {
        LOG.info("[ salvar ]");
        LOG.debug("entity: " + entity);

        String linha = null;

        try {
            setaProximoCodigo((Entity) entity);
            linha = criarLinha(entity);
            String caminhoArquivo = getArquivo();
            Files.write(Paths.get(caminhoArquivo), Arrays.asList(linha), StandardCharsets.UTF_8, APPEND, CREATE);
            return ((Entity) entity).getCodigo();
        }
        catch (Exception e) {
            String message = "Nao foi possivel salvar a linha: " + linha;
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void atualizar(List<T> oldEntitiesList, List<T> newEntitiesList) throws RepositoryException {
        LOG.info("[ atualizar ]");
        LOG.debug("oldEntitiesList: " + oldEntitiesList);
        LOG.debug("newEntitiesList: " + newEntitiesList);

        try {
            List<String> oldLines = criarLinhas(oldEntitiesList);
            List<String> newLines = criarLinhas(newEntitiesList);
            LOG.debug("oldLines: " + oldLines);
            LOG.debug("newLines: " + newLines);

            String caminhoArquivo = getArquivo();
            Path path = Paths.get(caminhoArquivo);

            for (int i = 0; i < oldLines.size(); i++) {
                writeLine(path, oldLines.get(i), newLines.get(i));
            }
        }
        catch (Exception e) {
            String message = "Nao foi possivel atualizar a linha";
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public List<T> buscarRegistros() throws RepositoryException {
        LOG.info("[ buscarRegistros ]");

        try {
            List<String> linhas = Files.readAllLines(Paths.get(getArquivo()));
            LOG.debug("linhas: " + linhas);
            return criarRegistros(linhas);
        }
        catch (Exception e) {
            String message = "Nao foi possivel buscar os registros";
            LOG.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    private void setaProximoCodigo(Entity entity) throws RepositoryException {
        LOG.debug("[ setaProximoCodigo ]");
        LOG.debug("entity: " + entity);

        Long proximoCodigo = proximoCodigo();
        incrementarCodigo(proximoCodigo);
        entity.withCodigo(proximoCodigo);
    }

    private List<String> criarLinhas(List<T> entityList) {
        List<String> linhas = new ArrayList<>();
        for (T entity : entityList) {
            linhas.add(criarLinha(entity));
        }
        return linhas;
    }

    private void writeLine(Path path, String oldLine, String newLine) throws IOException {
        Stream<String> lines = Files.lines(path);
        List<String> replaced = lines
                .map(line -> line.replaceAll(oldLine, newLine))
                .collect(Collectors.toList());
        Files.write(path, replaced);
        lines.close();
    }

    public abstract String getArquivoContagem();

    public abstract String getArquivo();

    public abstract String criarLinha(T entity);

    public abstract List<T> criarRegistros(List<String> linhas) throws ParseException;
}
