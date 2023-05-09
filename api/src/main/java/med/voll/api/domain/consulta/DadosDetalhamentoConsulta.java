package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosDetalhamentoConsulta(Long id, 
                                        @JsonAlias({"id_medico", "medico_id"}) Long idMedico, 
                                        @JsonAlias({"id_paciente", "paciente_id"}) Long idPaciente, 
                                        LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
