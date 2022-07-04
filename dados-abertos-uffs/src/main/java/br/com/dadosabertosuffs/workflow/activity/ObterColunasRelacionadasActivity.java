package br.com.dadosabertosuffs.workflow.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.ColunasRelacionadas;
import br.com.dadosabertosuffs.entity.dto.Resource;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterColunasRelacionadasActivity {
    
    /**
     * Varre todas as colunas de todos os recursos recebido por parametro e encontra
     * os campos com mesmo nome.
     * @param hashRecursosPorDataset
     */
    public HashMap<String, List<ColunasRelacionadas>> execute(HashMap<String, Resource> hashRecursosPorDataset) {
        HashMap<String, List<ColunasRelacionadas>> hashColunasRelacionadas = new HashMap<>();
        
        hashRecursosPorDataset.forEach(
            (nomeDataset, resource1) -> {
                hashRecursosPorDataset.forEach(
                    (nomeDataset2, resource2) -> {
                        if(!nomeDataset.equals(nomeDataset2)) {
                            resource1.getCampos().forEach(
                                (campo1) -> {
                                    resource2.getCampos().forEach(
                                        (campo2) -> {
                                            //Não dá pra saber se um campo realmente serve como foreign key pq existem nomes com campos iguals que não são fk, por exemplo "data" e podem haver outros. O código n pode identificar isso dinamicamente
                                            //Não dá pra saber pq mesmo sabendo que o campo é fk, se outras X tabelas tbm tiverem esse campo, não sabemos qual é a tabela que tem esse campo orinalmente. Nos conectariamos com todas as outras.
                                            if(!campo2.getId().equals("_id") && campo1.getId().equals(campo2.getId())) {  
                                                var novaColunaRelacionada = ColunasRelacionadas.builder()
                                                                                .nomeDataset(nomeDataset2)
                                                                                .nomeCampo(campo2.getId())
                                                                                .build();
                                                if(hashColunasRelacionadas.get(nomeDataset) == null) {
                                                    var novaLista = new ArrayList<ColunasRelacionadas>();
                                                    novaLista.add(novaColunaRelacionada);
                                                    hashColunasRelacionadas.put(nomeDataset, novaLista);
                                                } else {
                                                    hashColunasRelacionadas.get(nomeDataset).add(novaColunaRelacionada);
                                                }
                                            }
                                        }
                                    );
                                }
                            );
                        }
                    }
                );
            }
        );

        return hashColunasRelacionadas;
    }
}
