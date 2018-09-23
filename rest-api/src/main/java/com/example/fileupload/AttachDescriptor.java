package com.example.fileupload;

import java.io.Serializable;

public class AttachDescriptor implements Serializable {
    private String attachName;
    private String attachContent;

    public AttachDescriptor() {
    }

    public AttachDescriptor(String attachName, String attachContent) {
        this.attachName = attachName;
        this.attachContent = attachContent;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getAttachContent() {
        return attachContent;
    }

    public void setAttachContent(String attachContent) {
        this.attachContent = attachContent;
    }
}
