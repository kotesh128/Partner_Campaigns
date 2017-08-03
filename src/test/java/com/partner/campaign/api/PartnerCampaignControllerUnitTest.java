package com.partner.campaign.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import com.partner.campaign.model.Campaign;
import com.partner.campaign.model.CampaignResponse;
import com.partner.campaign.service.PartnerCampaignService;

public class PartnerCampaignControllerUnitTest {

    private PartnerCampaignController partnerCampaignController;

    private static final PartnerCampaignService partnerCampaignService = mock(PartnerCampaignService.class);
    
    @BeforeEach
    public void initialize() {
        partnerCampaignController = new PartnerCampaignController();
        partnerCampaignController.setPartnerCampaignService(partnerCampaignService);
    }
    
    
    @Test
    public void testFindCampaignsNoCampaigns() {
        when(partnerCampaignService.findCampaigns(eq("test Content"), eq("promo title"), eq(200))).thenReturn(new ArrayList<Campaign>());
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findCampaigns("test Content", "promo title", 200);
        assertTrue(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindCampaignsNullInput() {
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findCampaigns(null, null, null);
        assertTrue(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindCampaignsSuccess() {
        String content = "Test content for the campaing";
        Campaign campaign = new Campaign();
        campaign.setAd_content(content);
        List<Campaign> campaigns = new ArrayList<Campaign>();
        campaigns.add(campaign);
        when(partnerCampaignService.findCampaigns(eq("test Content"), eq("promo title"), eq(200))).thenReturn(campaigns);
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findCampaigns("test Content", "promo title", 200);
        assertFalse(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertTrue(responseEntity.getBody().size() == 1);
        assertTrue(responseEntity.getBody().get(0).getAd_content().equals(content));
    }
    
    @Test
    public void testFindByPartnerFailure() {
        when(partnerCampaignService.findByPartner(eq("12345"))).thenReturn(new ArrayList<Campaign>());
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findbyPartner("12345");
        assertTrue(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void testFindByPartnerSuccess() {
        
        String content = "Test content for the campaing";
        Campaign campaign = new Campaign();
        campaign.setAd_content(content);
        List<Campaign> campaigns = new ArrayList<Campaign>();
        campaigns.add(campaign);
        when(partnerCampaignService.findByPartner(eq("12345"))).thenReturn(campaigns);
        ResponseEntity<List<Campaign>> responseEntity = partnerCampaignController.findbyPartner("12345");
        assertFalse(CollectionUtils.isEmpty(responseEntity.getBody()));
        assertTrue(responseEntity.getBody().size() == 1);
        assertTrue(responseEntity.getBody().get(0).getAd_content().equals(content));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    public void testCreateCampaignFailure() {
        when(partnerCampaignService.createCampaign(any(Campaign.class))).thenReturn("FAILURE");
        Campaign campaign = new Campaign();
        ResponseEntity<CampaignResponse> responseEntity = partnerCampaignController.createCampaign(campaign);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        
    }
    
    @Test
    public void testCreateCampaignSuccess() {
        when(partnerCampaignService.createCampaign(any(Campaign.class))).thenReturn("SUCCESS");
        Campaign campaign = new Campaign();
        ResponseEntity<CampaignResponse> responseEntity = partnerCampaignController.createCampaign(campaign);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
    }
    
    @Test
    public void testUpdateCampaignFailure() {
        when(partnerCampaignService.updateCampaign(any(Campaign.class))).thenReturn(null);
        Campaign campaign = new Campaign();
        ResponseEntity<Campaign> responseEntity = partnerCampaignController.updateCampaign(campaign);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        
    }
    
    @Test
    public void testUpdateCampaignSuccess() {
        Campaign campaign = new Campaign();
        when(partnerCampaignService.updateCampaign(any(Campaign.class))).thenReturn(campaign);
        ResponseEntity<Campaign> responseEntity = partnerCampaignController.updateCampaign(campaign);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        
    }

}