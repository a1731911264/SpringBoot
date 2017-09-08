package com.momo.test.pojo;
// 相册实体类

import java.io.Serializable;
import java.util.Date;

public class Album implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String albumId;
	// fengmiantu
	private String cover;
	// 创建日期
	private Date createDate;
	// 描述
	private String albumDesc;
	
	private String userId;
}
