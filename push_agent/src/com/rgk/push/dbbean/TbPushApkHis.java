package com.rgk.push.dbbean;

public class TbPushApkHis extends BaseHisBean {
	public static final int STATUS_SENDING = 0;
	public static final int STATUS_REC_SUCC = 1;
	public static final int STATUS_DOWNLOND = 2;
	public static final int STATUS_INSTALLER = 3;
	public static final int STATUS_DELETE = 4;

	@Override
	public String getTableName() {
		return "t_push_apk_his";
	}
	
	public static int getStatus(String statusStr) {
		int status = -1;
		if("00".equals(statusStr)) {
			status = 1;
		} else if("11".equals(statusStr)) {
			status = 2;
		} else if("12".equals(statusStr)) {
			status = 3;
		} else if("13".equals(statusStr)) {
			status = 4;
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
		case STATUS_DOWNLOND:
			desc = "用户下载";
			break;
		case STATUS_INSTALLER:
			desc = "用户安装";
			break;
		case STATUS_DELETE:
			desc = "用户删除";
			break;
		}
		return desc;
	}

}
