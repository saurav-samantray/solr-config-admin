package com.solr.admin.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UploadConfigResponse {
    private boolean success;
    private List configSets;
}
