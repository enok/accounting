package br.com.accounting.business.service;

import br.com.accounting.business.ConfigBusiness;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

@ContextConfiguration(classes = ConfigBusiness.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class GenericTest {

    @Autowired
    protected String diretorio;

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
