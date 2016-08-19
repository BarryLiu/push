package com.rgk.push.bean;

/**
 * Created by dexin.su on 2014/11/18.
 */
public class PushOrderData {
    private Long server_id; //t_push_text_his:id
    private String title; //t_push_text:title
    private String comments; //t_push_text:comments
    private String commands; //t_push_text:icon

    /*** for db **/
    public void setServerId(Long serverId) {
        this.server_id = serverId;
    }
    /*** for db **/

    public Long getServer_id() {
        return server_id;
    }
    public void setServer_id(Long server_id) {
        this.server_id = server_id;
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
}

