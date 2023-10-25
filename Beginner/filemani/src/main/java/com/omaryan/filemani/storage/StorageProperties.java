package com.omaryan.filemani.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
    /**
     * Folder location for storing files
     */
    private String location = "upload-dir"; //Name of the folder which will store the uploaded files

    public String getLocation(){
        return location;
    }
    public void setLocation(){
        this.location = location;
    }
}
