package br.com.accounting.rest.controller;

import br.com.accounting.rest.vo.ContaTransferenciaVO;
import br.com.accounting.rest.vo.ContaVO;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.accounting.core.util.Utils.getStringFromCurrentDate;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
public class ContaControllerTest extends GenericTest {
    @Autowired
    private ContaController controller;

    private MockMvc mvc;
    private Gson gson;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        gson = new Gson();
    }

    @Test
    public void criarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContaVO dto = getVOCarol();
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao salvar.")));
    }

    @Test
    public void criarSemNome() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")));
    }

    @Test
    public void criarSemNomeEDescricao() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null)
                .descricao(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")));
    }

    @Test
    public void criarSemNomeEDescricaoEValorDefault() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null)
                .descricao(null)
                .valorDefault(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")));
    }

    @Test
    public void criarSemCampos() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null)
                .descricao(null)
                .valorDefault(null)
                .cumulativo(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo cumulativo é obrigatório.")));
    }

    @Test
    public void criarComValorDefaultIncorreto() throws Exception {
        ContaVO dto = getVOCarol()
                .valorDefault("#500,00");
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"#500,00\"")));
    }

    @Test
    public void criarComRecorrenteIncorreto() throws Exception {
        ContaVO dto = getVOCarol()
                .cumulativo("SN");
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo cumulativo é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void criarDuplicado() throws Exception {
        ContaVO dto = getVOCarol();
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Conta duplicada.")));
    }

    @Test
    public void criar() throws Exception {
        criarContaCarol();
    }

    @Test
    public void atualizarSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContaVO vo = getVOCarol();
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar.")));
    }

    @Test
    public void atualizarSemNome() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")));
    }

    @Test
    public void atualizarSemNomeEDescricao() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null)
                .descricao(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(2)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")));
    }

    @Test
    public void atualizarSemNomeEDescricaoEValorDefault() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null)
                .descricao(null)
                .valorDefault(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(3)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")));
    }

    @Test
    public void atualizarSemCampos() throws Exception {
        ContaVO dto = getVOCarol()
                .nome(null)
                .descricao(null)
                .valorDefault(null)
                .cumulativo(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(4)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo nome é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[1]", is("O campo descrição é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[2]", is("O campo valorDefault é obrigatório.")))
                .andExpect(jsonPath("$.mensagens[3]", is("O campo cumulativo é obrigatório.")));
    }

    @Test
    public void atualizarSemCodigo() throws Exception {
        criarContaCarol();

        ContaVO vo = getVOCarol()
                .codigo(null);
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarSemSaldo() throws Exception {
        criarContaCarol();

        ContaVO dto = getVOCarol()
                .codigo("0")
                .saldo(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo saldo é obrigatório.")));
    }

    @Test
    public void atualizarSemDataAtualizacao() throws Exception {
        criarContaCarol();

        ContaVO dto = getVOCarol()
                .codigo("0")
                .dataAtualizacao(null);
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo dataAtualização é obrigatório.")));
    }

    @Test
    public void atualizarComValorDefaultIncorreto() throws Exception {
        criarContaCarol();

        ContaVO dto = getVOCarol()
                .codigo("0")
                .valorDefault("#500,00");
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("java.text.ParseException: Unparseable number: \"#500,00\"")));
    }

    @Test
    public void atualizarComRecorrenteIncorreto() throws Exception {
        ContaVO dto = getVOCarol()
                .cumulativo("SN");
        String json = gson.toJson(dto);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor do campo cumulativo é diferente de 'S' ou 'N'.")));
    }

    @Test
    public void atualizarProibidoAlterarCodigo() throws Exception {
        criarContaCarol();

        ContaVO vo = getVOCarol()
                .codigo("1");
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizar() throws Exception {
        criarContaCarol();

        ContaVO vo = getVOCarol()
                .codigo("0");
        String json = gson.toJson(vo);

        mvc.perform(put("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void transferirSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        ContaVO origem = getVOCarol();
        ContaVO destino = getVOEnok();

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(100.00);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao transferir saldos entre contas.")));
    }

    @Test
    public void transferirSemConta() throws Exception {
        ContaVO origem = getVOCarol();
        ContaVO destino = getVOEnok();

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(100.00);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void transferirSemCodigoNaOrigem() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo(null);
        ContaVO destino = getVOEnok();

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(100.00);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void transferirSemCodigoNoDestino() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo("0");
        ContaVO destino = getVOEnok()
                .codigo(null);

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(100.00);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void transferirSemSaldoOrigem() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo("0")
                .saldo(null);
        ContaVO destino = getVOEnok()
                .codigo("1");

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(100.0);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo saldo é obrigatório.")));
    }

    @Test
    public void transferirSemSaldoDestino() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo("0");
        ContaVO destino = getVOEnok()
                .codigo("1")
                .saldo(null);

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(100.0);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O campo saldo é obrigatório.")));
    }

    @Test
    public void transferirSemValor() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo("0");
        ContaVO destino = getVOEnok()
                .codigo("1");

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(null);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor deve ser maior do que 0.")));
    }

    @Test
    public void transferirComValorZero() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo("0");
        ContaVO destino = getVOEnok()
                .codigo("1");

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(0.0);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor deve ser maior do que 0.")));
    }

    @Test
    public void transferirComValorNegativo() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo("0");
        ContaVO destino = getVOEnok()
                .codigo("1");

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(-1.0);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("O valor deve ser maior do que 0.")));
    }

    @Test
    public void transferirComValorMaiorQueSaldoOrigem() throws Exception {
        criarContaCarol();
        criarContaEnok();

        ContaVO origem = getVOCarol()
                .codigo("0");
        ContaVO destino = getVOEnok()
                .codigo("1");

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(1000.00);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Saldo insuficiente.")));
    }

    @Test
    public void transferir() throws Exception {
        criarContaCarol();
        criarContaEnok();

        String codigoOrigem = "0";
        ContaVO origem = getVOCarol()
                .codigo(codigoOrigem);

        String codigoDestino = "1";
        ContaVO destino = getVOEnok()
                .codigo(codigoDestino);

        ContaTransferenciaVO transferencia = new ContaTransferenciaVO()
                .origem(origem)
                .destino(destino)
                .valor(500.00);
        String json = gson.toJson(transferencia);

        mvc.perform(put("/conta/transferencia")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        mvc.perform(get("/conta/{codigo}", codigoOrigem)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.nome", is("CAROL")))
                .andExpect(jsonPath("$.descricao", is("Valores passados para a Carol")))
                .andExpect(jsonPath("$.valorDefault", is("500,00")))
                .andExpect(jsonPath("$.saldo", is("0,00")))
                .andExpect(jsonPath("$.cumulativo", is("S")))
                .andExpect(jsonPath("$.dataAtualizacao", is(getStringFromCurrentDate())));

        mvc.perform(get("/conta/{codigo}", codigoDestino)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("1")))
                .andExpect(jsonPath("$.nome", is("ENOK")))
                .andExpect(jsonPath("$.descricao", is("Valores passados para o Enok")))
                .andExpect(jsonPath("$.valorDefault", is("500,00")))
                .andExpect(jsonPath("$.saldo", is("1.000,00")))
                .andExpect(jsonPath("$.cumulativo", is("S")))
                .andExpect(jsonPath("$.dataAtualizacao", is(getStringFromCurrentDate())));
    }

    @Test
    public void atualizarCumulativasSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        mvc.perform(put("/conta/cumulativa")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao atualizar as contas cumulativas.")));
    }

    @Test
    public void atualizarCumulativasSemRegistros() throws Exception {
        mvc.perform(put("/conta/cumulativa")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void atualizarCumulativas() throws Exception {
        criarContaCarol();

        mvc.perform(put("/conta/cumulativa")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        String codigo = "0";

        mvc.perform(delete("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao excluir.")));
    }

    @Test
    public void excluirSemCodigo() throws Exception {
        String codigo = null;

        mvc.perform(delete("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(delete("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void excluirInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(delete("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void excluir() throws Exception {
        criarContaCarol();

        String codigo = "0";

        mvc.perform(delete("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        String codigo = "0";

        mvc.perform(get("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar por código.")));
    }

    @Test
    public void buscarPorCodigoSemCodigo() throws Exception {
        String codigo = null;

        mvc.perform(get("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoComCodigoIncorreto() throws Exception {
        String codigo = "a";

        mvc.perform(get("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarPorCodigoInexistente() throws Exception {
        String codigo = "0";

        mvc.perform(get("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(417)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Registro inexistente.")));
    }

    @Test
    public void buscarPorCodigo() throws Exception {
        criarContaCarol();

        String codigo = "0";

        mvc.perform(get("/conta/{codigo}", codigo)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is("0")))
                .andExpect(jsonPath("$.nome", is("CAROL")))
                .andExpect(jsonPath("$.descricao", is("Valores passados para a Carol")))
                .andExpect(jsonPath("$.valorDefault", is("500,00")))
                .andExpect(jsonPath("$.saldo", is("500,00")))
                .andExpect(jsonPath("$.cumulativo", is("S")))
                .andExpect(jsonPath("$.dataAtualizacao", is(getStringFromCurrentDate())));
    }

    @Test
    public void buscarTudoSemDiretorio() throws Exception {
        deletarDiretorioEArquivos();

        mvc.perform(get("/conta")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInsufficientStorage())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(507)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Erro de persistência ao buscar tudo.")));
    }

    @Test
    public void buscarTudoInexistente() throws Exception {
        mvc.perform(get("/conta")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void buscarTudo() throws Exception {
        criarContaCarol();

        mvc.perform(get("/conta")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].codigo", is("0")))
                .andExpect(jsonPath("$[0].nome", is("CAROL")))
                .andExpect(jsonPath("$[0].descricao", is("Valores passados para a Carol")))
                .andExpect(jsonPath("$[0].valorDefault", is("500,00")))
                .andExpect(jsonPath("$[0].saldo", is("500,00")))
                .andExpect(jsonPath("$[0].cumulativo", is("S")))
                .andExpect(jsonPath("$[0].dataAtualizacao", is(getStringFromCurrentDate())));
    }

    private ContaVO getVOCarol() {
        return new ContaVO()
                .nome("CAROL")
                .descricao("Valores passados para a Carol")
                .valorDefault("500,00")
                .saldo("500,00")
                .cumulativo("S")
                .dataAtualizacao(getStringFromCurrentDate());
    }

    private ContaVO getVOEnok() {
        return new ContaVO()
                .nome("ENOK")
                .descricao("Valores passados para o Enok")
                .valorDefault("500,00")
                .saldo("500,00")
                .cumulativo("S")
                .dataAtualizacao(getStringFromCurrentDate());
    }

    private void criarContaCarol() throws Exception {
        ContaVO dto = getVOCarol();
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));
    }

    private void criarContaEnok() throws Exception {
        ContaVO dto = getVOEnok();
        String json = gson.toJson(dto);

        mvc.perform(post("/conta")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(1)));
    }
}
