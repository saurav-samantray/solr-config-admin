package com.solr.admin.controller;

import com.solr.admin.payload.UploadConfigRequest;
import com.solr.admin.payload.UploadConfigResponse;
import com.solr.admin.service.SolrConfigService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/solr/configSet")
public class SolrConfigController {
    @Autowired
    SolrConfigService configService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity upload(String configSetPath, String configSetName) throws IOException {
        return new ResponseEntity(configService.uploadConfigSet(configSetPath,configSetName),HttpStatus.OK);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(String configSetName) throws IOException {
        return new ResponseEntity(configService.deleteConfigSet(configSetName),HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity fetch() throws IOException {
        return new ResponseEntity(configService.fetchConfigSet(), HttpStatus.OK);
    }

}
