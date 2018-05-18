package br.com.accounting.commons.factory;

public abstract class GenericDTOFactory<D, E> {
    public abstract GenericDTOFactory begin();

    public abstract GenericDTOFactory preencherCampos(E entity);

    public abstract D build();
}
