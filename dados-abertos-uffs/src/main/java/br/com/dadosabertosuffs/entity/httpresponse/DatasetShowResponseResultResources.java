package br.com.dadosabertosuffs.entity.httpresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.dadosabertosuffs.entity.dto.Resource;
import lombok.Getter;

@Getter
public class DatasetShowResponseResultResources {
    
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    public Resource toResource() {
        return Resource.builder()
            .id(id)
            .nome(name)
            .build();
    }
}
