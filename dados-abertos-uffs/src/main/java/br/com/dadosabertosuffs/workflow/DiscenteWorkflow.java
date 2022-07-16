package br.com.dadosabertosuffs.workflow;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dadosabertosuffs.cache.Cache;
import br.com.dadosabertosuffs.entity.dto.ResourceEstrutura;
import br.com.dadosabertosuffs.entity.httpresponse.ResourceResponse;
import br.com.dadosabertosuffs.workflow.activity.CriarObterDatasetConteudoResponse;
import br.com.dadosabertosuffs.workflow.activity.JsonArrayToListaDeRegistrosActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterCamposRecursoActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterColunasRelacionadasActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterConteudoRecursoPrincipalActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterConteudoRecursoRelacionadoActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterIdRecursoPorDatasetActivity;
import br.com.dadosabertosuffs.workflow.activity.ObterNomesDatasetsActivity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscenteWorkflow {
    
    @Autowired
    private final ObterNomesDatasetsActivity obterNomesDatasets;

    @Autowired
    private final ObterIdRecursoPorDatasetActivity obterIdRecursosPorDataset;
    
    @Autowired
    private final ObterCamposRecursoActivity obterCamposRecurso;

    @Autowired
    private final ObterColunasRelacionadasActivity obterColunasRelacionadas;
    
    @Autowired
    private final JsonArrayToListaDeRegistrosActivity jsonArrayToListaDeRegistrosActivity;

    @Autowired
    private final ObterConteudoRecursoPrincipalActivity obterConteudoRecursoPrincipalActivity;
    
    @Autowired
    private final ObterConteudoRecursoRelacionadoActivity obterConteudoRecursoRelacionadoActivity;

    @Autowired
    private final CriarObterDatasetConteudoResponse criarObterDatasetConteudoResponse;
    
    @Autowired
    private final Cache cache;
    
    public List<String> obterNomesDatasets() throws IOException, InterruptedException {
        return obterNomesDatasets.execute();
    }

    public ResourceResponse obterDatasetConteudo(String datasetNome, String filtros, List<String> relacionamentos) throws IOException, InterruptedException {
        var hashRecursoEstrutura = obterRecursoEstrutura(relacionamentos);

        ResourceResponse datasetPrincipalConteudo = obterConteudoRecursoPrincipalActivity.execute(datasetNome, hashRecursoEstrutura, filtros);
        var conteudoDatasetPrincipalSemRelacionamento = jsonArrayToListaDeRegistrosActivity.execute(datasetPrincipalConteudo);

        var conteudoDatasetPrincipalComRelacionamento = obterConteudoRecursoRelacionadoActivity.execute(relacionamentos, datasetNome, conteudoDatasetPrincipalSemRelacionamento, hashRecursoEstrutura);

        return criarObterDatasetConteudoResponse.execute(datasetPrincipalConteudo, conteudoDatasetPrincipalComRelacionamento);
    }

    /**
     * Obtém hash de recursoEstrutura. A chave do Hash é o nome do dataset, o campo é a estrutura
     * que consiste no id, nome, campos e datasets relacionados.
     * @throws InterruptedException
     * @throws IOException
     */
    private HashMap<String, ResourceEstrutura> obterRecursoEstrutura(List<String> relacionamentos) throws IOException, InterruptedException {
        if(cache.atualizacaoNecessaria()) {
            HashMap<String, ResourceEstrutura> hashRecursoEstruturaPorDataset = obterIdRecursosPorDataset.execute(obterNomesDatasets.execute());
            obterCamposRecurso.execute(hashRecursoEstruturaPorDataset);
            obterColunasRelacionadas.execute(hashRecursoEstruturaPorDataset);
            cache.atualizarCache(hashRecursoEstruturaPorDataset);

            return hashRecursoEstruturaPorDataset;
        } else {
            return cache.getHashRecursoEstruturaPorDataset();
        }
    }
}
