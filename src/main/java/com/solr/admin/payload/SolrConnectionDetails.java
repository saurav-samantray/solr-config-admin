package com.solr.admin.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolrConnectionDetails {
    String zkHostString;
    boolean zkConnected=false;
    boolean solrConnected=false;
    List configSets;
    List collections;
    private List<String> errors;
}
