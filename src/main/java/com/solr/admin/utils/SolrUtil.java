package com.solr.admin.utils;

import com.solr.admin.payload.CollectionDetails;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.SolrClientBuilder;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.common.cloud.SolrZkClient;
import org.apache.solr.common.cloud.ZkConfigManager;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SolrUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolrUtil.class);

    static SolrZkClient zkSolrClient = null;

    static CloudSolrClient cloudSolrClient = null;

    static SolrClient solrClient = null;


    public static void createConnection(String zkHostString){
        if(cloudSolrClient == null || !zkHostString.equalsIgnoreCase(cloudSolrClient.getZkHost())){
            cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHostString).build();
            //cloudSolrClient = new CloudSolrClient.Builder().withHttpClient(new HttpSolrClient.Builder().withBaseSolrUrl("http://localhost:8983/solr/").build().getHttpClient());
            //cloudSolrClient = new CloudSolrClient.Builder().withSolrUrl("http://localhost:8983/solr/").build();
        }


        if(zkSolrClient == null || !zkHostString.equalsIgnoreCase(zkSolrClient.getZkServerAddress())){
            zkSolrClient = new SolrZkClient(zkHostString, 3000);
        }
    }

    public static void closeConnection() throws IOException {
        if(cloudSolrClient != null ){
            cloudSolrClient.close();
            cloudSolrClient = null;
        }
        if(zkSolrClient != null ){
            zkSolrClient.close();
            zkSolrClient = null;
        }
    }

    public static void createSolrConnection(String zkHostString){
        solrClient = new HttpSolrClient.Builder("http://localhost:8983/solr/collection1").build();
        //solrClient.get
    }

    public static SolrZkClient getZkConnection(){
       return zkSolrClient;
    }
    public static CloudSolrClient getSolrConnection(){
        return cloudSolrClient;
    }

    public boolean uploadConfigSet(String filePath,String configSetName) throws IOException{
            ZkConfigManager manager = new ZkConfigManager(SolrUtil.zkSolrClient);
            manager.uploadConfigDir(Paths.get(filePath), configSetName);
        return true;
    }

    public boolean deleteConfigSet(String configSetName) throws IOException{
        ZkConfigManager manager = new ZkConfigManager(SolrUtil.zkSolrClient);
        manager.deleteConfigDir(configSetName);
        return true;
    }

    public List<String> fetchConfigSets() throws IOException{
        ZkConfigManager manager = new ZkConfigManager(SolrUtil.zkSolrClient);
        return manager.listConfigs();
    }

    public List<String> fetchCollections() throws IOException, SolrServerException {
        return CollectionAdminRequest.listCollections(cloudSolrClient);
    }

    public CollectionDetails fetchCollectionDetails(String name) throws IOException, SolrServerException {
        CollectionDetails collectionDetails = new CollectionDetails();
        final CollectionAdminRequest.ClusterStatus req = CollectionAdminRequest.getClusterStatus();
        req.setCollectionName(name);
        NamedList<Object> rsp = cloudSolrClient.request(req);
        NamedList<Object> cluster = (NamedList<Object>) rsp.get("cluster");
        NamedList<Object> collections = (NamedList<Object>) cluster.get("collections");
        LinkedHashMap colDetails = (LinkedHashMap) collections.get(name);
        LinkedHashMap aliases = (LinkedHashMap) cluster.get("aliases");
        collectionDetails.setName(name);
        collectionDetails.setConfigName((String)colDetails.get("configName"));
        collectionDetails.setAliases(new ArrayList());
        if(aliases != null) {
            aliases.forEach((key, value) -> {
                if (value != null && ((String) value).equals(name)) {
                    collectionDetails.getAliases().add(key);
                }
            });
        }
        return collectionDetails;
    }


}