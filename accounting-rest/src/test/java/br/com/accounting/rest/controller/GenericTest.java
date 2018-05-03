package br.com.accounting.rest.controller;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public abstract class GenericTest {

    private final String diretorio = "D:\\\\tmp\\\\arquivos";

    @Before
    public void setUp() throws IOException {
        criarDiretorio();
        deletarArquivosDoDiretorio();
    }

    @After
    public void after() throws IOException {
        criarDiretorio();
        deletarArquivosDoDiretorio();
    }

    protected void criarDiretorio() throws IOException {
        if (!diretorioExiste()) {
            Files.createDirectory(Paths.get(diretorio));
        }
    }

    protected void deletarDiretorioEArquivos() throws IOException {
        if (diretorioExiste()) {
            Path diretorioPath = Paths.get(diretorio);
            Files.walk(diretorioPath, FileVisitOption.FOLLOW_LINKS)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .peek(System.out::println)
                    .forEach(File::delete);
        }
    }

    protected void deletarArquivosDoDiretorio() throws IOException {
        if (diretorioExiste()) {
            Path diretorioPath = Paths.get(diretorio);
            Files.walk(diretorioPath, FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .peek(System.out::println)
                    .forEach(File::delete);
        }
    }

    private boolean diretorioExiste() {
        return Files.exists(Paths.get(diretorio));
    }
}
