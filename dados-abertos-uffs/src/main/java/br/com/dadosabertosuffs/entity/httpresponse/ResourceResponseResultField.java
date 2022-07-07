package br.com.dadosabertosuffs.entity.httpresponse;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class ResourceResponseResultField {

    private String type; 

    private String id;
}
