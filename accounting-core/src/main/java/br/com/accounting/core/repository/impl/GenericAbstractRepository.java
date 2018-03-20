package br.com.accounting.core.repository.impl;

import br.com.accounting.core.exception.RepositoryException;
import br.com.accounting.core.repository.GenericRepository;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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

public abstract class GenericAbstractRepository<T> implements GenericRepository<T> {

    @Override
    public Long proximoCodigo() throws RepositoryException {
        Long proximoCodigo = null;

        try {
            Path path = buscarArquivoContagem();
            List<String> lines = readAllLines(path);
            proximoCodigo = buscarProximoCodigo(lines);
        }
        catch (Exception e) {
            String message = "Nao foi possivel ler as linhas do arquivo: " + getArquivoContagem();
            throw new RepositoryException(message, e);
        }

        return proximoCodigo;
    }

    @Override
    public void incrementarCodigo(final Long codigo) throws RepositoryException {
        try {
            Long novoCodigo = codigo;
            String linha = String.valueOf(++novoCodigo);
            write(get(getArquivoContagem()), linha.getBytes(), CREATE);
        }
        catch (Exception e) {
            String message = "Nao foi possivel incrementar o codigo: " + codigo;
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void salvar(final T entity) throws RepositoryException {
        String linha = null;

        try {
            linha = criarLinha(entity);
            String caminhoArquivo = getArquivo();
            write(get(caminhoArquivo), asList(linha), UTF_8, APPEND, CREATE);
        }
        catch (Exception e) {
            String message = "Nao foi possivel salvar a linha: " + linha;
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public List<T> buscarRegistros() throws RepositoryException {
        try {
            Path caminhoArquivo = buscarCaminhoArquivo();
            List<String> linhas = Files.readAllLines(caminhoArquivo);
            return criarRegistros(linhas);
        }
        catch (Exception e) {
            String message = "Nao foi possivel buscar os registros";
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void atualizar(final T entity, final T entityAtualizada) throws RepositoryException {
        try {
            String linha = criarLinha(entity);
            String linhaAtualizada = criarLinha(entityAtualizada);

            String caminhoArquivo = getArquivo();
            Path path = Paths.get(caminhoArquivo);

            atualizarLinha(path, linha, linhaAtualizada);
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar a linha.";
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void deletar(final T entity) throws RepositoryException {
        try {
            String linha = criarLinha(entity);

            String caminhoArquivo = getArquivo();
            Path path = Paths.get(caminhoArquivo);

            deletarLinha(path, linha);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar a linha.";
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

    private void atualizarLinha(Path caminho, String linha, String novaLinha) throws IOException {
        Stream<String> linhas = Files.lines(caminho);
        List<String> replaced = linhas
                .map(line -> line.replaceAll(linha, novaLinha))
                .collect(Collectors.toList());
        Files.write(caminho, replaced);
        linhas.close();
    }

    private void deletarLinha(Path caminho, String linha) throws IOException {
        Stream<String> linhas = Files.lines(caminho);
        List<String> replaced = linhas
                .filter(line -> !line.equals(linha))
                .collect(Collectors.toList());
        Files.write(caminho, replaced);
        linhas.close();
    }

    public abstract String getArquivo();

    public abstract String getArquivoContagem();

    public abstract String criarLinha(T entity);

    public abstract List<T> criarRegistros(List<String> linhas) throws ParseException;
}
