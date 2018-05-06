package br.com.accounting.business.service;

import br.com.accounting.business.ConfigBusiness;
import br.com.accounting.business.exception.BusinessException;
import br.com.accounting.business.exception.GenericException;
import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.UpdateException;
import br.com.accounting.core.exception.StoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ContextConfiguration(classes = ConfigBusiness.class, loader = AnnotationConfigContextLoader.class)
@RunWith(SpringRunner.class)
public abstract class GenericTest {

    @Autowired
    protected String diretorio;

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

    protected void assertCreationAndMandatoryFields(MissingFieldException e, String... campos) throws BusinessException {
        assertMandatoryFields(e, campos);
        throw e;
    }

    protected void assertUpdateAndMandatoryFields(BusinessException e, String... campos) throws BusinessException {
        assertThat(e.getMessage(), equalTo("Não foi possível atualizar."));
        assertMandatoryFields(e, campos);
        throw e;
    }

    private void assertMandatoryFields(BusinessException e, String... camposArray) {
        List<String> campos = Arrays.asList(camposArray);
        String mensagem = "O campo %s é obrigatório.";

        if (e instanceof MissingFieldException) {
            MissingFieldException e1 = (MissingFieldException) e;
            List<String> erros = e1.getErros();
            assertThat(erros.size(), equalTo(campos.size()));

            for (int i = 0; i < campos.size(); i++) {
                assertThat(erros.get(i), equalTo(String.format(mensagem, campos.get(i))));
            }
        }
    }

    protected void assertUpdateNotModifiebleFields(BusinessException e, String... camposArray) throws BusinessException {
        List<String> campos = Arrays.asList(camposArray);
        assertThat(e.getMessage(), equalTo("Não foi possível atualizar."));

        UpdateException e1 = (UpdateException) e.getCause();
        List<String> erros = e1.getErros();
        assertThat(erros.size(), equalTo(campos.size()));

        for (int i = 0; i < campos.size(); i++) {
            assertThat(erros.get(i), equalTo("O campo " + campos.get(i) + " não pode ser alterado."));
        }

        throw e;
    }

    private boolean diretorioExiste() {
        return Files.exists(Paths.get(diretorio));
    }
}
