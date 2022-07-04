package br.com.dadosabertosuffs.entity.httpresponse;

import lombok.Getter;

/** Retorno do endpoint GET /package_show do Portal de Dados Abertos da UFFS */
@Getter
public class DatasetShowResponse {
    
    private DatasetShowResponseResult result;
}
