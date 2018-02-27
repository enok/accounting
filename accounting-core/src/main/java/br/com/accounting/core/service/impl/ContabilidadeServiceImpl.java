package br.com.accounting.core.service.impl;

import br.com.accounting.core.entity.*;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.filter.*;
import br.com.accounting.core.ordering.*;
import br.com.accounting.core.repository.ContabilidadeRepository;
import br.com.accounting.core.service.ContabilidadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContabilidadeServiceImpl extends GenericService<Contabilidade> implements ContabilidadeService {
    private static final Logger LOG = LoggerFactory.getLogger(GenericService.class);

    @Autowired
    public ContabilidadeServiceImpl(ContabilidadeRepository contabilidadeRepository) {
        super(contabilidadeRepository);
    }

    @Override
    public List<Contabilidade> filtrarPorIntervaloDeVencimento(String vencimentoInicial, String vencimentoFinal, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorIntervaloDeVencimento ]");
        LOG.debug("vencimentoInicial: " + vencimentoInicial);
        LOG.debug("vencimentoFinal: " + vencimentoFinal);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeVencimento(vencimentoInicial, vencimentoFinal);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por intervalo de vencimento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorIntervaloDeVencimento(String vencimentoInicial, String vencimentoFinal) throws ServiceException {
        LOG.info("[ filtrarPorIntervaloDeVencimento ]");
        LOG.debug("vencimentoInicial: " + vencimentoInicial);
        LOG.debug("vencimentoFinal: " + vencimentoFinal);

        try {
            Filtro filtro = new FiltroContabilidadeVencimento(vencimentoInicial, vencimentoFinal);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por intervalo de vencimento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorTipoDePagamento(TipoPagamento tipoPagamento, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorTipoDePagamento ]");
        LOG.debug("tipoPagamento: " + tipoPagamento);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeTipoPagamento(tipoPagamento);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por tipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorTipoDePagamento(TipoPagamento tipoPagamento) throws ServiceException {
        LOG.info("[ filtrarPorTipoDePagamento ]");
        LOG.debug("tipoPagamento: " + tipoPagamento);

        try {
            Filtro filtro = new FiltroContabilidadeTipoPagamento(tipoPagamento);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por tipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorSubTipoDePagamento(String descricao, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorSubTipoDePagamento ]");
        LOG.debug("descricao: " + descricao);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeSubTipoPagamentoDescricao(descricao);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por subtipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorSubTipoDePagamento(String descricao) throws ServiceException {
        LOG.info("[ filtrarPorSubTipoDePagamento ]");
        LOG.debug("descricao: " + descricao);

        try {
            Filtro filtro = new FiltroContabilidadeSubTipoPagamentoDescricao(descricao);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por subtipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorTipo(Tipo tipo, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorTipo ]");
        LOG.debug("tipo: " + tipo);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeTipo(tipo);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por tipo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorTipo(Tipo tipo) throws ServiceException {
        LOG.info("[ filtrarPorTipo ]");
        LOG.debug("tipo: " + tipo);

        try {
            Filtro filtro = new FiltroContabilidadeTipo(tipo);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por tipo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorGrupo(String descricao, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorGrupo ]");
        LOG.debug("descricao: " + descricao);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeGrupoDescricao(descricao);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorGrupo(String descricao) throws ServiceException {
        LOG.info("[ filtrarPorGrupo ]");
        LOG.debug("descricao: " + descricao);

        try {
            Filtro filtro = new FiltroContabilidadeGrupoDescricao(descricao);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorGrupoESubGrupo(String descricaoGrupo, String descricaoSubGrupo, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorGrupoESubGrupo ]");
        LOG.debug("descricaoGrupo: " + descricaoGrupo);
        LOG.debug("descricaoSubGrupo: " + descricaoSubGrupo);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeGrupoDescricaoSubGrupoDescricao(descricaoGrupo, descricaoSubGrupo);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por grupo e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorGrupoESubGrupo(String descricaoGrupo, String descricaoSubGrupo) throws ServiceException {
        LOG.info("[ filtrarPorGrupoESubGrupo ]");
        LOG.debug("descricaoGrupo: " + descricaoGrupo);
        LOG.debug("descricaoSubGrupo: " + descricaoSubGrupo);

        try {
            Filtro filtro = new FiltroContabilidadeGrupoDescricaoSubGrupoDescricao(descricaoGrupo, descricaoSubGrupo);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por grupo e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorDescricao(String descricao, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorDescricao ]");
        LOG.debug("descricao: " + descricao);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeDescricao(descricao);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorDescricao(String descricao) throws ServiceException {
        LOG.info("[ filtrarPorDescricao ]");
        LOG.debug("descricao: " + descricao);

        try {
            Filtro filtro = new FiltroContabilidadeDescricao(descricao);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorParcelamentoPai(Long codigoPai, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorParcelamentoPai ]");
        LOG.debug("codigoPai: " + codigoPai);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeParcelamentoPai(codigoPai);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por parcelamento codigo pai";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorParcelamentoPai(Long codigoPai) throws ServiceException {
        LOG.info("[ filtrarPorParcelamentoPai ]");
        LOG.debug("codigoPai: " + codigoPai);

        try {
            Filtro filtro = new FiltroContabilidadeParcelamentoPai(codigoPai);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por parcelamento codigo pai";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorCategoria(Categoria categoria, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorCategoria ]");
        LOG.debug("categoria: " + categoria);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeCategoria(categoria);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por categoria";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorCategoria(Categoria categoria) throws ServiceException {
        LOG.info("[ filtrarPorCategoria ]");
        LOG.debug("categoria: " + categoria);

        try {
            Filtro filtro = new FiltroContabilidadeCategoria(categoria);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por categoria";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorStatus(Status status, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ filtrarPorStatus ]");
        LOG.debug("status: " + status);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            Filtro filtro = new FiltroContabilidadeStatus(status);
            return filtrar(filtro, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por status";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> filtrarPorStatus(Status status) throws ServiceException {
        LOG.info("[ filtrarPorStatus ]");
        LOG.debug("status: " + status);

        try {
            Filtro filtro = new FiltroContabilidadeStatus(status);
            return filtrar(filtro);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel filtrar os registros por status";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorVencimento(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorVencimento ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por vencimento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorVencimento(Order order) throws ServiceException {
        LOG.info("[ ordenarPorVencimento ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeVencimento();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por vencimento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorTipoDePagamento(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorTipoDePagamento ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por tipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorTipoDePagamento(Order order) throws ServiceException {
        LOG.info("[ ordenarPorTipoDePagamento ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipoPagamento();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por tipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarSubTipoDePagamento(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarSubTipoDePagamento ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por subtipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarSubTipoDePagamento(Order order) throws ServiceException {
        LOG.info("[ ordenarSubTipoDePagamento ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeSubTipoPagamentoDescricao();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por subtipo de pagamento";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorTipo(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorTipo ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por tipo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorTipo(Order order) throws ServiceException {
        LOG.info("[ ordenarPorTipo ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeTipo();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por tipo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorGrupo(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorGrupo ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorGrupo(Order order) throws ServiceException {
        LOG.info("[ ordenarPorGrupo ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricao();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por grupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorGrupoESubGrupo(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorGrupoESubGrupo ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por grupo e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorGrupoESubGrupo(Order order) throws ServiceException {
        LOG.info("[ ordenarPorGrupoESubGrupo ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeGrupoDescricaoSubGrupoDescricao();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por grupo e subgrupo";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorDescricao(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorDescricao(Order order) throws ServiceException {
        LOG.info("[ ordenarPorDescricao ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeDescricao();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por descricao";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorParcelamentoPai(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorParcelamentoPai ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeParcelamentoPai();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por parcelamento codigo pai";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorParcelamentoPai(Order order) throws ServiceException {
        LOG.info("[ ordenarPorParcelamentoPai ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeParcelamentoPai();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por parcelamento codigo pai";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorCategoria(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorCategoria ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por categoria";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorCategoria(Order order) throws ServiceException {
        LOG.info("[ ordenarPorCategoria ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeCategoria();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por categoria";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorValor(Order order, List<Contabilidade> registros) throws ServiceException {
        LOG.info("[ ordenarPorValor ]");
        LOG.debug("order: " + order);
        LOG.debug("registros: " + registros);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeValor();
            return ordenar(campoOrdem, order, registros);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por valor";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorValor(Order order) throws ServiceException {
        LOG.info("[ ordenarPorValor ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeValor();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por valor";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorStatus(Order order, List<Contabilidade> contabilidades) throws ServiceException {
        LOG.info("[ ordenarPorStatus ]");
        LOG.debug("order: " + order);
        LOG.debug("contabilidades: " + contabilidades);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeStatus();
            return ordenar(campoOrdem, order, contabilidades);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por status";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }

    @Override
    public List<Contabilidade> ordenarPorStatus(Order order) throws ServiceException {
        LOG.info("[ ordenarPorStatus ]");
        LOG.debug("order: " + order);

        try {
            CampoOrdem campoOrdem = new CampoOrdemContabilidadeStatus();
            return ordenar(campoOrdem, order);
        } catch (Exception e) {
            String mensagem = "Nao foi possivel ordenar os registros por status";
            LOG.error(mensagem, e);
            throw new ServiceException(mensagem, e);
        }
    }
}
