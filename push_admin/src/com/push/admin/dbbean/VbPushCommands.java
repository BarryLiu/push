package com.push.admin.dbbean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by dexin.su on 2014/11/19.
 */
public class VbPushCommands implements SqlBean {
    @Override
    public String getTableName() {
        return "v_push_commands";
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "id";
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{
                "id",
                "title",
                "commands",
                "comments",
                "imei1",
                "imei2",
                "country_name",
                "model_name",
                "version_name",
                "push_date",
                "create_date",
                "update_date",
                "update_comments",
                "count_sending",
                "count_sended",
                "count_clicked"
        };
    }

    private Long id;
    private String title;
    private String comments;
    private String commands;
    private String imei1;
    private String imei2;
    private String countryName;
    private String modelName;
    private String versionName;
    private Timestamp pushDate;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String updateComments;
    private Integer countSending;
    private Integer countSended;
    private Integer countClicked;

    private boolean canEdit=true;
    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Timestamp getPushDate() {
        return pushDate;
    }

    public void setPushDate(Timestamp pushDate) {
        this.pushDate = pushDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateComments() {
        return updateComments;
    }

    public void setUpdateComments(String updateComments) {
        this.updateComments = updateComments;
    }

    public Integer getCountSending() {
        return countSending;
    }

    public void setCountSending(Integer countSending) {
        this.countSending = countSending;
    }

    public Integer getCountSended() {
        return countSended;
    }

    public void setCountSended(Integer countSended) {
        this.countSended = countSended;
    }

    public Integer getCountClicked() {
        return countClicked;
    }

    public void setCountClicked(Integer countClicked) {
        this.countClicked = countClicked;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }
}
