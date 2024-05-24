package com.esercizio.gestioneTicket;

import com.esercizio.gestioneTicket.controllers.RuoliTeamsController;
import com.esercizio.gestioneTicket.model.RuoliAziendali;
import com.esercizio.gestioneTicket.model.RuoliTeams;
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
class RuoliTeamsTests {
    @Autowired
    private RuoliTeamsController ruoliTeamsController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        assertThat(ruoliTeamsController).isNotNull();
    }

    // METODO INTERNO A MO' DI COSTRUTTORE
    public RuoliTeams crea (Long id, String descrizione, Boolean valido){
        return RuoliTeams.builder()
                .id(id)
                .descrizione(descrizione)
                .valido(valido)
                .build();
    }

    @Test
    // TEST -> CREAZIONE RUOLO TEAM CORRETTA
    public void creaRuoliTeamsMetodo1() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(37L,"Prova Giusta 37",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> CREAZIONE RUOLO TEAM SBAGLIATA - CATCH PER CAMPO DESCRIZIONE AVENTE UN NUMERO
    // IllegalArgumentException
    public void creaRuoliTeamsMetodo2() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(35L,"1233",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> CREAZIONE RUOLO TEAM SBAGLIATA - CATCH PER DESCRIZIONE GIA' PRESENTE NEL DB
    // EntityExistsException
    public void creaRuoliTeamsMetodo3() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(35L,"Prova Giusta 30",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> CREAZIONE RUOLO TEAM SBAGLIATA - CATCH PER DESCRIZIONE NULL
    // IllegalArgumentException
    public void creaRuoliTeamsMetodo4() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(35L,"",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> MODIFICA RUOLO TEAM CORRETTA
    public void modificaRuoliTeamsMetodo1() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/edit?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(1L,"Prova Modifica 6",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> MODIFICA RUOLO TEAM SBAGLIATA - CATCH PER ID = 0
    // IllegalArgumentException
    public void modificaRuoliTeamsMetodoIDZERO() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/edit?id=0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(0L,"Michelangelo",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> MODIFICA RUOLO TEAM SBAGLIATA - CATCH PER CAMPO DESCRIZIONE AVENTE UN NUMERO
    // IllegalArgumentException
    public void modificaRuoliTeamsMetodo2() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/edit?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(1L,"456",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> MODIFICA RUOLO TEAM SBAGLIATA - CATCH PER DESCRIZIONE GIA' PRESENTE NEL DB
    // EntityExistsException
    public void modificaRuoliTeamsMetodo3() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/edit?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(1L,"Prova Giusta 30",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> MODIFICA RUOLO TEAM SBAGLIATA - CATCH PER DESCRIZIONE NULL
    // IllegalArgumentException
    public void modificaRuoliTeamsMetodo4() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/edit?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(1L,"",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

    @Test
    // TEST -> MODIFICA RUOLO TEAM SBAGLIATA - CATCH PER ID NON PRESENTE PRESENTE NEL DB
    // EntityExistsException
    public void modificaRuoliTeamsMetodo5() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/edit?id=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crea(10L,"ID NON PRESENTE",true))))
                .andDo(print()).andExpect(status().isOk())
        ;
    }



	@Test
    // TEST -> LISTA RUOLI TEAMS IN ORDINE DI ID
	public void getAllOrdineIdRuoliTeams() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/findAllOrdineId"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

	@Test
    // TEST -> LISTA RUOLI TEAMS IN ORDINE DI DESCRIZIONE
	public void getAllOrdineDescrizioneRuoliTeams() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/findAllOrdineDescrizione"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

    @Test
    // TEST -> CAMBIO BOOLEANO DI VALIDO - ID CORRETTO DA VERO A FALSO
    public void switchValidazioneRuoliTeams1() throws Exception{
        // IMPOSTO A FALSO
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/switchValidazione/1"))
                .andDo(print()).andExpect(status().isOk())
        ;
        // lo resetto a VERO
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/switchValidazione/1"))
                .andDo(print()).andExpect(status().isOk())
        ;
    }


    @Test
    // TEST -> CAMBIO BOOLEANO DI VALIDO - ID VALORE ERRATO
    public void switchValidazioneRuoliTeams2() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/switchValidazione/0"))
                .andDo(print()).andExpect(status().isOk())
        ;
    }
    @Test
    // TEST -> CAMBIO BOOLEANO DI VALIDO - ID NON ESISTE NEL DB
    public void switchValidazioneRuoliTeams3() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/switchValidazione/10"))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

	@Test
    // TEST -> LISTA RUOLI CON VALIDO=TRUE
	public void getAllTrueRuoliTeam() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/findAllTrue"))
				.andDo(print()).andExpect(status().isOk())
		;
	}



	@Test
    // TEST-> TROVO RUOLO CON IL SUO ID - CORRETTO ID
	public void findByIdRuoliTeams1() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/findById/1"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

    @Test
    // TEST-> TROVO RUOLO CON IL SUO ID - ID FORMATO ERRATO
    public void findByIdRuoliTeams2() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/findById/0"))
                .andDo(print()).andExpect(status().isOk())
        ;
    }

	@Test
	public void findRuoloByDescrizioneRuoliTeams() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/ruoloTeam/findByString/gian"))
				.andDo(print()).andExpect(status().isOk())
		;
	}

}
