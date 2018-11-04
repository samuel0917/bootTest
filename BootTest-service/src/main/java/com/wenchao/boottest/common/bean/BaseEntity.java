package com.wenchao.boottest.common.bean;


import java.util.Date;


public class BaseEntity {
	
	/**
	 * 创建时间
	 */
	@Column(value = "create_time")
    private Date createTime;
	/**
	 * 创建人
	 */
	@Column(value = "create_name")
    private String createName;
	/**
	 * 创建人Id
	 */
	@Column(value = "create_id")
    private Long createId;
	/**
	 * 修改时间
	 */
	@Column(value = "modify_time")
    private Date modifyTime;
	/**
	 * 修改人
	 */
	@Column(value = "modify_name")
    private String modifyName;
	/**
	 * 修改人
	 */
	@Column(value = "modify_id")
    private Long modifyId;
	/**
	 * 修改人
	 */
	@Column(value = "status_code")
    private int statusCode;
	/**
	 * 修改人
	 */
	@Column(value = "status_text")
    private String statusText;
    /**
	 * 修改人
	 */
	@Column(value = "remark")
    private String remark;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyName() {
		return modifyName;
	}
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
	public Long getModifyId() {
		return modifyId;
	}
	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

    
    
	
    
    
    
}
