package br.com.accounting.core.service;

import br.com.accounting.core.CoreConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static br.com.accounting.core.repository.impl.GenericRepository.DIRETORIO;

@ContextConfiguration(classes = CoreConfig.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class GenericTest {

    @Before
    public void setUp() throws IOException {
        criarDiretorio();
        deletarArquivosDoDiretorio();
    }

    @After
    public void after() throws IOException {
        deletarArquivosDoDiretorio();
    }

    protected void criarDiretorio() throws IOException {
        if (!diretorioExiste()) {
            Files.createDirectory(Paths.get(DIRETORIO));
        }
    }

    protected void deletarDiretorioEArquivos() throws IOException {
        if (diretorioExiste()) {
            Path diretorio = Paths.get(DIRETORIO);
            Files.walk(diretorio, FileVisitOption.FOLLOW_LINKS)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .peek(System.out::println)
                    .forEach(File::delete);
        }
    }

    protected void deletarArquivosDoDiretorio() throws IOException {
        if (diretorioExiste()) {
            Path diretorio = Paths.get(DIRETORIO);
            Files.walk(diretorio, FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .peek(System.out::println)
                    .forEach(File::delete);
        }
    }

    private boolean diretorioExiste() {
        return Files.exists(Paths.get(DIRETORIO));
    }
}
