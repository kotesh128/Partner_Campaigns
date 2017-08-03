package com.partner.campaign.model;

import java.io.Serializable;

public class CampaignResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;

    private String description;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
