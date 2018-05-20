package br.com.accounting.conta.business.impl;

import br.com.accounting.commons.annotation.History;
import br.com.accounting.commons.exception.*;
import br.com.accounting.commons.exception.StoreException;
import br.com.accounting.commons.entity.Conta;
import br.com.accounting.commons.service.impl.GenericAbstractBusiness;
import br.com.accounting.conta.business.ContaBusiness;
import br.com.accounting.conta.dto.ContaDTO;
import br.com.accounting.conta.factory.ContaDTOFactory;
import br.com.accounting.conta.factory.ContaFactory;
import br.com.accounting.conta.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static br.com.accounting.commons.util.Utils.getDoubleFromString;
import static br.com.accounting.commons.util.Utils.isMonthChanged;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.*;

@Service
public class ContaBusinessImpl extends GenericAbstractBusiness<ContaDTO, Conta> implements ContaBusiness {
    private ContaService service;

    @Autowired
    public ContaBusinessImpl(final ContaService service) {
        super(service, ContaDTOFactory.create());
        this.service = service;
    }

    @History
    @Override
    public void adicionarCredito(final ContaDTO dto, final Double credito) throws StoreException, GenericException {
        try {
            Conta entity = criarEntity(dto);
            entity.dataAtualizacao(LocalDate.now());
            service.atualizarSaldo(entity, credito);
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao adicionar crédito.", e);
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @History
    @Override
    public void adicionarDebito(final ContaDTO dto, final Double debito) throws StoreException, GenericException {
        try {
            Conta entity = criarEntity(dto);
            entity.dataAtualizacao(LocalDate.now());
            service.atualizarSaldo(entity, -debito);
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao adicionar débito.", e);
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @History
    @Override
    public void transferir(final ContaDTO origem, final ContaDTO destino, final Double valor) throws StoreException, BusinessException, GenericException {
        try {
            validarUpdate(origem);
            validarUpdate(destino);

            Double saldoOrigem = getDoubleFromString(origem.saldo());

            if ((valor == null) || (valor <= 0)) {
                throw new ValidationException("O valor deve ser maior do que 0.");
            }
            if (saldoOrigem < valor) {
                throw new BusinessException("Saldo insuficiente.");
            }

            adicionarDebito(origem, valor);
            adicionarCredito(destino, valor);
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao transferir saldos entre contas.", e);
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @Override
    public void atualizarCumulativas() throws StoreException, BusinessException, GenericException {
        try {
            List<Conta> entitiesBuscadas = service.buscarCumulativas();
            if (isEmpty(entitiesBuscadas)) {
                throw new BusinessException("Registro inexistente.");
            }
            for (Conta entity : entitiesBuscadas) {
                LocalDate dataAtualizacao = entity.dataAtualizacao();
                if (isMonthChanged(dataAtualizacao)) {
                    Double novoSaldo = entity.valorDefault() + entity.saldo();
                    entity.saldo(novoSaldo);
                    entity.dataAtualizacao(LocalDate.now());
                    service.atualizar(entity);
                }
            }
        }
        catch (StoreException e) {
            throw new StoreException("Erro de persistência ao atualizar as contas cumulativas.", e);
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GenericException(e);
        }
    }

    @Override
    public void validarEntrada(final ContaDTO dto, final List<String> erros) throws MissingFieldException, CreateException {
        if (isBlank(dto.nome())) {
            erros.add(format(msg, "nome"));
        }
        if (isBlank(dto.descricao())) {
            erros.add(format(msg, "descrição"));
        }
        if (isBlank(dto.valorDefault())) {
            erros.add(format(msg, "valorDefault"));
        }
        if (isBlank(dto.cumulativo())) {
            erros.add(format(msg, "cumulativo"));
        }
        conferirErrosCamposObrigatorios(erros);

        List<String> errosCreate = new ArrayList<>();

        conferirValorBooleano(errosCreate, dto.cumulativo(), "cumulativo");

        conferirErrosCreate(errosCreate);
    }

    @Override
    public void validarEntradaUpdate(final ContaDTO dto, final Conta entity, final List<String> erros) throws ValidationException {
        if (isBlank(dto.saldo())) {
            erros.add(format(msg, "saldo"));
        }
        if (isBlank(dto.dataAtualizacao())) {
            erros.add(format(msg, "dataAtualização"));
        }
        conferirErrosCamposObrigatorios(erros);

        List<String> errosUpdate = new ArrayList<>();
        conferirErrosUpdate(errosUpdate);
    }

    @Override
    public void validaRegistroDuplicado(final Conta conta) throws StoreException, ParseException, DuplicatedRegistryException {
        Conta contaBuscada = service.buscarPorNome(conta.nome());

        if (conta.equals(contaBuscada)) {
            throw new DuplicatedRegistryException("Conta duplicada.");
        }
    }

    @Override
    public Conta criarEntity(final ContaDTO dto) throws ValidationException {
        try {
            ContaFactory factory = ContaFactory
                    .begin();
            preenderFactory(factory, dto);
            return factory
                    .build();
        }
        catch (DateTimeParseException | ParseException | IllegalArgumentException e) {
            throw new ValidationException(e);
        }
    }

    @Override
    protected Conta criarEntity(ContaDTO dto, Conta entityBuscado) throws ValidationException {
        try {
            ContaFactory factory = ContaFactory
                    .begin()
                    .preencherCamposBuscados(entityBuscado);
            preenderFactory(factory, dto);
            return factory
                    .build();
        }
        catch (DateTimeParseException | ParseException | IllegalArgumentException e) {
            throw new ValidationException(e);
        }
    }

    private void preenderFactory(final ContaFactory factory, final ContaDTO dto) throws ParseException {
        factory
                .withCodigo(dto.codigo())
                .withNome(dto.nome())
                .withDescricao(dto.descricao())
                .withValorDefault(dto.valorDefault())
                .withSaldo(dto.saldo())
                .withCumulativo(dto.cumulativo())
                .withDataAtualizacao(dto.dataAtualizacao());
    }
}
