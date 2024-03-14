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
public class ShareCapitalTaggingDetailsPojo implements Serializable {

    /**
     * Creates a new instance of ShareCapitalTaggingDetailsPojo
     */
    public ShareCapitalTaggingDetailsPojo() {
    }

    private String acctno;
    private String name;
    private String entryType;
    private BigDecimal amount;
    private BigDecimal balance;
    
    
    
    
    
    
    
    
    
    
    
    

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
