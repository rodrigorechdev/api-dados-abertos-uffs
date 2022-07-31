package br.com.dadosabertosuffs.entity.dto;

import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonArray;

import br.com.dadosabertosuffs.mapper.Mapper;
import lombok.Getter;

@Getter
public class ResourceWithRelationshipResponse {
    HashMap<String, String> mainResource;
    JsonArray relatedResources;//JsonArray de lista de ResourceRelacionado.class

    public ResourceWithRelationshipResponse(HashMap<String, String> mainResource, List<ResourceRelacionado> relatedResources) {
        this.mainResource = mainResource;
        this.relatedResources = Mapper.listResourceRelacionadoToJsonArray(relatedResources);
    }
}
