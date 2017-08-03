package com.partner.campaign.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import com.partner.campaign.dao.PartnerCampaignDao;
import com.partner.campaign.model.AdStatusEnum;
import com.partner.campaign.model.Campaign;
import com.partner.campaign.model.CampaignResponse;
import com.partner.campaign.service.PartnerCampaignService;

public class PartnerCampaignControllerIntTest {

    private PartnerCampaignController partnerCampaignController;

    private PartnerCampaignService partnerCampaignService;
    
    private PartnerCampaignDao partnerCampaignDao;
    
    private static List<Campaign> campaignList = new ArrayList<Campaign>();
    

    @BeforeEach
    public void initialize() {
       
        partnerCampaignController = new PartnerCampaignController();
        partnerCampaignService = new PartnerCampaignService();
        partnerCampaignDao = new PartnerCampaignDao();
        partnerCampaignController.setPartnerCampaignService(partnerCampaignService);
        partnerCampaignService.setPartnerCampaignDao(partnerCampaignDao);
    }
    
    
    @Test
    public void testFindCampaignsNoActiveCampaigns() throws InterruptedException {
        campaignList.clear();
        campaignList.add(getCampaign());
        partnerCampaignDao.setCampaignList(campaignList);
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findCampaigns("test Content3", null, null);
        assertTrue(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindCampaignsNullInput() {
        campaignList.clear();
        campaignList.add(getCampaign());
        partnerCampaignDao.setCampaignList(campaignList);
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findCampaigns(null, null, null);
        assertTrue(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindCampaignsSuccess() {
        campaignList.clear();
        campaignList.add(getCampaign());
        partnerCampaignDao.setCampaignList(campaignList);
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findCampaigns("test campaign", null, null);
        assertFalse(CollectionUtils.isEmpty(responseEntity.getBody()));

        assertTrue(responseEntity.getBody().size() == 1);
        assertTrue(responseEntity.getBody().get(0).getAd_content().equals("test campaign content"));
    }
    
    @Test
    public void testFindByPartnerFailure() throws InterruptedException {
        campaignList.clear();
        campaignList.add(getCampaign());
        partnerCampaignDao.setCampaignList(campaignList);
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findbyPartner("2");
        assertTrue(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindByPartnerSuccess() {
        campaignList.clear();
        campaignList.add(getCampaign());
        partnerCampaignDao.setCampaignList(campaignList);
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findbyPartner("1");
        assertFalse(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertTrue(responseEntity.getBody().size() == 1);
        assertTrue(responseEntity.getBody().get(0).getAd_content().equals("test campaign content"));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void testCreateCampaignFailure() {
        campaignList.clear();
        campaignList.add(getCampaign());
        partnerCampaignDao.setCampaignList(campaignList);
        Campaign campaign = new Campaign();
        campaign.setPartner_id("1");
        campaign.setAd_content("test content");
        campaign.setAd_title("promo title");
        campaign.setAd_status(AdStatusEnum.ACTIVE);
        campaign.setDuration(5);
        ResponseEntity<CampaignResponse> responseEntity = partnerCampaignController.createCampaign(campaign);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        
    }
    
    private Campaign getCampaign() {
        Clock clock = Clock.systemDefaultZone();
        Campaign campaign = new Campaign();
        campaign.setPartner_id("1");
        campaign.setAd_content("test campaign content");
        campaign.setAd_title("promo title");
        campaign.setAd_status(AdStatusEnum.ACTIVE);
        campaign.setDuration(20);
        campaign.setStartTimeMillis(clock.millis());
        campaign.setEndTimeMillis(clock.millis() + (20000));
        
        return campaign;
    }
    
    

}