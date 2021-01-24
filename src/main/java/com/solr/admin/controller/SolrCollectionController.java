package com.solr.admin.controller;

import com.solr.admin.service.SolrCollectionService;
import com.solr.admin.service.SolrConfigService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/solr/collections")
public class SolrCollectionController {

    Logger LOGGER = LoggerFactory.getLogger(SolrCollectionController.class);

    @Autowired
    SolrCollectionService collectionService;

    @GetMapping(value="",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity fetchCollections() throws IOException, SolrServerException {
        return new ResponseEntity(collectionService.fetchCollections(), HttpStatus.OK);
    }

    @GetMapping(value="/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity fetchDetails(@PathVariable("name") String name) throws IOException, SolrServerException {
        return new ResponseEntity(collectionService.fetchDetails(name), HttpStatus.OK);
    }
}
