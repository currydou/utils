package com.curry.storage;

import java.util.List;

/**
 * Created by next on 2018/6/8.
 */

public class QuickAccount  {


    private int pageStatus;
    private Integer applyStatus;
    private String creator;
    private String invoice_no;
    private String invoiceType;
    private String invoiceDate;
    private Double money;
    private Double applyMoney;
    private String updateTime;
    private String createTime;
    private String uuid;
    private String externalUUID;//选择过来的发票都会有这个id，快速记账的没有
    private String company;
    private String remark;
    private String costTag;
    private Integer costTagId;
    private String teamId;//标签所在的团队
    private String costTagImg;
    private Integer feeFlag;//是否是企业费用标签
    private String applySheet; //代表当前报销单的uuid
    private List<String> images;

    private Double taxAmount;
    private String feeFlagImg;
    private String invoiceSource;


    public int getPageStatus() {
        return pageStatus;
    }

    public void setPageStatus(int pageStatus) {
        this.pageStatus = pageStatus;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Double getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(Double applyMoney) {
        this.applyMoney = applyMoney;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExternalUUID() {
        return externalUUID;
    }

    public void setExternalUUID(String externalUUID) {
        this.externalUUID = externalUUID;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCostTag() {
        return costTag;
    }

    public void setCostTag(String costTag) {
        this.costTag = costTag;
    }

    public Integer getCostTagId() {
        return costTagId;
    }

    public void setCostTagId(Integer costTagId) {
        this.costTagId = costTagId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getCostTagImg() {
        return costTagImg;
    }

    public void setCostTagImg(String costTagImg) {
        this.costTagImg = costTagImg;
    }

    public Integer getFeeFlag() {
        return feeFlag;
    }

    public void setFeeFlag(Integer feeFlag) {
        this.feeFlag = feeFlag;
    }

    public String getApplySheet() {
        return applySheet;
    }

    public void setApplySheet(String applySheet) {
        this.applySheet = applySheet;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getFeeFlagImg() {
        return feeFlagImg;
    }

    public void setFeeFlagImg(String feeFlagImg) {
        this.feeFlagImg = feeFlagImg;
    }

    public String getInvoiceSource() {
        return invoiceSource;
    }

    public void setInvoiceSource(String invoiceSource) {
        this.invoiceSource = invoiceSource;
    }
}
