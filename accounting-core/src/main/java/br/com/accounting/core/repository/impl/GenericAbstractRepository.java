package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Entity;
import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.repository.GenericRepository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.Arrays.asList;
import static org.springframework.util.CollectionUtils.isEmpty;

public abstract class GenericAbstractRepository<T> implements GenericRepository<T> {
    public abstract String getArquivo();

    public abstract String getArquivoContagem();

    public abstract String criarLinha(T entity);

    public abstract T criarEntity(final String linha) throws ParseException;

    @Override
    public Long proximoCodigo() throws StoreException {

        List<String> lines;
        final String message = "Não foi possível ler as linhas do arquivo: " + getArquivoContagem();

        try {
            Path path = buscarArquivoContagem();
            lines = readAllLines(path);
        }
        catch (Exception e) {
            throw new StoreException(message, e);
        }

        return buscarProximoCodigo(lines);
    }

    @Override
    public void incrementarCodigo(final Long codigo) throws StoreException, RepositoryException {
        final String message = "Não foi possível incrementar o código: " + codigo;
        String linha;
        try {
            Long novoCodigo = codigo;
            linha = String.valueOf(++novoCodigo);
        }
        catch (Exception e) {
            throw new RepositoryException(message, e);
        }

        try {
            write(get(getArquivoContagem()), linha.getBytes(), CREATE);
        }
        catch (Exception e) {
            throw new StoreException(message, e);
        }
    }

    @Override
    public void salvar(final T entity) throws StoreException {
        String linha = criarLinha(entity);
        try {
            Path caminhoArquivo = buscarCaminhoArquivo();
            write(caminhoArquivo, asList(linha), UTF_8, APPEND, CREATE);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar a linha: " + linha;
            throw new StoreException(message, e);
        }
    }

    @Override
    public void atualizar(final T entity) throws StoreException {
        String linha = criarLinha(entity);
        try {
            Path caminhoArquivo = buscarCaminhoArquivo();
            atualizarLinha(caminhoArquivo, ((Entity) entity).getCodigo(), linha);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar a linha.";
            throw new StoreException(message, e);
        }
    }

    @Override
    public void deletar(final T entity) throws StoreException {
        String linha = criarLinha(entity);
        try {
            Path caminhoArquivo = buscarCaminhoArquivo();
            deletarLinha(caminhoArquivo, linha);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar a linha.";
            throw new StoreException(message, e);
        }
    }

    @Override
    public List<T> buscarRegistros() throws StoreException, RepositoryException {
        final String message = "Não foi possível buscar os registros.";
        List<String> linhas;
        try {
            Path caminhoArquivo = buscarCaminhoArquivo();
            linhas = readAllLines(caminhoArquivo);
        }
        catch (Exception e) {
            throw new StoreException(message, e);
        }

        try {
            return criarRegistros(linhas);
        }
        catch (Exception e) {
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public T filtrarCodigo(final List<T> entities, final Long codigo) {
        List<T> entityesBuscadas = entities
                .stream()
                .filter(c -> (((Entity) c).getCodigo().equals(codigo)))
                .collect(Collectors.toList());
        if (isEmpty(entityesBuscadas)) {
            return null;
        }
        return entityesBuscadas.get(0);
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

    private void atualizarLinha(Path caminho, Long codigo, String linha) throws IOException {
        Stream<String> linhasStream = Files.lines(caminho, UTF_8);
        List<String> linhasAtualizadas = linhasStream
                .map(linhaAtual -> buscarLinha(codigo, linha, linhaAtual))
                .collect(Collectors.toList());
        Files.write(caminho, linhasAtualizadas, UTF_8);
        linhasStream.close();
    }

    private static String buscarLinha(Long codigo, String novaLinha, String linha) {
        return linha.startsWith(String.valueOf(codigo).concat(";")) ? novaLinha : linha;
    }

    private void deletarLinha(Path caminho, String linha) throws IOException {
        Stream<String> linhasSream = Files.lines(caminho, UTF_8);
        List<String> linhasAtualizadas = linhasSream
                .filter(line -> !line.equals(linha))
                .collect(Collectors.toList());
        write(caminho, linhasAtualizadas);
        linhasSream.close();
    }

    private List<T> criarRegistros(final List<String> linhas) throws ParseException {
        List<T> entities = new ArrayList<>();
        for (String linha : linhas) {
            T entity = criarEntity(linha);
            entities.add(entity);
        }
        return entities;
    }
}
