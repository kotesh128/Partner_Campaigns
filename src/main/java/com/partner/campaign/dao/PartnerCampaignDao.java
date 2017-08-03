
package com.partner.campaign.dao;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.partner.campaign.model.AdStatusEnum;
import com.partner.campaign.model.Campaign;

/**
 * Repository class that stores ad campaigns
 */
@Repository
public class PartnerCampaignDao {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerCampaignDao.class);

    private static List<Campaign> campaignList = new ArrayList<Campaign>();

    private static String SUCCESS = "Success";

    private static String FAILURE = "Failure";

    /**
     * Find campaigns by content, title and duration.
     * Return Active and Inactive campaigns.
     * @param adContent
     * @param adTitle
     * @param duration
     * @return List<Campaign>
     */
    public List<Campaign> findCampaigns(String adContent, String adTitle, Integer duration) {
        LOG.info("*******Finding campaigns for content {}, title {} and duration {}*********** ", adContent, adTitle, duration);

        List<Campaign> partnerCampaigns = null;

        if (!StringUtils.isEmpty(adContent)) {

            partnerCampaigns = campaignList.stream().filter(t -> t.getAd_content().contains(adContent))
                    .collect(Collectors.toList());

        }

        if (!StringUtils.isEmpty(adTitle) && (duration == null || duration.intValue() <= 0)) {

            partnerCampaigns = campaignList.stream().filter(t -> t.getAd_title().equalsIgnoreCase(adTitle))
                    .collect(Collectors.toList());

        } else if ((duration != null && duration.intValue() > 0) && StringUtils.isEmpty(adTitle)) {

            partnerCampaigns = campaignList.stream().filter(t -> t.getDuration() == duration.intValue())
                    .collect(Collectors.toList());

        }

        else if ((duration != null && duration.intValue() > 0) && !StringUtils.isEmpty(adTitle)) {

            partnerCampaigns = (List<Campaign>) campaignList.stream()
                    .filter(t -> t.getDuration() == duration.intValue())
                    .filter(t -> t.getAd_title().equalsIgnoreCase(adTitle)).collect(Collectors.toList());

        }

        if (partnerCampaigns == null) {
            partnerCampaigns = new ArrayList<Campaign>();
        }

        return partnerCampaigns;

    }

    /**
     * Find ad campaigns by partner, return only active campaigns.
     * @param partnerId
     * @return List<Campaign>
     */
    public List<Campaign> findByPartner(String partnerId) {
        LOG.info("*******Finding campaigns of partner*********** " + partnerId);

        List<Campaign> partnerCampaigns = new ArrayList<Campaign>();
        Clock clock = Clock.systemDefaultZone();

        partnerCampaigns = campaignList.stream().filter(t -> t.getPartner_id().equalsIgnoreCase(partnerId))
                .filter(t -> t.getEndTimeMillis() > clock.millis())
                .filter(t -> t.getAd_status().equals(AdStatusEnum.ACTIVE)).collect(Collectors.toList());

        return partnerCampaigns;

    }

    /**
     * Create ad campaigns by partner, only active campaign allowed per partner.
     * @param campaign
     * @return String
     */
    public String createCampaign(Campaign campaign) {
        LOG.info("*******Creating campaign of partner*********** " + campaign.getPartner_id());

        Clock clock = Clock.systemDefaultZone();

        List<Campaign> partnerCampaigns = campaignList.stream()
                .filter(t -> t.getPartner_id().equalsIgnoreCase(campaign.getPartner_id()))
                .filter(t -> t.getEndTimeMillis() > clock.millis())
                .filter(t -> t.getAd_status().equals(AdStatusEnum.ACTIVE)).collect(Collectors.toList());

        if (partnerCampaigns.isEmpty()) {
            campaign.setStartTimeMillis(clock.millis());
            campaign.setEndTimeMillis(clock.millis() + (campaign.getDuration() * 1000));
            campaignList.add(campaign);

            return SUCCESS;
        }

        return FAILURE;
    }

    /**
     * Update Campaign to update campaign proeprties
     * @param campaign
     * @return Campaign
     */
    public Campaign updateCampaign(Campaign campaign) {
        LOG.info("*******Updating campaign of partner*********** " + campaign.getPartner_id());

        Clock clock = Clock.systemDefaultZone();
        Optional<Campaign> campaignOption = campaignList.stream()
                .filter(t -> t.getPartner_id().equalsIgnoreCase(campaign.getPartner_id()))
                .filter(t -> t.getEndTimeMillis() > clock.millis())
                .filter(t -> t.getAd_status().equals(AdStatusEnum.ACTIVE)).findFirst();

        if (campaignOption.isPresent()) {
            Campaign partnerCampaign = campaignOption.get();
            partnerCampaign.setAd_content(campaign.getAd_content());
            partnerCampaign.setAd_title(campaign.getAd_title());
            partnerCampaign.setDuration(campaign.getDuration());
            partnerCampaign.setAd_status(campaign.getAd_status());

            partnerCampaign.setEndTimeMillis(campaign.getStartTimeMillis() + (campaign.getDuration() * 1000));

            return partnerCampaign;

        }

        return null;
    }

    /**
     * Scheduler to change status of campaigns after duration expires
     */
    @Scheduled(fixedRate = 10000)
    public void updateCampaignStatus() {
        Clock clock = Clock.systemDefaultZone();
        LOG.info("*******Running scheduler*********** " + clock.millis());

        campaignList.stream().filter(t -> t.getEndTimeMillis() < clock.millis())
                .forEach(t -> t.setAd_status(AdStatusEnum.INACTIVE));

    }
    
    public void setCampaignList(List<Campaign> campaigns) {
        campaignList = campaigns;
    }

}
