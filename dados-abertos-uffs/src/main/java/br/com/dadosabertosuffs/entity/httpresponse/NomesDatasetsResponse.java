package br.com.dadosabertosuffs.entity.httpresponse;

import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class NomesDatasetsResponse {
    
    private List<String> result;
}
