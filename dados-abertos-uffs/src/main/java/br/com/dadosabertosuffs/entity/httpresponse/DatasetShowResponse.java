package br.com.dadosabertosuffs.entity.httpresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/** Retorno do endpoint GET /package_show do Portal de Dados Abertos da UFFS */
@Getter
public class DatasetShowResponse {
    
    @JsonProperty("result")
    private DatasetShowResponseResult result;
}
