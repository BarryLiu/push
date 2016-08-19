package com.rgk.push.bean;

public class Response {
	public static final String SUCESS = "{\"code\":\"00\"}";
	public static final String FAIL_NO_ID = "{\"code\":\"01\"}";
	public static final String FAIL_NO_DATA = "{\"code\":\"02\"}";
	public static final String FAIL_DUP_UUID = "{\"code\":\"03\"}";
	public static final String FAIL = "{\"code\":\"99\"}";
	public static final String OVER_HALF_YEAR = "{\"code\":\"01\"}";
	
	public static class Bean {
		private String name;
		private String code;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	
	public static class PushApkRes {
		private String code = "00";
		private String name = "push_apk";
		private PushApkData details;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public PushApkData getDetails() {
			return details;
		}
		public void setDetails(PushApkData details) {
			this.details = details;
		}
	}
	
	public static class PushClientRes {
		private String code = "00";
		private String name = "push_client";
		private PushClientData details;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public PushClientData getDetails() {
			return details;
		}
		public void setDetails(PushClientData details) {
			this.details = details;
		}
	}
	
	public static class PushConfigRes {
		private String code = "00";
		private String name = "push_config";
		private PushConfigData details;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public PushConfigData getDetails() {
			return details;
		}
		public void setDetails(PushConfigData details) {
			this.details = details;
		}
	}
	
	public static class PushLinkRes {
		private String code = "00";
		private String name = "push_link";
		private PushLinkData details;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public PushLinkData getDetails() {
			return details;
		}
		public void setDetails(PushLinkData details) {
			this.details = details;
		}
	}
	
	public static class PushTextRes {
		private String code = "00";
		private String name = "push_text";
		private PushTextData details;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public PushTextData getDetails() {
			return details;
		}
		public void setDetails(PushTextData details) {
			this.details = details;
		}
	}

    public static class PushOrderRes {
        private String code = "00";
        private String name = "push_commands";
        private PushOrderData details;
        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public PushOrderData getDetails() {
            return details;
        }
        public void setDetails(PushOrderData details) {
            this.details = details;
        }
    }
}
