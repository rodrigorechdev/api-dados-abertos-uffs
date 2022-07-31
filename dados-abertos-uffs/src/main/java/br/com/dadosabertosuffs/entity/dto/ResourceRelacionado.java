package br.com.dadosabertosuffs.entity.dto;

import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResourceRelacionado {
    private String datasetName;
    private String fieldName;
    private ResourceResponse content;
}
