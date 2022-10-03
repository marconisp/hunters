package br.com.jhisolution.user.hunters.web.rest.dto;

import java.time.LocalDate;
import java.util.List;

public class RelatorioReceberDTO {

    public LocalDate dataInicio;
    public LocalDate dateFim;
    public List<ReceberDTO> recebers;

    public RelatorioReceberDTO() {}

    public RelatorioReceberDTO(LocalDate dataInicio, LocalDate dateFim, List<ReceberDTO> recebers) {
        super();
        this.dataInicio = dataInicio;
        this.dateFim = dateFim;
        this.recebers = recebers;
    }

    public static RelatorioReceberDTO getInstance(LocalDate dataInicio, LocalDate dateFim, List<ReceberDTO> recebers) {
        return new RelatorioReceberDTO(dataInicio, dateFim, recebers);
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDateFim() {
        return dateFim;
    }

    public void setDateFim(LocalDate dateFim) {
        this.dateFim = dateFim;
    }

    public List<ReceberDTO> getRecebers() {
        return recebers;
    }

    public void setRecebers(List<ReceberDTO> recebers) {
        this.recebers = recebers;
    }
}
