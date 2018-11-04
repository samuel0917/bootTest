package com.wenchao.boottest.entity.mybatis;

import java.util.Date;

import com.wenchao.boottest.common.bean.Column;
import com.wenchao.boottest.common.bean.Id;
import com.wenchao.boottest.common.bean.Table;


@Table(value = "sys_access_log")
public class Log {
	/**
	 * 主键
	 */
	@Id(value = "id")
	private Long id;
	/**
	 * 请求url
	 */
	@Column(value = "url")
	private String url;
	/**
	 * 请求参数
	 */
	@Column(value = "params")
	private String params;
	
	/**
	 * 请求方式 get post
	 */
	@Column(value = "request_type")
	private String requestType;
	/**
	 * 请求类方法
	 */
	@Column(value = "class_method")
	private String classMethod;
	
	/**
	 * 请求用户id
	 */
	@Column(value = "user_id")
	private Long userId;
	/**
	 * 请求用户名称
	 */
	@Column(value = "username")
	private String username;
	/**
	 * 请求用户角色
	 */
	@Column(value = "user_role")
	private String userRole;
	/**
	 * 请求时间
	 */
	@Column(value = "create_time")
	private Date createTime;
	/**
	 * 请求ip
	 */
	@Column(value = "ip")
	private String ip;
	
	/**
	 * 请求来源
	 */
	@Column(value = "from_app")
	private String fromApp;
	
	/**
	 * 日志类型id
	 */
	@Column(value = "log_cata_id")
	private Integer logCataId;
   
	/**
	 * 日志类型
	 */
	@Column(value = "log_cata_text")
	private String logCataText;
	
	/**
	 * 处理时间
	 */
	@Column(value = "process_time")
	private Long processTime;
	
	/**
	 * 日志模块
	 */
	@Column(value = "log_module")
	private String logModule;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
   
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }
   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
   
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFromApp() {
		return fromApp;
	}

	public void setFromApp(String fromApp) {
		this.fromApp = fromApp;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Integer getLogCataId() {
		return logCataId;
	}

	public void setLogCataId(Integer logCataId) {
		this.logCataId = logCataId;
	}

	public String getLogCataText() {
		return logCataText;
	}

	public void setLogCataText(String logCataText) {
		this.logCataText = logCataText;
	}

	public Long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}

	public String getLogModule() {
		return logModule;
	}

	public void setLogModule(String logModule) {
		this.logModule = logModule;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getClassMethod() {
		return classMethod;
	}

	public void setClassMethod(String classMethod) {
		this.classMethod = classMethod;
	}
    
    
}