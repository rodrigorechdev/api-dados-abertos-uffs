package br.com.dadosabertosuffs.entity.httpresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class DatasetShowResponseResultResources {
    
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

}
