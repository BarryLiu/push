package com.rgk.push.dbbean;

import java.sql.Timestamp;

/**
 * Created by dexin.su on 2014/11/13.
 */
public class TbPushCommandsHis extends BaseHisBean {
    public static final int STATUS_SENDING = 0;
    private static final int STATUS_REC_SUCC = 1;
    private static final int STATUS_CLICK = 2;

    @Override
    public String getTableName() {
        return "t_push_commands_his";
    }

    public static int getStatus(String statusStr) {
        int status = -1;
        if("00".equals(statusStr)) {
            status = 1;
        } else if("61".equals(statusStr)) {
            status = 2;
        }
        return status;
    }

    public static String getStatusDesc(int status) {
        String desc = "未知状态";
        switch(status) {
            case STATUS_SENDING:
                desc = "发送中";
                break;
            case STATUS_REC_SUCC:
                desc = "接收成功";
                break;
            case STATUS_CLICK:
                desc = "执行成功";
                break;
        }
        return desc;
    }
}
