package br.com.dadosabertosuffs.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ColunasRelacionadas {
    String nomeDataset;
    String nomeCampo;
}
