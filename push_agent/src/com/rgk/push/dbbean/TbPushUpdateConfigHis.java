package com.rgk.push.dbbean;

public class TbPushUpdateConfigHis extends BaseHisBean {
	public static final int STATUS_SENDING = 0;
	public static final int STATUS_REC_SUCC = 1;
	public static final int STATUS_UPDATE_SUCC = 2;

	@Override
	public String getTableName() {
		return "t_push_update_config_his";
	}
	
	public static int getStatus(String statusStr) {
		int status = -1;
		if("00".equals(statusStr)) {
			status = 1;
		} else if("51".equals(statusStr)) {
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
		case STATUS_UPDATE_SUCC:
			desc = "更新成功";
			break;
		}
		return desc;
	}
	
	

}
