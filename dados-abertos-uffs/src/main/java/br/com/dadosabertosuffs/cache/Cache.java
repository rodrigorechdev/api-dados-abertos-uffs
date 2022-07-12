package br.com.dadosabertosuffs.cache;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import lombok.Getter;

@Getter
@Service
public class Cache {
    private final Integer horasCache = 1;
    private LocalDateTime ultimaAtualizacao = null;

    private HashMap<String, ResourceEstrutura> hashRecursoEstruturaPorDataset = null;

    public boolean atualizacaoNecessaria() {
        return ultimaAtualizacao == null || 
                ChronoUnit.HOURS.between(ultimaAtualizacao, LocalDateTime.now()) >= horasCache;
    }

    public void atualizarCache(HashMap<String, ResourceEstrutura> hashRecursoEstruturaPorDataset) {
        this.ultimaAtualizacao = LocalDateTime.now();
        this.hashRecursoEstruturaPorDataset = hashRecursoEstruturaPorDataset;
    }

}
