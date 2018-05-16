package br.com.accounting.business.service.impl;

import br.com.accounting.business.annotation.History;
import br.com.accounting.business.dto.ContabilidadeDTO;
import br.com.accounting.business.exception.*;
import br.com.accounting.business.factory.ContabilidadeDTOFactory;
import br.com.accounting.business.service.ContabilidadeBusiness;
import br.com.accounting.core.entity.*;
import br.com.accounting.core.exception.ServiceException;
import br.com.accounting.core.exception.StoreException;
import br.com.accounting.core.factory.ContabilidadeFactory;
import br.com.accounting.core.service.*;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.core.util.Utils.*;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ContabilidadeBusinessImpl extends GenericAbstractBusiness<ContabilidadeDTO, Contabilidade> implements ContabilidadeBusiness {
    private ContabilidadeService service;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private SubGrupoService subGrupoService;
    @Autowired
    private LocalService localService;
    @Autowired
    private CartaoService cartaoService;
    @Autowired
    private ContaService contaService;

    @Autowired
    public ContabilidadeBusinessImpl(final ContabilidadeService service) {
        super(service, ContabilidadeDTOFactory.create());
        this.service = service;
    }

    @History
    public List<Long> criar(final ContabilidadeDTO dto) throws ValidationException, StoreException, GenericException {
        try {
            final List<String> erros = new ArrayList<>();

            validarEntrada(dto, erros);

            Contabilidade entity = criarEntity(dto);
            List<Long> codigos = new ArrayList<>();
            Long codigoPai = null;

            entity.dataPagamento(null);

            validaRegistroDuplicado(entity);

            if (entity.parcelado()) {
                List<Long> codigosParcelados = criarParcelados(entity, codigoPai);
                codigos.addAll(codigosParcelados);
            }
            else if (entity.recorrente()) {
                int meses = getRemainingMonthsInclusiveFromYear(entity.dataVencimento());
                List<Long> codigosRecorrentes = criarRecorrente(entity, meses);
                codigos.addAll(codigosRecorrentes);
            }
            else {
                Long codigo = service.salvar(entity);
                codigos.add(codigo);
            }

            return codigos;
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao salvar.", e);
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @History
    @Override
    public void atualizar(final ContabilidadeDTO dto) throws ValidationException, StoreException, GenericException {
        dto.dataAtualizacao(getStringFromCurrentDate());
        super.atualizar(dto);
    }

    @History
    @Override
    public void atualizarRecursivamente(final ContabilidadeDTO dto) throws StoreException, BusinessException, GenericException {
        try {
            if (!ehParcelado(dto) && !ehRecorrente(dto)) {
                atualizar(dto);
            }
            else {
                if (ehParcelado(dto)) {
                    atualizarParcelas(dto);
                }
                else {
                    atualizarRecorrentes(dto);
                }
            }
        }
        catch (DateTimeParseException | ParseException e) {
            throw new ValidationException(e);
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao atualizar recursivamente.", e);
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @History
    @Override
    public List<Long> incrementarRecorrentes(final Integer anos) throws StoreException, BusinessException {
        try {
            if (anos < 1) {
                throw new BusinessException("O valor de anos deve ser maior ou igual a 1.");
            }
            List<Contabilidade> entities = service.buscarTodasRecorrentesNaoLancadas();

            List<Long> codigos = new ArrayList<>();
            for (Contabilidade entity : entities) {
                int meses = getRemainingMonthsInclusiveFromYear(entity.dataVencimento());
                if (anos > 1) {
                    meses += ((anos - 1) * 12);
                }
                List<Long> codigosRecorrentes = criarRecorrente(entity, meses);
                codigos.addAll(codigosRecorrentes);
            }

            return codigos;
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível atualizar recorrentes.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void excluirSubsequentes(final ContabilidadeDTO dto) throws StoreException, BusinessException {
        try {
            excluirParcelas(dto);
            excluirRecorrentes(dto);
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível excluir subsequentes.";
            throw new BusinessException(message, e);
        }
    }

    @History
    @Override
    public void realizarPagamento(final Long codigo) throws StoreException, BusinessException {
        try {
            ContabilidadeDTO dto = buscarPorCodigo(codigo);
            dto.dataPagamento(getStringFromCurrentDate());
            atualizar(dto);
        }
        catch (StoreException e) {
            throw e;
        }
        catch (Exception e) {
            String message = "Não foi possível realizar o pagamento.";
            throw new BusinessException(message, e);
        }
    }

    @Override
    public List<ContabilidadeDTO> buscarTodasAsParcelas(final Long codigoPai) throws BusinessException {
        List<ContabilidadeDTO> entitiesDTO;
        try {
            List<Contabilidade> entitiesFiltradas = service.buscarTodasAsParcelas(codigoPai);
            entitiesDTO = criarListaDTO(entitiesFiltradas);
        }
        catch (Exception e) {
            String message = "Não foi possível buscas todas as parcelas.";
            throw new BusinessException(message, e);
        }
        return entitiesDTO;
    }

    @Override
    public List<ContabilidadeDTO> buscarTodasAsRecorrentes(Long codigo) throws BusinessException {
        List<ContabilidadeDTO> entitiesDTO;
        try {
            List<Contabilidade> entitiesFiltradas = service.buscarTodasRecorrentesSeguintesInclusivo(codigo);
            entitiesDTO = criarListaDTO(entitiesFiltradas);
        }
        catch (Exception e) {
            String message = "Não foi possível buscas todas as recorrentes.";
            throw new BusinessException(message, e);
        }
        return entitiesDTO;
    }

    @Override
    public void validarEntrada(final ContabilidadeDTO dto, final List<String> erros) throws MissingFieldException,
            StoreException, ParseException, CreateException {
        if (isBlank(dto.dataVencimento())) {
            erros.add(format(msg, "dataVencimento"));
        }
        if (isBlank(dto.recorrente())) {
            erros.add(format(msg, "recorrente"));
        }
        if (isBlank(dto.grupo())) {
            erros.add(format(msg, "grupo"));
        }
        if (isBlank(dto.subGrupo())) {
            erros.add(format(msg, "subGrupo"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        if (isBlank(dto.usouCartao())) {
            erros.add(format(msg, "usouCartão"));
        }
        if (isBlank(dto.parcelado())) {
            erros.add(format(msg, "parcelado"));
        }
        if (usouCartao(dto)) {
            if (temCartao(dto)) {
                if (ehParcelado(dto)) {
                    if (naoTemParcelas(dto)) {
                        erros.add(format(msg, "parcelas"));
                    }
                }
            }
            else {
                erros.add(format(msg, "cartão"));
            }
        }
        if (isBlank(dto.conta())) {
            erros.add(format(msg, "conta"));
        }
        if (isBlank(dto.tipo())) {
            erros.add(format(msg, "tipo"));
        }
        if (isBlank(dto.valor())) {
            erros.add(format(msg, "valor"));
        }
        conferirErrosCamposObrigatorios(erros);

        List<String> errosCreate = new ArrayList<>();

        conferirValorBooleano(errosCreate, dto.recorrente(), "recorrente");
        conferirValorBooleano(errosCreate, dto.usouCartao(), "usouCartao");
        conferirValorBooleano(errosCreate, dto.parcelado(), "parcelado");

        conferirRecorrenteEParcelado(dto, errosCreate);
        conferirSeGrupoCadastrado(dto, errosCreate);
        conferirSeSubGrupoCadastrado(dto, errosCreate);
        conferirSeLocalCadastrado(dto, errosCreate);
        conferirSeCartaoCadastrado(dto, errosCreate);
        conferirSeContaCadastrada(dto, errosCreate);

        conferirSeGrupoESubGrupoEstaoAssociados(dto, errosCreate);

        conferirErrosCreate(errosCreate);
    }

    @Override
    public void validarEntradaUpdate(final ContabilidadeDTO dto, final Contabilidade entity, final List<String> erros) throws ValidationException {
        conferirCodigo(dto, erros);
        if (isBlank(dto.dataLancamento())) {
            erros.add(format(msg, "dataLançamento"));
        }
        if (usouCartao(dto) && temCartao(dto) && ehParcelado(dto) && naoTemParcela(dto)) {
            erros.add(format(msg, "parcela"));
        }
        if (ehParcelado(dto) && (entity != null) && naoEhPai(entity)) {
            if (isBlank(dto.codigoPai())) {
                erros.add(format(msg, "códigoPai"));
            }
        }
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();

        conferirCodigoAlterado(dto, entity, errosUpdate);
        conferirDataLancamentoAlterada(dto, entity, errosUpdate);
        conferirParcelasAlteradas(dto, entity, errosUpdate);
        conferirParcelaAlterada(dto, entity, errosUpdate);
        conferirCodigoPaiAlterado(dto, entity, errosUpdate);

        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final Contabilidade entity) throws StoreException, ParseException, DuplicatedRegistryException {
        Contabilidade entityBuscada = service.buscar(entity.dataVencimento(), entity.recorrente(), entity.grupo(), entity.local(),
                entity.descricao(), entity.usouCartao(), entity.parcelado(), entity.parcelamento(),
                entity.conta(), entity.tipo(), entity.valor());
        if (entity.equals(entityBuscada)) {
            throw new DuplicatedRegistryException("Contabilidade duplicada.");
        }
    }

    @Override
    public Contabilidade criarEntity(final ContabilidadeDTO dto) throws ValidationException {
        try {
            return ContabilidadeFactory
                    .begin()
                    .withCodigo(dto.codigo())
                    .withDataVencimento(dto.dataVencimento())
                    .withDataPagamento(dto.dataPagamento())
                    .withRecorrente(dto.recorrente())
                    .withGrupo(dto.grupo(), dto.subGrupo())
                    .withLocal(dto.local())
                    .withDescricao(dto.descricao())
                    .withUsouCartao(dto.usouCartao())
                    .withCartao(dto.cartao())
                    .withParcelado(dto.parcelado())
                    .withParcelamento(dto.parcela(), dto.parcelas())
                    .withConta(dto.conta())
                    .withTipo(dto.tipo())
                    .withValor(dto.valor())
                    .withCodigoPai(dto.codigoPai())
                    .withProximoLancamento(dto.proximoLancamento())
                    .build();
        }
        catch (DateTimeParseException | ParseException | IllegalArgumentException e) {
            throw new ValidationException(e);
        }
    }

    @Override
    protected Contabilidade criarEntity(ContabilidadeDTO dto, Contabilidade entityBuscado) throws ValidationException {
        return criarEntity(dto);
    }

    private List<Long> criarParcelados(Contabilidade entity, Long codigoPai) throws StoreException, ServiceException {
        List<Long> codigos = new ArrayList<>();
        LocalDate dataVencimento = entity.dataVencimento();
        for (int i = 0; i < entity.parcelamento().parcelas(); i++) {
            entity.dataVencimento(dataVencimento);
            entity.parcelamento().parcela(i + 1);
            entity.codigoPai(codigoPai);

            Long codigo = service.salvar(entity);
            codigos.add(codigo);

            dataVencimento = getDateNextMonth(dataVencimento);
            if (codigoPai == null) {
                codigoPai = codigo;
            }
        }
        return codigos;
    }

    private List<Long> criarRecorrente(final Contabilidade entity, final Integer meses) throws StoreException, ParseException, ServiceException, BusinessException, GenericException {
        validaRecorrente(entity, meses);

        List<Long> codigos = new ArrayList<>();
        Long codigoAnterior = null;
        for (int i = 0; i < meses; i++) {
            Contabilidade copiaEntity = SerializationUtils.clone(entity);
            Contabilidade entityBuscada = null;
            if (codigoAnterior != null) {
                entityBuscada = service.buscarPorCodigo(codigoAnterior);
                if (entityBuscada != null) {
                    LocalDate novaDataVencimento = buscarNovaDataDeVencimento(entityBuscada);
                    copiaEntity.dataVencimento(novaDataVencimento);
                }
            }

            Long codigo;
            if ((i == 0) && (copiaEntity.codigo() != null)) {
                codigo = copiaEntity.codigo();
            }
            else {
                codigo = service.salvar(copiaEntity);
            }
            codigos.add(codigo);

            if (codigoAnterior != null && (entityBuscada != null)) {
                entityBuscada.proximoLancamento(codigo);
                atualizarEntity(entityBuscada);
            }
            codigoAnterior = codigo;
        }

        return codigos;
    }

    private void atualizarEntity(final Contabilidade entity) throws StoreException, BusinessException, GenericException {
        entity.dataAtualizacao(LocalDate.now());
        ContabilidadeDTO dto = criarDTO(entity);
        super.atualizar(dto);
    }

    private void validaRecorrente(Contabilidade dto, Integer meses) throws BusinessException {
        if (dto.proximoLancamento() != null) {
            throw new BusinessException("A contabilidade já possui proximoLancamento.");
        }
    }

    private LocalDate buscarNovaDataDeVencimento(Contabilidade entity) {
        return getDateNextMonth(entity.dataVencimento());
    }

    private String buscarCodigoPai(ContabilidadeDTO dto) {
        String codigoPai = dto.codigoPai();
        if (ehPai(dto) && isBlankOrNull(codigoPai)) {
            codigoPai = dto.codigo();
        }
        return codigoPai;
    }

    private int primeiraParcela(ContabilidadeDTO dto) {
        return !isBlankOrNull(dto.parcela()) ? parseInt(dto.parcela()) - 1 : -1;
    }

    private void gravarCodigo(ContabilidadeDTO dto, String codigoPai, int i) {
        String codigo = dto.codigo();
        if (!ehPai(dto)) {
            if (!isBlankOrNull(codigoPai)) {
                codigo = valueOf(parseInt(codigoPai) + i);
            }
        }
        dto.codigo(codigo);
    }

    private void gravarCodigoPai(ContabilidadeDTO dto, String codigoPai, String parcela) {
        if (ehPai(dto)) {
            dto.codigoPai(null);
        }
        else {
            dto.codigoPai(codigoPai);
        }
    }

    private boolean ehPai(ContabilidadeDTO dto) {
        return !isBlankOrNull(dto.parcela()) && dto.parcela().equals("1");
    }

    private void excluirParcelas(ContabilidadeDTO dto) throws StoreException, ParseException, ServiceException {
        if (dto.parcelado().equals("S")) {
            List<Contabilidade> entities = buscarParcelasSeguintes(dto);
            for (Contabilidade entity : entities) {
                service.deletar(entity);
            }
        }
    }

    private List<Contabilidade> buscarParcelasSeguintes(ContabilidadeDTO dto) throws StoreException, ParseException {
        Long codigoPai = parseLong(buscarCodigoPai(dto));
        int parcelaAtual = parseInt(dto.parcela());
        return service.buscarParcelasSeguintesInclusivo(codigoPai, parcelaAtual);
    }

    private void excluirRecorrentes(ContabilidadeDTO dto) throws StoreException, ServiceException, ParseException {
        if (dto.recorrente().equals("S")) {
            long codigo = parseLong(dto.codigo());
            List<Contabilidade> entities = service.buscarTodasRecorrentesSeguintesInclusivo(codigo);
            for (Contabilidade entity : entities) {
                service.deletar(entity);
            }
            service.normalizarProximosLancamentos();
        }
    }

    private List<ContabilidadeDTO> criarListaDTO(List<Contabilidade> entities) {
        return super.criarListaDTO(dtoFactory, entities);
    }

    private ContabilidadeDTO criarDTO(Contabilidade entity) {
        return super.criarDTO(dtoFactory, entity);
    }

    private void conferirRecorrenteEParcelado(ContabilidadeDTO dto, List<String> errosCreate) {
        if (dto.recorrente().equals("S") && dto.parcelado().equals("S")) {
            errosCreate.add("Uma contabilidade não pode ser recorrente e parcelada.");
        }
    }

    private void conferirDataLancamentoAlterada(ContabilidadeDTO dto, Contabilidade entity, List<String> errosUpdate) {
        if ((entity != null) && datasLancamentoDiferentes(dto, entity)) {
            errosUpdate.add("O campo dataLançamento não pode ser alterado.");
        }
    }

    private void conferirParcelasAlteradas(ContabilidadeDTO dto, Contabilidade entity, List<String> errosUpdate) {
        if ((entity != null) && entity.parcelado() && parcelasDiferentes(dto, entity)) {
            errosUpdate.add("O campo parcelas não pode ser alterado.");
        }
    }

    private void conferirParcelaAlterada(ContabilidadeDTO dto, Contabilidade entity, List<String> errosUpdate) {
        if ((entity != null) && entity.parcelado() && parcelaDiferentes(dto, entity)) {
            errosUpdate.add("O campo parcela não pode ser alterado.");
        }
    }

    private void conferirCodigoPaiAlterado(ContabilidadeDTO dto, Contabilidade entity, List<String> errosUpdate) {
        if ((entity != null) && codigosPaiDiferentes(dto, entity)) {
            errosUpdate.add("O campo códigoPai não pode ser alterado.");
        }
    }

    private boolean datasLancamentoDiferentes(ContabilidadeDTO dto, Contabilidade entity) {
        return !dto.dataLancamento().equals(getStringFromDate(entity.dataLancamento()));
    }

    private boolean parcelasDiferentes(ContabilidadeDTO dto, Contabilidade entity) {
        return !dto.parcelas().equals(entity.parcelamento().parcelas().toString());
    }

    private boolean parcelaDiferentes(ContabilidadeDTO dto, Contabilidade entity) {
        return !dto.parcela().equals(entity.parcelamento().parcela().toString());
    }

    private boolean codigosPaiDiferentes(ContabilidadeDTO dto, Contabilidade entity) {
        if ((dto.codigoPai() == null) && (entity.codigoPai() == null)) {
            return false;
        }
        if ((dto.codigoPai() == null) || (entity.codigoPai() == null)) {
            return true;
        }
        return !dto.codigoPai().equals(entity.codigoPai().toString());
    }

    private boolean naoEhPai(Contabilidade entity) {
        return temParcela(entity) && naoEhPrimeiraParcela(entity);
    }

    private boolean temParcela(Contabilidade entity) {
        return entity.parcelamento().parcela() != null;
    }

    private boolean naoEhPrimeiraParcela(Contabilidade entity) {
        return !entity.parcelamento().parcela().equals(1);
    }

    private boolean usouCartao(ContabilidadeDTO dto) {
        return !isBlank(dto.usouCartao()) && getBooleanFromString(dto.usouCartao());
    }

    private boolean temCartao(ContabilidadeDTO dto) {
        return !isBlank(dto.cartao());
    }

    private boolean ehParcelado(ContabilidadeDTO dto) {
        return !isBlankOrNull(dto.parcelado()) && getBooleanFromString(dto.parcelado());
    }

    private boolean ehRecorrente(ContabilidadeDTO dto) {
        return !isBlankOrNull(dto.recorrente()) && getBooleanFromString(dto.recorrente());
    }

    private boolean naoTemParcela(ContabilidadeDTO dto) {
        return isBlank(dto.parcela());
    }

    private boolean naoTemParcelas(ContabilidadeDTO dto) {
        return isBlank(dto.parcelas());
    }

    private void atualizarParcelas(ContabilidadeDTO dto) throws StoreException, GenericException, ValidationException {
        String dataVencimento = dto.dataVencimento();
        String parcelasString = isBlankOrNull(dto.parcelas()) ? dto.parcela() : dto.parcelas();
        int parcelas = isBlankOrNull(parcelasString) ? 0 : parseInt(parcelasString);

        String codigoPai = buscarCodigoPai(dto);

        for (int i = primeiraParcela(dto); i < parcelas; i++) {
            dto.dataVencimento(dataVencimento);
            String parcela = isBlankOrNull(dto.parcela()) ? null : valueOf(i + 1);
            dto.parcela(parcela);

            gravarCodigo(dto, codigoPai, i);
            gravarCodigoPai(dto, codigoPai, parcela);

            atualizar(dto);
            dataVencimento = getStringNextMonth(dataVencimento);
        }
    }

    private void atualizarRecorrentes(ContabilidadeDTO dto) throws StoreException, ParseException, BusinessException, GenericException {
        Long codigo = isBlankOrNull(dto.codigo()) ? null : parseLong(dto.codigo());
        List<Contabilidade> entities = service.buscarTodasRecorrentesSeguintesInclusivo(codigo);

        if (CollectionUtils.isEmpty(entities)) {
            throw new BusinessException("Não foi possível buscar os registros recorrentes relacionados.");
        }

        List<ContabilidadeDTO> entitiesDTOS = criarListaDTO(entities);

        String dataVencimento = dto.dataVencimento();
        for (int i = 0; i < entitiesDTOS.size(); i++) {
            ContabilidadeDTO entityDTO = entitiesDTOS.get(i);

            ContabilidadeDTO dtoCreated = ContabilidadeDTOFactory
                    .create()
                    .withCodigo(entityDTO.codigo())
                    .withDataLancamento(entityDTO.dataLancamento())
                    .withDataAtualizacao(LocalDate.now())
                    .withDataVencimento(dataVencimento)
                    .withDataPagamento(entityDTO.dataPagamento())
                    .withRecorrente(entityDTO.recorrente())
                    .withGrupo(dto.grupo(), dto.subGrupo())
                    .withLocal(dto.local())
                    .withDescricao(dto.descricao())
                    .withConta(dto.conta())
                    .withTipo(entityDTO.tipo())
                    .withValor(dto.valor())
                    .withProximoLancamento(entityDTO.proximoLancamento())
                    .build();

            atualizar(dtoCreated);

            dataVencimento = getStringNextMonth(dataVencimento);
        }
    }

    private void conferirSeGrupoCadastrado(ContabilidadeDTO dto, List<String> errosCreate) throws StoreException, ParseException {
        Grupo grupo = grupoService.buscarPorNome(dto.grupo());
        if (grupo == null) {
            errosCreate.add("Grupo não cadastrado.");
        }
    }

    private void conferirSeSubGrupoCadastrado(ContabilidadeDTO dto, List<String> errosCreate) throws StoreException, ParseException {
        SubGrupo subGrupo = subGrupoService.buscarPorNome(dto.subGrupo());
        if (subGrupo == null) {
            errosCreate.add("SubGrupo não cadastrado.");
        }
    }

    private void conferirSeLocalCadastrado(ContabilidadeDTO dto, List<String> errosCreate) throws StoreException, ParseException {
        String localString = dto.local();
        if (!isBlankOrNull(localString)) {
            Local local = localService.buscarPorNome(localString);
            if (local == null) {
                errosCreate.add("Local não cadastrado.");
            }
        }
    }

    private void conferirSeCartaoCadastrado(ContabilidadeDTO dto, List<String> errosCreate) throws StoreException, ParseException {
        String cartaoString = dto.cartao();
        if (!isBlankOrNull(cartaoString)) {
            Cartao cartao = cartaoService.buscarPorNumero(cartaoString);
            if (cartao == null) {
                errosCreate.add("Cartão não cadastrado.");
            }
        }
    }

    private void conferirSeContaCadastrada(ContabilidadeDTO dto, List<String> errosCreate) throws StoreException, ParseException {
        Conta conta = contaService.buscarPorNome(dto.conta());
        if (conta == null) {
            errosCreate.add("Conta não cadastrada.");
        }
    }

    private void conferirSeGrupoESubGrupoEstaoAssociados(ContabilidadeDTO dto, List<String> errosCreate) throws StoreException, ParseException {
        Grupo grupo = grupoService.buscarPorNome(dto.grupo());
        if (grupo != null) {
            boolean subGrupoEncontrado = false;
            for (SubGrupo subGrupo : grupo.subGrupos()) {
                if (subGrupo.nome().equals(dto.subGrupo())) {
                    subGrupoEncontrado = true;
                    break;
                }
            }
            if (!subGrupoEncontrado) {
                errosCreate.add("Grupo e SubGrupo não estão associados.");
            }
        }
    }
}
