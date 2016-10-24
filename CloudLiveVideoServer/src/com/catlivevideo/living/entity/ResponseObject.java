package com.catlivevideo.living.entity;

public class ResponseObject<T> {
	private String msg;
	private int state;
	private int size;
	private int page;
	private long count;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	private T obj;
	public Object getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public ResponseObject(int state,T obj){
		this.state = state;
		this.obj = obj;
	}
	public ResponseObject(int state,String msg){
		this.state = state;
		this.msg = msg;
	}
	public ResponseObject(int state,String msg,T obj){
		this.state = state;
		this.msg = msg;
		this.obj = obj;
	}
}
