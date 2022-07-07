package br.com.dadosabertosuffs.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ColunasRelacionadas {
    String nomeDataset;
    String nomeCampo;
}
