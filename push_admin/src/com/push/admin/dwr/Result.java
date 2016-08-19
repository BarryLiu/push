package com.push.admin.dwr;

public class Result {
	private String res;
	private String msg;
	private Long arg1;
	private String title;
	
	public Result() {}
	
	public Result(String res) {
		this.res = res;
	}
	
	public Result(String res, String msg) {
		this.res = res;
		this.msg = msg;
	}
	
	public Result(String res, String msg, Long arg1) {
		this.res = res;
		this.msg = msg;
		this.arg1 = arg1;
	}
	
	public Result(String res, String msg, String title) {
		this.res = res;
		this.msg = msg;
		this.title = title;
	}
	
	public static String toJson(String res, String msg) {
		return new Result(res, msg).toString();
	}
	
	public static String toJson(String res) {
		return new Result(res).toString();
	}
	
	public static String toJson(String res, String msg, Long arg1) {
		return new Result(res, msg, arg1).toString();
	}
	
	public static String toJson(String res, String msg, String title) {
		return new Result(res, msg, title).toString();
	}
	
	public static String toJson(String res, Long arg1) {
		Result result = new Result(res);
		result.arg1 = arg1;
		return result.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if(res != null) {
			if(res.equals(Dwr.SUCCESS)) {
				sb.append("'res':1");
			} else if(res.equals(Dwr.RELOGIN)) {  //Re-Login 2
				sb.append("'res':2");
			} else if(res.equals(Dwr.REFRESH)) {  //Refresh 3
				sb.append("'res':3");
			} else if(res.equals(Dwr.FAILURE)){
				sb.append("'res':0");
			} else {
				sb.append("'res':-1");
			}
		} else {
			sb.append("'res':0");
		}
		if(msg != null) {
			sb.append(",'msg':'"+msg+"'");
		} else {
			sb.append(",'msg':null");
		}
		if(arg1 != null) {
			sb.append(",'arg1':");
			sb.append(arg1);
		} else {
			sb.append(",'arg1':null");
		}
		if(title != null) {
			sb.append(",'title':'"+title+"'");
		} else {
			sb.append(",'title':null");
		}
		sb.append("}");
		return sb.toString();
	}
//	public String getRes() {
//		return res;
//	}
//	public Result setRes(String res) {
//		this.res = res;
//		return this;
//	}
//	public String getMsg() {
//		return msg;
//	}
//	public Result setMsg(String msg) {
//		this.msg = msg;
//		return this;
//	}
	
}
