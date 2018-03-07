package br.com.accounting.core.service;

import br.com.accounting.core.entity.*;
import br.com.accounting.core.exception.ServiceException;

import java.util.List;

public interface ContabilidadeService extends GenericService<Contabilidade> {
    List<Contabilidade> filtrarPorIntervaloDeVencimento(String vencimentoInicial, String vencimentoFinal, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorValoresAcimaDoVencimento(String vencimento, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorIntervaloDeVencimento(String vencimentoInicial, String vencimentoFinal) throws ServiceException;

    List<Contabilidade> filtrarPorTipoDePagamento(TipoPagamento tipoPagamento, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorTipoDePagamento(TipoPagamento tipoPagamento) throws ServiceException;

    List<Contabilidade> filtrarPorSubTipoDePagamento(String descricao, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorSubTipoDePagamento(String descricao) throws ServiceException;

    List<Contabilidade> filtrarPorTipo(Tipo tipo, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorTipo(Tipo tipo) throws ServiceException;

    List<Contabilidade> filtrarPorGrupo(String descricao, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorGrupo(String descricao) throws ServiceException;

    List<Contabilidade> filtrarPorGrupoESubGrupo(String descricaoGrupo, String descricaoSubGrupo, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorGrupoESubGrupo(String descricaoGrupo, String descricaoSubGrupo) throws ServiceException;

    List<Contabilidade> filtrarPorDescricao(String descricao, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorDescricao(String descricao) throws ServiceException;

    List<Contabilidade> filtrarPorParcelamentoPai(Long codigoPai, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorParcelamentoPai(Long codigoPai) throws ServiceException;

    List<Contabilidade> filtrarPorCategoria(Categoria categoria, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorCategoria(Categoria categoria) throws ServiceException;

    List<Contabilidade> filtrarPorStatus(Status status, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> filtrarPorStatus(Status status) throws ServiceException;

    List<Contabilidade> ordenarPorVencimento(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorVencimento(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorTipoDePagamento(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorTipoDePagamento(Order order) throws ServiceException;

    List<Contabilidade> ordenarSubTipoDePagamento(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarSubTipoDePagamento(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorTipo(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorTipo(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorGrupo(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorGrupo(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorGrupoESubGrupo(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorGrupoESubGrupo(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorDescricao(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorDescricao(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorParcelamentoPai(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorParcelamentoPai(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorCategoria(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorCategoria(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorValor(Order order, List<Contabilidade> registros) throws ServiceException;

    List<Contabilidade> ordenarPorValor(Order order) throws ServiceException;

    List<Contabilidade> ordenarPorStatus(Order order, List<Contabilidade> contabilidades) throws ServiceException;

    List<Contabilidade> ordenarPorStatus(Order order) throws ServiceException;
}
