package com.cmw.core.kit.email;
/*
 * 关联字符到关联资源的映射
 */
public class RelatedMap {

	//关联标识
	private String cid;
	//关联资源url
	private String url;
	
	public RelatedMap() {
	
	}
	
	public RelatedMap(String cid, String url) {
		super();
		this.cid = cid;
		this.url = url;
	}
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
