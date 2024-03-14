/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.portal.theme.ThemeDisplay;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author mis
 */
@ManagedBean
@RequestScoped
public class ShareCapitalTaggingController implements serializable {

    /**
     * Creates a new instance of ShareCapitalController
     */
    public ShareCapitalTaggingController() {
    }
    @ManagedProperty(value = "#{shareCapitalTaggingData}")
    private ShareCapitalTaggingData shareCapitalTaggingData;
    @ManagedProperty(value = "#{customEntityManagerFactory}")
    private CustomEntityManagerFactory customEntityManagerFactory;
    @ManagedProperty(value = "#{dbConnection}")
    private DbConnection dbConnection;
    @ManagedProperty(value = "#{portalData}")
    private PortalData portalData;
    private DataConvert dataConvert;
    @ManagedProperty(value = "#{customDate}")
    private CustomDate customDate;
    @ManagedProperty(value = "#{exportData}")
    private ExportData exportData;

    /*
     *  Getter & Setter
     */
    public ShareCapitalTaggingData getShareCapitalTaggingData() {
        return shareCapitalTaggingData;
    }

    public void setShareCapitalTaggingData(ShareCapitalTaggingData shareCapitalTaggingData) {
        this.shareCapitalTaggingData = shareCapitalTaggingData;
    }

    public CustomEntityManagerFactory getCustomEntityManagerFactory() {
        return customEntityManagerFactory;
    }

    public void setCustomEntityManagerFactory(CustomEntityManagerFactory customEntityManagerFactory) {
        this.customEntityManagerFactory = customEntityManagerFactory;
    }

    public DbConnection getDbConnection() {
        return dbConnection == null ? dbConnection = new DbConnection() : dbConnection;
    }

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public PortalData getPortalData() {
        return portalData == null ? portalData = new PortalData() : portalData;
    }

    public void setPortalData(PortalData portalData) {
        this.portalData = portalData;
    }

    public DataConvert getDataConvert() {
        return dataConvert == null ? dataConvert = new DataConvert() : dataConvert;
    }

    public void setDataConvert(DataConvert dataConvert) {
        this.dataConvert = dataConvert;
    }

    public CustomDate getCustomDate() {
        return customDate == null ? customDate = new CustomDate() : customDate;
    }

    public void setCustomDate(CustomDate customDate) {
        this.customDate = customDate;
    }

    public ExportData getExportData() {
        return exportData == null ? exportData = new ExportData() : exportData;
    }

    public void setExportData(ExportData exportData) {
        this.exportData = exportData;
    }

    /*
    
     Methods
    
     */
    public void init() {
        if (FacesContext.getCurrentInstance().isPostback() == false) {

            //getShareCapitalTaggingData().setLocation(null);
            getShareCapitalTaggingData().setToDate(null);
            getShareCapitalTaggingData().setFromDate(null);
            getShareCapitalTaggingData().setDateSpan(null);
            getShareCapitalTaggingData().setOrganizationLocationId(null);
            getShareCapitalTaggingData().setCount(null);
            //getShareCapitalTaggingData().setTotalsPojo(null);
            getShareCapitalTaggingData().setDetailsPojo(null);
            getShareCapitalTaggingData().setTotalsPojo(null);
        }
    }

    public void generateTagging() {
        System.out.println("Start from date na may format date " + getCustomDate().formatDate(getShareCapitalTaggingData().getFromDate(), "yyyy MM dd"));
        System.out.println("Start to date na may format date 1 " + getCustomDate().formatDate1(getShareCapitalTaggingData().getToDate(), "yyyy MM dd"));
//        System.out.println("Start Location" + getDataConvert().taggingOrgs(getShareCapitalTaggingData().getOrganizationLocationId()));

        if (getShareCapitalTaggingData().getOrganizationLocationId() != null && getShareCapitalTaggingData().getFromDate() != null && getShareCapitalTaggingData().getToDate() != null) {

            //set from date and to date to string
            getShareCapitalTaggingData().setDateSpan(getCustomDate().formatDate(getShareCapitalTaggingData().getFromDate(), "MMMM dd, yyyy") + " to " + getCustomDate().formatDate(getShareCapitalTaggingData().getToDate(), "MMMM dd, yyyy"));

            /*
             - To Get Count
             */
            try {
                System.out.println("Try Catch from date " + getShareCapitalTaggingData().getFromDate());
                System.out.println("Try Catch to date " + getShareCapitalTaggingData().getToDate());
                System.out.println("Try Catch Location " + getDataConvert().taggingOrgs(getShareCapitalTaggingData().getOrganizationLocationId()));

                String query = "SELECT COUNT(sc_acctno) as acctno  "
                        + "FROM sc_tagging_view_V2 "
                        + "WHERE  post_date::date BETWEEN '" + getCustomDate().formatDate(getShareCapitalTaggingData().getFromDate(), "yyyy MM dd") + "' AND '" + getCustomDate().formatDate(getShareCapitalTaggingData().getToDate(), "yyyy MM dd") + "' "
                        + "AND ou_description ILIKE  '%" + getDataConvert().taggingOrgs(getShareCapitalTaggingData().getOrganizationLocationId()) + "%'";
                getShareCapitalTaggingData().setCount((Long) getCustomEntityManagerFactory().getFinancialDbEntityManagerFactory().createEntityManager().createNativeQuery(query).getSingleResult());

            } catch (Exception e) {
                System.out.println("Error - Count" + e.getMessage());
            }

            /*
             - use string query
             SELECT get_totals('" + getCustomDate().formatDate(getShareCapitalTaggingData().getFromDate(), "yyyy-MM-dd") + "','" + getCustomDate().formatDate(getShareCapitalTaggingData().getToDate(), "yyyy-MM-dd") + "','" + getDataConvert().taggingOrgs(getShareCapitalTaggingData().getOrganizationLocationId())
             */
            try {
                String query = "WITH sum_table AS ( "
                        + "SELECT CASE "
                        + "WHEN tv2.entry_type = 'Credit' THEN 'Total Credit' "
                        + "WHEN tv2.entry_type = 'Debit' THEN 'Total Debit' "
                        + "END AS entry_type, "
                        + "SUM(amount) AS sum_amount "
                        + "FROM sc_tagging_view_V2 tv2 "
                        + "WHERE  post_date::date BETWEEN '" + getCustomDate().formatDate(getShareCapitalTaggingData().getFromDate(), "yyyy-MM-dd") + "' AND '" + getCustomDate().formatDate(getShareCapitalTaggingData().getToDate(), "yyyy-MM-dd") + "' "
                        + "AND ou_description ILIKE '%" + getDataConvert().taggingOrgs(getShareCapitalTaggingData().getOrganizationLocationId()) + "%' "
                        + "GROUP BY tv2.entry_type "
                        + ") "
                        + "SELECT entry_type,sum_amount FROM sum_table st  "
                        + "WHERE entry_type = 'Total Credit' OR entry_type = 'Total Debit' "
                        + "UNION "
                        + "SELECT 'Total Balance' AS entry_type, "
                        + "(SELECT sum_amount FROM sum_table st WHERE entry_type = 'Total Credit') - "
                        + "(SELECT  sum_amount FROM sum_table st WHERE entry_type = 'Total Debit') AS sum_amount "
                        + "FROM sum_table ";
                getShareCapitalTaggingData().setTaggingTotals(getCustomEntityManagerFactory().getFinancialDbEntityManagerFactory().createEntityManager().createNativeQuery(query).getResultList()
                );

                //pass query result to pojo class via for each
                List<ShareCapitalTaggingTotalsPojo> totalResult = new ArrayList<>();
                for (Object[] totalObj : getShareCapitalTaggingData().getTaggingTotals()) {
                    ShareCapitalTaggingTotalsPojo sct = new ShareCapitalTaggingTotalsPojo();
                    sct.setEntryType((String) totalObj[0]);
                    sct.setTotalAmount((BigDecimal) totalObj[1]);
                    totalResult.add(sct);
                }
                getShareCapitalTaggingData().setTotalsPojo(totalResult);
            } catch (Exception e) {
                System.out.println("Error - Totals Table" + e.getMessage());

            }
            //get detailed 
            try {

                String query = "SELECT "
                        + "sc_acctno, "
                        + "acct_name, "
                        + "entry_type, "
                        + "SUM(amount), "
                        + "balance "
                        + "FROM sc_tagging_view_v2 "
                        + "WHERE  post_date::date BETWEEN '" + getCustomDate().formatDate(getShareCapitalTaggingData().getFromDate(), "yyyy-MM-dd") + "' AND '" + getCustomDate().formatDate(getShareCapitalTaggingData().getToDate(), "yyyy-MM-dd") + "' "
                        + "AND ou_description ILIKE '%" + getDataConvert().taggingOrgs(getShareCapitalTaggingData().getOrganizationLocationId()) + "%' "
                        + "GROUP BY sc_acctno,acctno,acct_name,entry_type,amount,balance";
                getShareCapitalTaggingData().setTaggingDetails(getCustomEntityManagerFactory().getFinancialDbEntityManagerFactory().createEntityManager().createNativeQuery(query).getResultList());

                //pass obj to pojo class via for each
                List<ShareCapitalTaggingDetailsPojo> detailsResult = new ArrayList<>();
                for (Object[] detailObj : getShareCapitalTaggingData().getTaggingDetails()) {
                    ShareCapitalTaggingDetailsPojo sctd = new ShareCapitalTaggingDetailsPojo();
                    sctd.setAcctno((String) detailObj[0]);
                    sctd.setName((String) detailObj[1]);
                    sctd.setEntryType((String) detailObj[2]);
                    sctd.setAmount((BigDecimal) detailObj[3]);
                    sctd.setBalance((BigDecimal) detailObj[4]);
                    detailsResult.add(sctd);
                }
                getShareCapitalTaggingData().setDetailsPojo(detailsResult);

            } catch (Exception e) {
                System.out.println("Error - Detailed Table" + e.getMessage());

            }

        }
    }

    public void export() {

        Integer columnNo;
        HSSFWorkbook workbook;
        HSSFSheet sheet;
        HSSFRow dataRow;
        HSSFCell cell;
        HSSFCellStyle cellStyle, boldStyle;
        HSSFFont font;
        ThemeDisplay themeDisplay = LiferayFacesContext.getInstance().getThemeDisplay();
        getExportData().createFolder(null, themeDisplay, "Share Capital Tagging Report", "DESCRIPTION");
        getExportData().setFilename("Share Capital Tagging Report (" + new Date() + ")");
        getExportData().setFilename(getExportData().getFilename().replace(":", ""));

        try {
            getExportData().setFilename(getExportData().getFilename().concat(".xls"));
            workbook = new HSSFWorkbook();

            cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));

            font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);

            boldStyle = workbook.createCellStyle();
            boldStyle.setFont(font);

            //create sheetname
            sheet = workbook.createSheet("Share Capital Tagging Report");
            dataRow = sheet.createRow((short) 0);
            columnNo = 1;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("SHARE CAPITAL");
            cell.setCellStyle(boldStyle);
            //use this to add space below
            dataRow = sheet.createRow(dataRow.getRowNum() + 1);
            columnNo = 1;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue(getDataConvert().labelTagging(getShareCapitalTaggingData().getOrganizationLocationId()) + "(" + (getShareCapitalTaggingData().getCount()) + ")");
            cell.setCellStyle(boldStyle);
            dataRow = sheet.createRow(dataRow.getRowNum() + 1);
            columnNo = 1;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue(getShareCapitalTaggingData().getDateSpan());
            cell.setCellStyle(boldStyle);
            dataRow = sheet.createRow(dataRow.getRowNum() + 2);

            columnNo = 1;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("Entry Type");
            cell.setCellStyle(boldStyle);

            columnNo = 2;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("Total Amount");
            cell.setCellStyle(boldStyle);

            for (int i = 0; i < getShareCapitalTaggingData().getTotalsPojo().size(); i++) {
                dataRow = sheet.createRow(dataRow.getRowNum() + 1);
                //Entry Type
                columnNo = 1;
                cell = dataRow.createCell(columnNo++);
                cell.setCellValue((String) getShareCapitalTaggingData().getTotalsPojo().get(i).getEntryType());
                cell.setCellStyle(cellStyle);
                //Total Amount
                  /*
                 To convert a bigDecimal (via excel):
                 - Cast into bigDecimal first
                 - Then convert it into double value
                 i.e.
                 (((BigDecimal)getData.DataVariable.get(i).getVar)).doubleValue())
                 */

                columnNo = 2;
                cell = dataRow.createCell(columnNo++);
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellValue(((BigDecimal) getShareCapitalTaggingData().getTotalsPojo().get(i).getTotalAmount()).doubleValue());
                cell.setCellStyle(cellStyle);
            }

            dataRow = sheet.createRow(dataRow.getRowNum() + 2);
            columnNo = 1;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("Account no.");
            cell.setCellStyle(boldStyle);
            columnNo = 2;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("Name");
            cell.setCellStyle(boldStyle);
            columnNo = 3;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("Entry Type");
            cell.setCellStyle(boldStyle);
            columnNo = 4;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("Amount");
            cell.setCellStyle(boldStyle);
            columnNo = 5;
            cell = dataRow.createCell(columnNo++);
            cell.setCellValue("Balance");
            cell.setCellStyle(boldStyle);

            for (int i = 1; i < getShareCapitalTaggingData().getDetailsPojo().size(); i++) {
                dataRow = sheet.createRow(dataRow.getRowNum() + 1);

                //Account No.
                columnNo = 1;
                cell = dataRow.createCell(columnNo++);
                cell.setCellValue((String) getShareCapitalTaggingData().getTaggingDetails().get(i)[0]);
                cell.setCellStyle(cellStyle);
                //Name
                columnNo = 2;
                cell = dataRow.createCell(columnNo++);
                cell.setCellValue((String) getShareCapitalTaggingData().getTaggingDetails().get(i)[1]);
                cell.setCellStyle(cellStyle);
                //Entry Type
                columnNo = 3;
                cell = dataRow.createCell(columnNo++);
                cell.setCellValue((String) getShareCapitalTaggingData().getTaggingDetails().get(i)[2]);
                cell.setCellStyle(cellStyle);
                //Amount
                columnNo = 4;
                cell = dataRow.createCell(columnNo++);
                cell.setCellValue(((BigDecimal) getShareCapitalTaggingData().getTaggingDetails().get(i)[3]).doubleValue());
                cell.setCellStyle(cellStyle);
                //Balance
                columnNo = 5;
                cell = dataRow.createCell(columnNo++);
                cell.setCellValue(((BigDecimal) getShareCapitalTaggingData().getTaggingDetails().get(i)[4]).doubleValue());
                cell.setCellStyle(cellStyle);
            }

            FileOutputStream fileOutputStream = new FileOutputStream(getExportData().getFilename());
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            getExportData().fileUploadByDL(getExportData().getFilename(), "Share Capital Tagging Report", themeDisplay, null);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Success", "File Exported Successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);
            File file = new File(getExportData().getFilename());
            if (file.exists()) {
                file.delete();
            }

            getExportData().beanclear();
        } catch (Exception e) {
            System.out.print("ShareCapitalController.export() " + e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while generating excel file.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }
}
