package br.com.dadosabertosuffs.workflow.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponseResult;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JsonArrayToListaDeRegistrosActivity {
    
    public List<HashMap<String, String>> execute(ResourceResponse datasetPrincipalConteudo) throws JsonProcessingException {
        List<HashMap<String, String>> datasetPrincipalConteudoHash = jsonArrayToListaDeRegistros(datasetPrincipalConteudo.getResult());

        return datasetPrincipalConteudoHash;
    }

    /**
     * Transforma valor dos campos de JsonArray para lista de hash, cada elemento da lista é um registro
     * e cada registro contém um hash com campo e valor do atributo
    */
    private List<HashMap<String, String>> jsonArrayToListaDeRegistros(ResourceResponseResult conteudoRecursoDatasetPrincipal) {
        List<HashMap<String, String>> listRegistros = new ArrayList<>();
        for(int i=0; i< conteudoRecursoDatasetPrincipal.getRecords().size(); i++) {
            var registroJsonArray = conteudoRecursoDatasetPrincipal.getRecords().get(i).getAsJsonArray();
            HashMap<String, String> hashRecurso = new HashMap<>();
            listRegistros.add(hashRecurso);
            for(int i2=0; i2< registroJsonArray.size(); i2++) {
                hashRecurso.put(conteudoRecursoDatasetPrincipal.getFields().get(i2).getId(), registroJsonArray.get(i2).getAsString());
            }
        }
        return listRegistros;
    }

}
