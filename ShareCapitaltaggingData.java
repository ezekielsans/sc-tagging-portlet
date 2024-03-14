/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.OrganizationLocation;
import model.ScTaggingViewV2;
import model.ShareCapitalTaggingView;

/**
 *
 * @author mis
 */
@ManagedBean
@SessionScoped
public class ShareCapitalTaggingData implements serializable {

    /**
     * Creates a new instance of ShareCapitalTaggingData
     */
    public ShareCapitalTaggingData() {
    }

    @ManagedProperty(value = "#{customEntityManagerFactory}")
    private CustomEntityManagerFactory customEntityManagerFactory;
    @ManagedProperty(value = "#{scTaggingViewV2}")
    private List<ScTaggingViewV2> scTaggingViewV2;
    @ManagedProperty(value = "#{totalsPojo}")
    private List<ShareCapitalTaggingTotalsPojo> totalsPojo;
    @ManagedProperty(value = "#{detailsPojo}")
    private List<ShareCapitalTaggingDetailsPojo> detailsPojo;

    private Date fromDate, toDate;
    private String dateSpan;
    private String location;
    private Long count;
    private Integer organizationLocationId;
    private List<Object[]> taggingTotals;
    private List<Object[]> taggingDetails;
    private List<OrganizationLocation> organizationLocations;

    /*
     *  Getter & Setter
     */
    public List<OrganizationLocation> getOrganizationLocations() {
        return organizationLocations == null ? organizationLocations = new ArrayList<>() : organizationLocations;
    }

    public void setOrganizationLocations(List<OrganizationLocation> organizationLocations) {
        this.organizationLocations = organizationLocations;
    }

    public Integer getOrganizationLocationId() {
        return organizationLocationId;
    }

    public void setOrganizationLocationId(Integer organizationLocationId) {
        this.organizationLocationId = organizationLocationId;
    }

    public CustomEntityManagerFactory getCustomEntityManagerFactory() {
        return customEntityManagerFactory == null ? customEntityManagerFactory = new CustomEntityManagerFactory() : customEntityManagerFactory;
    }

    public void setCustomEntityManagerFactory(CustomEntityManagerFactory customEntityManagerFactory) {
        this.customEntityManagerFactory = customEntityManagerFactory;
    }

    public List<ShareCapitalTaggingTotalsPojo> getTotalsPojo() {
        return totalsPojo == null ? totalsPojo = new ArrayList<>() : totalsPojo;
    }

    public void setTotalsPojo(List<ShareCapitalTaggingTotalsPojo> totalsPojo) {
        this.totalsPojo = totalsPojo;
    }

    public List<ScTaggingViewV2> getScTaggingViewV2() {
        return scTaggingViewV2 == null ? scTaggingViewV2 = new ArrayList<>() : scTaggingViewV2;
    }

    public void setScTaggingViewV2(List<ScTaggingViewV2> scTaggingViewV2) {
        this.scTaggingViewV2 = scTaggingViewV2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getDateSpan() {
        return dateSpan;
    }

    public void setDateSpan(String dateSpan) {
        this.dateSpan = dateSpan;
    }

    public List<Object[]> getTaggingTotals() {
        return taggingTotals == null ? taggingTotals = new ArrayList<>() : taggingTotals;
    }

    public void setTaggingTotals(List<Object[]> taggingTotals) {
        this.taggingTotals = taggingTotals;
    }

    public List<Object[]> getTaggingDetails() {
        return taggingDetails == null ? taggingDetails = new ArrayList<>() : taggingDetails;
    }

    public void setTaggingDetails(List<Object[]> taggingDetails) {
        this.taggingDetails = taggingDetails;
    }

    public List<ShareCapitalTaggingDetailsPojo> getDetailsPojo() {
        return detailsPojo == null ? detailsPojo = new ArrayList<>() : detailsPojo;
    }

    public void setDetailsPojo(List<ShareCapitalTaggingDetailsPojo> detailsPojo) {
        this.detailsPojo = detailsPojo;
    }

    /*
     *  Methods
     */
    public void init() {
        if (FacesContext.getCurrentInstance().isPostback() == false) {
            setOrganizationLocations(getCustomEntityManagerFactory().getLportalMemOrgEntityManagerFactory().createEntityManager().createQuery(""
                    + "SELECT o FROM OrganizationLocation o ").getResultList());
            beanclear();

        }

    }

    public void beanclear() {

        System.out.println("Hello");

    }
}
