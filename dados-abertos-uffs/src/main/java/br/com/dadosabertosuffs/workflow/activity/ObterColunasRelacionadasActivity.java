package br.com.dadosabertosuffs.workflow.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.entity.dto.ColunasRelacionadas;
import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResultField;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObterColunasRelacionadasActivity {
    
    /**
     * Varre todas as colunas de todos os recursos recebido por parametro e encontra
     * os campos com mesmo nome.
     * @param hashRecursosPorDataset
     */
    public HashMap<String, ResourceEstrutura> execute(HashMap<String, ResourceEstrutura> hashRecursosPorDataset) {        
        hashRecursosPorDataset.forEach((nomeDataset, resource1) -> {//varre dataset1
                hashRecursosPorDataset.forEach((nomeDataset2, resource2) -> {//varre dataset2
                        if(!nomeDataset.equals(nomeDataset2)) {//desconsidera datasets iguais
                            resource1.getCampos().forEach((campo1) -> {//varre campos do dataset1
                                    resource2.getCampos().forEach((campo2) -> {//varre campos do dataset2
                                            if(recursosSaoRelacionados(campo1, campo2)) {   
                                                var novaColunaRelacionada = new ColunasRelacionadas(nomeDataset2, campo2.getId());
                                                addRelacionamentoNoHash(nomeDataset, hashRecursosPorDataset, novaColunaRelacionada);
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

        return hashRecursosPorDataset;
    }

    private boolean recursosSaoRelacionados(ResourceResponseResultField campo1, ResourceResponseResultField campo2) {
        return (campo1.getId().equals(campo2.getId()) && !campo1.getId().equals("_id"));
    }

    private void addRelacionamentoNoHash(String nomeDataset, HashMap<String, ResourceEstrutura> hashRecursosPorDataset, ColunasRelacionadas novaColunaRelacionada) {
        if(hashRecursosPorDataset.get(nomeDataset).getColunasRelacionadas() == null) {
            List<ColunasRelacionadas> novaLista = new ArrayList<ColunasRelacionadas>();
            novaLista.add(novaColunaRelacionada);
            hashRecursosPorDataset.get(nomeDataset).setColunasRelacionadas(novaLista);
        } else {
            hashRecursosPorDataset.get(nomeDataset).getColunasRelacionadas().add(novaColunaRelacionada);
        }
    }
}
