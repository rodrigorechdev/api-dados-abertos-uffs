package br.com.dadosabertosuffs.entity.httpresponse;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ResourceResponse {
    private String help;
    private boolean success;
    private ResourceResponseResult result;
    
}
