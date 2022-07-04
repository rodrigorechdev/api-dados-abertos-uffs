package br.com.dadosabertosuffs.entity.httpresponse;

import java.util.List;

import lombok.Getter;

@Getter
public class DatasetShowResponseResult {
    
    private List<DatasetShowResponseResultResources> resources;
}
