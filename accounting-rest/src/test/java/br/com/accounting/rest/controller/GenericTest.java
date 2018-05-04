package br.com.accounting.rest.controller;

import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.rest.ConfigRest;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@ContextConfiguration(classes = ConfigRest.class, loader = AnnotationConfigContextLoader.class)
public abstract class GenericTest {

    private final String diretorio = "D:\\tmp\\arquivos";

    @Before
    public void setUp() throws IOException, StoreException, BusinessException, GenericException {
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
