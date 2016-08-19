package com.push.admin.dbbean;

import java.sql.Timestamp;

/**
 * Created by dexin.su on 2014/11/13.
 */
public class TbPushCommandsHis implements SqlBean {
    @Override
    public String getTableName() {
        return "t_push_commands_his";
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "id";
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{
                "id",
                "push_id",
                "uuid",
                "status",
                "push_date",
                "c_update_date",
                "update_date",
                "update_comments"
        };
    }

    private Long id;
    private Long pushId;
    private String uuid;
    private Integer status;
    private Timestamp pushDate;
    private Timestamp cUpdateDate;
    private Timestamp updateDate;
    private String updateComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPushId() {
        return pushId;
    }

    public void setPushId(Long pushId) {
        this.pushId = pushId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getPushDate() {
        return pushDate;
    }

    public void setPushDate(Timestamp pushDate) {
        this.pushDate = pushDate;
    }

    public Timestamp getCUpdateDate() {
        return cUpdateDate;
    }

    public void setCUpdateDate(Timestamp cUpdateDate) {
        this.cUpdateDate = cUpdateDate;
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

    public Timestamp getcUpdateDate() {
        return cUpdateDate;
    }

    public void setcUpdateDate(Timestamp cUpdateDate) {
        this.cUpdateDate = cUpdateDate;
    }
}
