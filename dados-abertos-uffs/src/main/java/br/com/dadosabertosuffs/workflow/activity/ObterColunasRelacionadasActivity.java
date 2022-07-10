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
                                            if(!campo2.getId().equals("_id") && campo1.getId().equals(campo2.getId())) {  
                                                if(obterCamposForeignKey().contains(campo2.getId())) {
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

    public List<String> obterCamposForeignKey() {
        return List.of(
                "id_turma",
                "cod_ccr",
                "cod_uffs",
                "cod_uffs_curso_turma",
                "lista_docente_ch",
                "nome_curso",
                "cpf",
                "id_curso",
                "coordenador"
        );
    }

}
