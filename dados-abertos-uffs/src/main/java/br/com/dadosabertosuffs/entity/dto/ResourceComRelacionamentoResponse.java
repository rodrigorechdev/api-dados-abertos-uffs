package br.com.dadosabertosuffs.entity.dto;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;

import br.com.dadosabertosuffs.mapper.Mapper;
import lombok.Getter;

@Getter
public class ResourceComRelacionamentoResponse {
    HashMap<String, String> camposOriginais;
    JsonArray camposRelacionados;//JsonArray de lista de ResourceRelacionado.class

    public ResourceComRelacionamentoResponse(HashMap<String, String> camposOriginais, List<ResourceRelacionado> camposRelacionados) {
        this.camposOriginais = camposOriginais;
        this.camposRelacionados = Mapper.listResourceRelacionadoToJsonArray(camposRelacionados);
    }
}
