package br.com.dadosabertosuffs.entity.httpresponse;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ResourceResponseFieldResult {

    private String type; 

    private String id;
}
