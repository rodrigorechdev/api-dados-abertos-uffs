package br.com.dadosabertosuffs.entity.httpresponse;

import br.com.dadosabertosuffs.entity.dto.Resource;
import lombok.Getter;

@Getter
public class DatasetShowResponseResultResources {
    
    private String id;

    private String name;

    public Resource toResource() {
        return Resource.builder()
            .id(id)
            .nome(name)
            .build();
    }
}
