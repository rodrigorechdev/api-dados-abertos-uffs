package br.com.dadosabertosuffs.entity.dto;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResourceRelacionado {
    private String nomeDataset;
    private String nomeCampoRelacionado;
    private JSONObject conteudoRecurso;
}
