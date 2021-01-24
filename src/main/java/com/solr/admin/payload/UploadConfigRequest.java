package com.solr.admin.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadConfigRequest {
    String filePath;
    String configSetName;
}
