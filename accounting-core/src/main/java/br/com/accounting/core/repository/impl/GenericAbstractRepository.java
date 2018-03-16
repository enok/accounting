package br.com.accounting.core.repository.impl;

import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.Arrays.asList;

@Slf4j
public abstract class GenericAbstractRepository<T> implements GenericRepository<T> {

    @Override
    public Long proximoCodigo() {
        log.debug("[ proximoCodigo ]");

        Long proximoCodigo = null;

        try {
            Path path = buscarArquivoContagem();
            List<String> lines = readAllLines(path);
            proximoCodigo = buscarProximoCodigo(lines);
        }
        catch (Exception e) {
            String message = "Nao foi possivel ler as linhas do arquivo: " + getArquivoContagem();
            log.warn(message, e);
        }

        log.trace("proximoCodigo: {}", proximoCodigo);

        return proximoCodigo;
    }

    @Override
    public void incrementarCodigo(final Long codigo) throws RepositoryException {
        log.debug("[ incrementarCodigo ]");
        log.debug("codigo: {}", codigo);

        try {
            Long novoCodigo = codigo;
            String linha = String.valueOf(++novoCodigo);
            write(get(getArquivoContagem()), linha.getBytes(), CREATE);
        }
        catch (Exception e) {
            String message = "Nao foi possivel incrementar o codigo: " + codigo;
            log.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void salvar(final T entity) throws RepositoryException {
        log.debug("[ salvar ]");
        log.debug("entity: {}", entity);

        String linha = null;

        try {
            linha = criarLinha(entity);
            String caminhoArquivo = getArquivo();
            write(get(caminhoArquivo), asList(linha), UTF_8, APPEND, CREATE);
        }
        catch (Exception e) {
            String message = "Nao foi possivel salvar a linha: " + linha;
            log.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

//    @Override
//    public void atualizar(List<T> oldEntitiesList, List<T> newEntitiesList) throws RepositoryException {
//        LOG.info("[ atualizar ]");
//        LOG.debug("oldEntitiesList: " + oldEntitiesList);
//        LOG.debug("newEntitiesList: " + newEntitiesList);
//
//        try {
//            List<String> oldLines = criarLinhas(oldEntitiesList);
//            List<String> newLines = criarLinhas(newEntitiesList);
//            LOG.debug("oldLines: " + oldLines);
//            LOG.debug("newLines: " + newLines);
//
//            String caminhoArquivo = getArquivo();
//            Path path = Paths.get(caminhoArquivo);
//
//            for (int i = 0; i < oldLines.size(); i++) {
//                writeLine(path, oldLines.get(i), newLines.get(i));
//            }
//        }
//        catch (Exception e) {
//            String message = "Nao foi possivel atualizar a linha";
//            LOG.error(message, e);
//            throw new RepositoryException(message, e);
//        }
//    }

    @Override
    public List<T> buscarRegistros() throws RepositoryException {
        log.debug("[ buscarRegistros ]");

        try {
            Path caminhoArquivo = buscarCaminhoArquivo();
            List<String> linhas = Files.readAllLines(caminhoArquivo);
            log.trace("linhas: " + linhas);
            return criarRegistros(linhas);
        }
        catch (Exception e) {
            String message = "Nao foi possivel buscar os registros";
            log.error(message, e);
            throw new RepositoryException(message, e);
        }
    }

    private Path buscarArquivoContagem() throws IOException {
        String caminhoArquivoContagem = getArquivoContagem();
        return buscarArquivo(caminhoArquivoContagem);
    }

    private Path buscarArquivo(String caminhoArquivo) throws IOException {
        Path path = get(caminhoArquivo);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return path;
    }

    private Long buscarProximoCodigo(List<String> lines) {
        if (CollectionUtils.isEmpty(lines)) {
            return 0L;
        }
        return Long.valueOf(lines.get(0));
    }

    private Path buscarCaminhoArquivo() throws IOException {
        Path caminhoArquivo = Paths.get(getArquivo());
        if (!Files.exists(caminhoArquivo)) {
            Files.createFile(caminhoArquivo);
        }
        return caminhoArquivo;
    }

//    private void setaProximoCodigo(Entity entity) throws RepositoryException {
//        LOG.debug("[ setaProximoCodigo ]");
//        LOG.debug("entity: " + entity);
//
//        Long proximoCodigo = proximoCodigo();
//        incrementarCodigo(proximoCodigo);
//        entity.withCodigo(proximoCodigo);
//    }
//
//    private List<String> criarLinhas(List<T> entityList) {
//        List<String> linhas = new ArrayList<>();
//        for (T entity : entityList) {
//            linhas.add(criarLinha(entity));
//        }
//        return linhas;
//    }
//
//    private void writeLine(Path path, String oldLine, String newLine) throws IOException {
//        Stream<String> lines = Files.lines(path);
//        List<String> replaced = lines
//                .map(line -> line.replaceAll(oldLine, newLine))
//                .collect(Collectors.toList());
//        Files.write(path, replaced);
//        lines.close();
//    }

    public abstract String getArquivo();

    public abstract String getArquivoContagem();

    public abstract String criarLinha(T entity);

    public abstract List<T> criarRegistros(List<String> linhas) throws ParseException;
}
