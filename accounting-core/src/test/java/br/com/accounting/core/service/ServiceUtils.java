package br.com.accounting.core.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static br.com.accounting.core.repository.impl.GenericRepository.DIRETORIO;

public final class ServiceUtils {

    private ServiceUtils() {
    }

    public static void deletarDiretorioEArquivos() throws IOException {
        Path diretorio = Paths.get(DIRETORIO);
        Files.walk(diretorio, FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);
    }

    public static void deletarArquivosDoDiretorio() throws IOException {
        Path diretorio = Paths.get(DIRETORIO);
        Files.walk(diretorio, FileVisitOption.FOLLOW_LINKS)
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);
    }
}
