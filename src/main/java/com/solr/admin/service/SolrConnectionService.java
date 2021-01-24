package com.solr.admin.service;

import com.solr.admin.payload.SolrConnectionDetails;
import com.solr.admin.utils.SolrUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SolrConnectionService {

    @Autowired
    SolrUtil solrUtil;

    public SolrConnectionDetails initializeSolrZkConnection(String zkString) throws IOException, SolrServerException {
        SolrUtil.createConnection(zkString);
        return fetchConnectionDetails();
    }

    public SolrConnectionDetails closeSolrZkConnection() throws IOException, SolrServerException {
        SolrUtil.closeConnection();
        return new SolrConnectionDetails();
    }

    public SolrConnectionDetails fetchConnectionDetails() throws IOException, SolrServerException {
        SolrConnectionDetails connectionDetails = new SolrConnectionDetails();
        if(null != SolrUtil.getZkConnection()){
            connectionDetails.setZkHostString(SolrUtil.getZkConnection().getZkServerAddress());
            connectionDetails.setZkConnected(true);
            connectionDetails.setConfigSets(solrUtil.fetchConfigSets());
        }
        if(null != SolrUtil.getSolrConnection()){
            connectionDetails.setSolrConnected(true);
            connectionDetails.setCollections(solrUtil.fetchCollections());
        }
        return connectionDetails;
    }

}
