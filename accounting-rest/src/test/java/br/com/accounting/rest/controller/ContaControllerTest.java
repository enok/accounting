package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.ContaDTO;
import br.com.accounting.business.dto.ContabilidadeDTO;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        ContaDTO dto = getDTO();
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
                .andExpect(jsonPath("$.mensagens[0]", is("Não foi possível buscar os registros.")));
    }

    @Test
    public void criarSemNome() throws Exception {
        ContaDTO dto = getDTO()
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
        ContaDTO dto = getDTO()
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
        ContaDTO dto = getDTO()
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
        ContaDTO dto = getDTO()
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
        ContaDTO dto = getDTO()
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
        ContaDTO dto = getDTO()
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
        ContaDTO dto = getDTO();
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
        ContaDTO dto = getDTO();
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

    private ContaDTO getDTO() {
        return new ContaDTO()
                .nome("CAROL")
                .descricao("Valores passados para a Carol")
                .valorDefault("500,00")
                .cumulativo("S");
    }
}
