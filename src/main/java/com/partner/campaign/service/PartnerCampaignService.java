package com.partner.campaign.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partner.campaign.dao.PartnerCampaignDao;
import com.partner.campaign.model.Campaign;

/**
 * Service class that accesses campaign repository
 */
@Service
public class PartnerCampaignService {

    @Autowired
    PartnerCampaignDao partnerCampaignDao;

    public String createCampaign(Campaign campaign) {
        return partnerCampaignDao.createCampaign(campaign);
    }

    public Campaign updateCampaign(Campaign campaign) {
        return partnerCampaignDao.updateCampaign(campaign);
    }

    public List<Campaign> findCampaigns(String adContent, String adTitle, Integer duration) {
        return partnerCampaignDao.findCampaigns(adContent, adTitle, duration);
    }
    
    public List<Campaign> findByPartner(String partnerId) {
        return partnerCampaignDao.findByPartner(partnerId);
    }

    public PartnerCampaignDao getPartnerCampaignDao() {
        return partnerCampaignDao;
    }

    public void setPartnerCampaignDao(PartnerCampaignDao partnerCampaignDao) {
        this.partnerCampaignDao = partnerCampaignDao;
    }

}
