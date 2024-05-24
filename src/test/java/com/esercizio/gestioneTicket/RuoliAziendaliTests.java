package com.esercizio.gestioneTicket;

import com.esercizio.gestioneTicket.controllers.RuoliAziendaliController;
import com.esercizio.gestioneTicket.model.RuoliAziendali;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(value="test")
class RuoliAziendaliTests {
	@Autowired
	private RuoliAziendaliController ruoliAziendaliController;


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
		assertThat(ruoliAziendaliController).isNotNull();
	}

	// METODO INTERNO A MO' DI COSTRUTTORE
	public RuoliAziendali crea (Long id, String descrizione, Boolean valido){
		return RuoliAziendali.builder()
				.id(id)
				.descrizione(descrizione)
				.valido(valido)
				.build();
	}


	@Test
	// TEST -> CREAZIONE RUOLO AZIENDALE CORRETTA
	public void creaRuoliAziendaliMetodo1() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/new")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(crea(18L,"Prova Giusta 3",true))))
				.andDo(print()).andExpect(status().isOk())
		;
	}
	@Test
	// TEST -> CREAZIONE RUOLO AZIENDALE SBAGLIATA - CATCH PER CAMPO DESCRIZIONE AVENTE UN NUMERO
	// IllegalArgumentException
	public void creaRuoliAziendaliMetodo2() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/new")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(crea(19L,"1233",true))))
				.andDo(print()).andExpect(status().isOk())
		;
	}
	@Test
	// TEST -> CREAZIONE RUOLO TEAM SBAGLIATA - CATCH PER DESCRIZIONE GIA' PRESENTE NEL DB
	// EntityExistsException
	public void creaRuoliAziendaliMetodo3() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/new")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(crea(20L,"Prova Giusta 2",true))))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
	// TEST -> CREAZIONE RUOLO TEAM SBAGLIATA - CATCH PER DESCRIZIONE NULL
	// IllegalArgumentException
	public void creaRuoliAziendaliMetodo4() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/new")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(crea(21L,"",true))))
				.andDo(print()).andExpect(status().isOk())
		;
	}



/*
	public RuoliAziendali editRuoliAziendali (){
		return RuoliAziendali.builder().id(202L).descrizione("Pigiamelli").valido(true)
				.build();
	}



	@Test
	public void editRuoliAziendaliMetodo() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/edit?id=202")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(editRuoliAziendali())))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
	public void getAllOrdineIdRuoliAziendali() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/findAllOrdineId"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
	public void getAllOrdineDescrizioneRuoliAziendali() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/findAllOrdineDescrizione"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
	public void getAllTrueRuoliAziendali() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/findAllTrue"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
	public void switchValidazioneRuoliAziendali() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/switchValidazione/1"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
	public void findByIdRuoliAziendali() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/findById/1"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
	public void findRuoloByDescrizioneRuoliAziendali() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloAziendale/findByString/fonico"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

 */





}
