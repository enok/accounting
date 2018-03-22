package br.com.accounting.core.repository.impl;

import br.com.accounting.core.entity.Entity;
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
            String message = "Não foi possível ler as linhas do arquivo: " + getArquivoContagem();
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
            String message = "Não foi possível incrementar o código: " + codigo;
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void salvar(final T entity) throws RepositoryException {
        String linha = null;

        try {
            linha = criarLinha(entity);
            Path caminhoArquivo = buscarCaminhoArquivo();
            write(caminhoArquivo, asList(linha), UTF_8, APPEND, CREATE);
        }
        catch (Exception e) {
            String message = "Não foi possível salvar a linha: " + linha;
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public void atualizar(final T entity) throws RepositoryException {
        try {
            String linha = criarLinha(entity);
            Path caminhoArquivo = buscarCaminhoArquivo();
            atualizarLinha(caminhoArquivo, ((Entity) entity).getCodigo(), linha);
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
            Path caminhoArquivo = buscarCaminhoArquivo();
            deletarLinha(caminhoArquivo, linha);
        }
        catch (Exception e) {
            String message = "Não foi possível deletar a linha.";
            throw new RepositoryException(message, e);
        }
    }

    @Override
    public List<T> buscarRegistros() throws RepositoryException {
        try {
            Path caminhoArquivo = buscarCaminhoArquivo();
            List<String> linhas = readAllLines(caminhoArquivo);
            return criarRegistros(linhas);
        }
        catch (Exception e) {
            String message = "Não foi possível buscar os registros";
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

    private Path buscarCaminhoArquivo() throws IOException, InterruptedException {
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
        return linha.startsWith(String.valueOf(codigo)) ? novaLinha : linha;
    }

    private void deletarLinha(Path caminho, String linha) throws IOException {
        Stream<String> linhasSream = Files.lines(caminho, UTF_8);
        List<String> linhasAtualizadas = linhasSream
                .filter(line -> !line.equals(linha))
                .collect(Collectors.toList());
        write(caminho, linhasAtualizadas);
        linhasSream.close();
    }

    public abstract String getArquivo();

    public abstract String getArquivoContagem();

    public abstract String criarLinha(T entity);

    public abstract List<T> criarRegistros(List<String> linhas) throws ParseException;
}
