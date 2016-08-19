package com.push.admin.dbbean;

import java.sql.Timestamp;

/**
 * Created by dexin.su on 2014/11/19.
 */
public class VbPushCommandsHis implements SqlBean {
    @Override
    public String getTableName() {
        return "v_push_commands_his";
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
                "title",
                "commands",
                "comments",
                "status_name",
                "status",
                "push_date",
                "pushed_date",
                "c_update_date",
                "update_date",
                "update_comments"
        };
    }

    private Long id;
    private Long pushId;
    private String uuid;
    private Integer status;
    private String title;
    private String comments;
    private String commands;
    private String statusName;
    private Timestamp pushDate;
    private Timestamp pushedDate;
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


    public Timestamp getPushedDate() {
        return pushedDate;
    }

    public void setPushedDate(Timestamp pushedDate) {
        this.pushedDate = pushedDate;
    }

    public void setPushId(Long pushId) {
        this.pushId = pushId;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Timestamp getcUpdateDate() {
        return cUpdateDate;
    }

    public void setcUpdateDate(Timestamp cUpdateDate) {
        this.cUpdateDate = cUpdateDate;
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

}
