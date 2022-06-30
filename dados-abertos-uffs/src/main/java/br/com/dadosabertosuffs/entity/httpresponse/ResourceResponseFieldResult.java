package br.com.dadosabertosuffs.entity.httpresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ResourceResponseFieldResult {

    @JsonProperty("type")
    private String type;
    
    @JsonProperty("id")
    private String id;
}
