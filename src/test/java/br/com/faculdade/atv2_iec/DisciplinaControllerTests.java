package br.com.faculdade.atv2_iec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DisciplinaControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /disciplinas retorna lista inicial com 2 itens")
	void listarTodas() throws Exception {
		mockMvc.perform(get("/disciplinas"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
				.andExpect(jsonPath("$[0].id", notNullValue()))
				.andExpect(jsonPath("$[0].nome", not(isEmptyOrNullString())));
	}

	@Test
	@DisplayName("GET /disciplinas/{id} retorna 404 quando não existe")
	void buscarPorIdNotFound() throws Exception {
		mockMvc.perform(get("/disciplinas/999999"))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("POST /disciplinas cria uma nova disciplina")
	void criarNovaDisciplina() throws Exception {
		String body = "{\"nome\":\"Banco de Dados\"}";
		mockMvc.perform(post("/disciplinas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString("/disciplinas/")))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.nome", is("Banco de Dados")));
	}
}


