package br.com.dadosabertosuffs.entity.httpresponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class DatasetShowResponseResult {
    
    @JsonProperty("resources")
    private List<DatasetShowResponseResultResources> resources;
}
