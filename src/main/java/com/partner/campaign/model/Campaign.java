package com.partner.campaign.model;

import java.io.Serializable;

public class Campaign implements Serializable {

	private static final long serialVersionUID = 1L;

	private String partner_id;
	private int duration;
	private String ad_content;
	private String ad_title;
	private AdStatusEnum ad_status;
	private long startTimeMillis;
	private long endTimeMillis;
	
    public String getPartner_id() {
        return partner_id;
    }
    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getAd_content() {
        return ad_content;
    }
    public void setAd_content(String ad_content) {
        this.ad_content = ad_content;
    }
    public String getAd_title() {
        return ad_title;
    }
    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }
    public AdStatusEnum getAd_status() {
        return ad_status;
    }
    public void setAd_status(AdStatusEnum ad_status) {
        this.ad_status = ad_status;
    }
    public long getStartTimeMillis() {
        return startTimeMillis;
    }
    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }
    public long getEndTimeMillis() {
        return endTimeMillis;
    }
    public void setEndTimeMillis(long endTimeMillis) {
        this.endTimeMillis = endTimeMillis;
    }
    

	
}
