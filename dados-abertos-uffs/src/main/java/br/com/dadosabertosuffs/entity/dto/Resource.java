package br.com.dadosabertosuffs.entity.dto;

import java.util.List;

import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResultField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Resource {
    private String id;
    private String nome;
    private List<ResourceResponseResultField> campos;
}
