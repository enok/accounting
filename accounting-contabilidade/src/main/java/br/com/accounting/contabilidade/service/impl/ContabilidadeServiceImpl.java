package br.com.accounting.contabilidade.service.impl;

import br.com.accounting.commons.dto.GrupoDTO;
import br.com.accounting.commons.entity.*;
import br.com.accounting.commons.exception.*;
import br.com.accounting.commons.service.impl.GenericAbstractService;
import br.com.accounting.contabilidade.repository.ContabilidadeRepository;
import br.com.accounting.contabilidade.service.ContabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ContabilidadeServiceImpl extends GenericAbstractService<Contabilidade> implements ContabilidadeService {
    @Autowired
    private RestTemplate restTemplate;

    private ContabilidadeRepository repository;

    @Autowired
    public ContabilidadeServiceImpl(ContabilidadeRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void ordenarTodas(final List<Contabilidade> entities) {
        repository.ordenarPorDataVencimentoGrupoSubGrupo(entities);
    }

    @Override
    public List<Contabilidade> buscarTodasAsParcelas(final Long codigoPai) throws StoreException, ParseException {
        List<Contabilidade> entities = repository.buscarRegistros();
        return repository.filtrarPorCodigoPai(entities, codigoPai);
    }

    @Override
    public List<Contabilidade> buscarParcelasSeguintesInclusivo(final Long codigoPai, final Integer parcelaAtual) throws StoreException, ParseException {
        List<Contabilidade> entities = repository.buscarRegistros();
        return repository.filtrarParcelasPosteriores(entities, codigoPai, parcelaAtual);
    }

    @Override
    public List<Contabilidade> buscarTodasRecorrentesNaoLancadas() throws StoreException, ParseException {
        List<Contabilidade> entities = repository.buscarRegistros();
        return repository.filtrarRecorrentesNaoLancados(entities);
    }

    @Override
    public List<Contabilidade> buscarTodasRecorrentesSeguintesInclusivo(final Long codigo) throws StoreException, ParseException {
        List<Contabilidade> entities = new ArrayList<>();
        Long proximoLancamento = codigo;
        do {
            Contabilidade entity = buscarPorCodigo(proximoLancamento);
            if (entity == null) {
                break;
            }
            entities.add(entity);
            proximoLancamento = entity.proximoLancamento();
        }
        while (proximoLancamento != null);
        return entities;
    }

    @Override
    public Contabilidade buscar(LocalDate dataVencimento, Boolean recorrente, Grupo grupo, Local local, String descricao,
                                Boolean usouCartao, Boolean parcelado, Parcelamento parcelamento, Conta conta,
                                TipoContabilidade tipo, Double valor) throws StoreException, ParseException {
        List<Contabilidade> entities = repository.buscarRegistros();
        String local_ = (local == null) ? "" : local.nome();
        Integer parcelas = (parcelamento != null) ? parcelamento.parcelas() : null;
        List<Contabilidade> contabilidades = repository.filtrarPorCampos(entities, dataVencimento, recorrente, grupo.nome(), local_, descricao,
                usouCartao, parcelado, parcelas, conta.nome(), tipo, valor);
        if (CollectionUtils.isEmpty(contabilidades)) {
            return null;
        }
        return contabilidades.get(0);
    }

    @Override
    public void normalizarProximosLancamentos() throws ServiceException, StoreException, ParseException {
        List<Contabilidade> entities = buscarTodas();
        for (Contabilidade entity : entities) {
            Contabilidade entityBuscada = buscarPorCodigo(entity.proximoLancamento());
            if (entityBuscada == null) {
                entity.proximoLancamento(null);
                atualizar(entity);
            }
        }
    }

    @Override
    public Grupo buscarGrupoPorNome(final String nome) throws BusinessException, StoreException, GenericException {
        Grupo grupo = null;
        String messageError = "buscar grupo por nome";

        try {
            String url = "http://localhost:9094/grupo/" + nome;

            HttpHeaders headers = createHttpHeaders("fred", "1234");
            HttpEntity<String> entity = new HttpEntity("parameters", headers);
            ResponseEntity<GrupoDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, GrupoDTO.class);
            int value = response.getStatusCodeValue();

            switch (value) {
                case 400:
                    throw new ValidationException("Erro de validação ao " + messageError);
                case 417:
                    throw new BusinessException("Erro de negócio ao " + messageError);
                case 507:
                    throw new StoreException("Erro de persistência ao " + messageError);
                case 503:
                    throw new GenericException("Erro genérico ao " + messageError);
            }



        } catch (Exception e) {
            throw new GenericException("Erro genérico ao " + messageError);
        }



        return res

        System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
    }

    private HttpHeaders createHttpHeaders(String user, String password) {
        String notEncoded = user + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", encodedAuth);
        return headers;
    }
}
