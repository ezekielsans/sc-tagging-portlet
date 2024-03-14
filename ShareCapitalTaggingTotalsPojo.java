/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author mis
 */
@ManagedBean
@SessionScoped
public class ShareCapitalTaggingTotalsPojo implements Serializable {

    /**
     * Creates a new instance of ShareCapitalTaggingTotalsPojo
     */
    public ShareCapitalTaggingTotalsPojo() {
    }

    private String entryType;
    private BigDecimal totalAmount;

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
