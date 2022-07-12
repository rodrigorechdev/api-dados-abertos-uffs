package br.com.dadosabertosuffs.entity.httpresponse;

import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import lombok.Getter;

@Getter
public class DatasetShowResponseResultResources {
    
    private String id;

    private String name;

    public ResourceEstrutura toResourceEstrutura() {
        return ResourceEstrutura.builder()
            .id(id)
            .nome(name)
            .build();
    }
}
