package br.com.accounting.business.validation;

import br.com.accounting.business.exception.MissingFieldException;
import br.com.accounting.business.exception.WrongEnumValueException;
import br.com.accounting.core.entity.Categoria;
import br.com.accounting.core.entity.Status;
import br.com.accounting.core.entity.Tipo;
import br.com.accounting.core.entity.TipoPagamento;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class ValidacaoCampo {

    private ValidacaoCampo() {
    }

    public static void validaCampoObrigatorio(String campo, String campoNome) throws MissingFieldException {
        if (isBlank(campo)) {
            throw new MissingFieldException("Campo obrigatorio faltando: " + campoNome);
        }
    }

    public static void validaTipoPagamento(String tipoPagamento) throws MissingFieldException, WrongEnumValueException {
        validaCampoObrigatorio(tipoPagamento, "tipoPagamento");

        try {
            TipoPagamento.valueOf(tipoPagamento);
        }
        catch (IllegalArgumentException e) {
            throw new WrongEnumValueException("Enum TipoPagamento com valor incorreto: " + tipoPagamento, e);
        }
    }

    public static void validaTipo(String tipo) throws MissingFieldException, WrongEnumValueException {
        validaCampoObrigatorio(tipo, "tipo");

        try {
            Tipo.valueOf(tipo);
        }
        catch (IllegalArgumentException e) {
            throw new WrongEnumValueException("Enum Tipo com valor incorreto: " + tipo, e);
        }
    }

    public static void validaCategoria(String categoria) throws MissingFieldException, WrongEnumValueException {
        validaCampoObrigatorio(categoria, "categoria");

        try {
            Categoria.valueOf(categoria);
        }
        catch (IllegalArgumentException e) {
            throw new WrongEnumValueException("Enum Categoria com valor incorreto: " + categoria, e);
        }
    }

    public static void validaStatus(String status) throws MissingFieldException, WrongEnumValueException {
        validaCampoObrigatorio(status, "status");

        try {
            Status.valueOf(status);
        }
        catch (IllegalArgumentException e) {
            throw new WrongEnumValueException("Enum Status com valor incorreto: " + status, e);
        }
    }
}
