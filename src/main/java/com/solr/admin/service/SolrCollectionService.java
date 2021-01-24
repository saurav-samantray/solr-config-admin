package com.solr.admin.service;

import com.solr.admin.payload.CollectionDetails;
import com.solr.admin.utils.SolrUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SolrCollectionService {
    @Autowired
    SolrUtil solrUtil;

    public List<String> fetchCollections() throws IOException, SolrServerException {
        return solrUtil.fetchCollections();
    }

    public CollectionDetails fetchDetails(String name) throws IOException, SolrServerException {
        return solrUtil.fetchCollectionDetails(name);
    }

}
