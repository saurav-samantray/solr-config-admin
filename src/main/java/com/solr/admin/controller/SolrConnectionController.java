package com.solr.admin.controller;

import com.solr.admin.service.SolrConnectionService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/solr")
public class SolrConnectionController {

    @Autowired
    SolrConnectionService connectionService;

    @GetMapping(value="/connection",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity fetchConnection() throws IOException, SolrServerException {
        return new ResponseEntity(connectionService.fetchConnectionDetails(), HttpStatus.OK);
    }

    @DeleteMapping(value="/connection",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity closeConnection() throws IOException, SolrServerException {
        return new ResponseEntity(connectionService.closeSolrZkConnection(), HttpStatus.OK);
    }

    @PostMapping(value="/initialize",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity upload(String zkHostString) throws IOException, SolrServerException {
        return new ResponseEntity(connectionService.initializeSolrZkConnection(zkHostString),HttpStatus.OK);
    }
}
