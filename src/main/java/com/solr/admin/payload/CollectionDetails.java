package com.solr.admin.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class CollectionDetails {
    private String name;
    private String configName;
    private List aliases;

}
