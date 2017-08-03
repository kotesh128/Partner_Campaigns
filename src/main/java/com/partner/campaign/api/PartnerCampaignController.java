package com.partner.campaign.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.partner.campaign.model.Campaign;
import com.partner.campaign.model.CampaignResponse;
import com.partner.campaign.service.PartnerCampaignService;

/**
 * This is controller class that gets requests to create, update, find campaigns
 * 
 */
@RestController
@RequestMapping(value = "/campaign")
public class PartnerCampaignController {

    @Autowired
    private PartnerCampaignService partnerCampaignService;

    private static final Logger LOG = LoggerFactory.getLogger(Campaign.class.getName());

    private static final String SUCCESS = "Success";

    @Value("${create.failure}")
    private String createFailureMsg;

    /**
     * Find ad campaigns by content, title and duration of campaigns.
     * Return Active and Inactive campaigns.
     * @param adContent
     * @param adTitle
     * @param duration
     * @return ResponseEntity<List<Campaign>>
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Campaign>> findCampaigns(
            @RequestParam(value = "content", required = false) String adContent,
            @RequestParam(value = "title", required = false) String adTitle,
            @RequestParam(value = "duration", required = false) Integer duration) {
        LOG.info("AnalyticsLookupDataMicroServiceController Request Details  Source*****");

        if (StringUtils.isEmpty(adContent) && StringUtils.isEmpty(adTitle) && (duration== null || duration.intValue() <= 0)) {
            return new ResponseEntity<List<Campaign>>(new ArrayList<Campaign>(), HttpStatus.BAD_REQUEST);
        }

        List<Campaign> campaingList = partnerCampaignService.findCampaigns(adContent, adTitle, duration);

        if (campaingList.isEmpty()) {
            return new ResponseEntity<List<Campaign>>(campaingList, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Campaign>>(campaingList, HttpStatus.OK);
    }

    /**
     * Find ad campaigns by partner Id.
     * Return only Active campaigns.
     * @param partnerId
     * @return ResponseEntity<List<Campaign>>
     */
    @RequestMapping(value = "/partnersearch", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Campaign>> findbyPartner(@RequestParam("partnerId") String partnerId) {
        LOG.info("AnalyticsLookupDataMicroServiceController Request Details  Source*****");

        List<Campaign> campaignList = partnerCampaignService.findByPartner(partnerId);

        if (campaignList.isEmpty()) {
            return new ResponseEntity<List<Campaign>>(campaignList, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<List<Campaign>>(campaignList, HttpStatus.OK);
    }

    /**
     * Create ad campaigns, only one active campaign is allowed for partner
     * @param campaign
     * @return ResponseEntity<ResponseEntity<Campaign>>
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<CampaignResponse> createCampaign(@RequestBody Campaign campaign) {
        this.validateCampaign(campaign);

        CampaignResponse response = new CampaignResponse();

        response.setStatus(partnerCampaignService.createCampaign(campaign));

        if (response.getStatus().equalsIgnoreCase(SUCCESS)) {

            response.setDescription("Created");
            return new ResponseEntity<CampaignResponse>(response, HttpStatus.OK);
        } else {
            response.setDescription(createFailureMsg);
            return new ResponseEntity<CampaignResponse>(response, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Update ad campaign
     * @param campaign
     * @return ResponseEntity<Campaign>
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Campaign> updateCampaign(@RequestBody Campaign campaign) {
        this.validateCampaign(campaign);

        Campaign updatedCampaign = partnerCampaignService.updateCampaign(campaign);

        if (updatedCampaign == null) {
            return new ResponseEntity<Campaign>(updatedCampaign, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Campaign>(updatedCampaign, HttpStatus.OK);

    }

    /**
     * Utility method to validate Campaign object for required properties
     * @param campaign
     */
    private void validateCampaign(Campaign campaign) {
        // TODO Validate Campaign object

    }

    public PartnerCampaignService getPartnerCampaignService() {
        return partnerCampaignService;
    }

    public void setPartnerCampaignService(PartnerCampaignService partnerCampaignService) {
        this.partnerCampaignService = partnerCampaignService;
    }

}
