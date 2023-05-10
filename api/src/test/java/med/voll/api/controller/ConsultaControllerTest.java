package med.voll.api.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.api.domain.consulta.AgendaDeConsulta;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsulta agendaDeConsulta;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void agendar_cenario1() throws Exception{
       
        var response = mvc.perform(MockMvcRequestBuilders.post("/agendar")).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao invalidas")
    @WithMockUser
    void agendar_cenario2() throws IOException, Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        long idMedico = 21;
        Long idPaciente = (long) 51;
        var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosAgendamentoConsultaJson
                                .write(new DadosAgendamentoConsulta(idMedico,idPaciente,data,especialidade))
                                .getJson())
                            ).andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(new DadosDetalhamentoConsulta(null, idMedico, idPaciente, data)).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}
