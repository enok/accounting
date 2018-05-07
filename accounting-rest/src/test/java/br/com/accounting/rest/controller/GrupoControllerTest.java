package br.com.accounting.rest.controller;

import br.com.accounting.business.dto.GrupoDTO;
import com.google.gson.Gson;
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
public class GrupoControllerTest extends GenericTest {
    @Autowired
    private GrupoController controller;

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

        GrupoDTO dto = getDTO();
        String json = gson.toJson(dto);

        mvc.perform(post("/grupo")
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
        GrupoDTO dto = getDTO()
                .nome(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/grupo")
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
        GrupoDTO dto = getDTO()
                .nome(null)
                .descricao(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/grupo")
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
    public void criarSemCampos() throws Exception {
        GrupoDTO dto = getDTO()
                .nome(null)
                .descricao(null)
                .subGrupos(null);
        String json = gson.toJson(dto);

        mvc.perform(post("/grupo")
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
                .andExpect(jsonPath("$.mensagens[2]", is("O campo subGrupos é obrigatório.")));
    }

    @Test
    public void criarDuplicado() throws Exception {
        GrupoDTO dto = getDTO();
        String json = gson.toJson(dto);

        mvc.perform(post("/grupo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigos", hasSize(1)))
                .andExpect(jsonPath("$.codigos[0]", is(0)));

        mvc.perform(post("/grupo")
                .characterEncoding("UTF-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo", is(400)))
                .andExpect(jsonPath("$.mensagens", hasSize(1)))
                .andExpect(jsonPath("$.mensagens[0]", is("Grupo duplicado.")));
    }

    @Test
    public void criar() throws Exception {
        GrupoDTO dto = getDTO();
        String json = gson.toJson(dto);

        mvc.perform(post("/grupo")
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

    private GrupoDTO getDTO() {
        return new GrupoDTO()
                .nome("Saúde")
                .descricao("Gastos gerais com saúde")
                .addSubGrupo("Suplementos");
    }
}
