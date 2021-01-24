package com.solr.admin.service;


import com.solr.admin.payload.UploadConfigResponse;
import com.solr.admin.payload.SolrConnectionDetails;
import com.solr.admin.utils.SolrUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolrConfigService {
    @Autowired
    SolrUtil solrUtil;

    public UploadConfigResponse uploadConfigSet(String filePath, String configSetName) throws IOException {
        return new UploadConfigResponse(solrUtil.uploadConfigSet(filePath, configSetName),solrUtil.fetchConfigSets());
    }

    public List<String> deleteConfigSet(String configSetName) throws IOException {
        solrUtil.deleteConfigSet(configSetName);
        return solrUtil.fetchConfigSets();
    }

    public List<String> fetchConfigSet() throws IOException {
        if(null != SolrUtil.getZkConnection()) {
            return solrUtil.fetchConfigSets();
        }else{
            return new ArrayList<>();
        }
    }
}
