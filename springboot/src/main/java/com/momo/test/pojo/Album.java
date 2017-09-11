package com.momo.test.pojo;
// 相册实体类

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class Album implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator="system_uuid")
    @GenericGenerator(name="system_uuid",strategy="uuid")
	private String albumId;
	/**
	 * 相册名称
	 */
	@Column(nullable = true, unique = false)
	private String albumName;
	/**
	 * 封面图
	 */
	@Column(nullable = false, unique = false)
	private String cover;
	/**
	 * 创建日期
	 */
	@Column(nullable = true, unique = false)
	private Date createDate;
	/**
	 * 描述
	 */
	@Column(nullable = false, unique = false)
	private String albumDesc;
	/**
	 *  用户id
	 */
	
	private String userId;
	/**
	 * 排序
	 */
	@Column(nullable = true, unique = true)
	private Integer sort;
	/**
	 * 相册状态（0 启用 1删除）
	 */
	@Column(nullable = true, unique = false)
	private int status;
	/**
	 * 相册权限（0 公开 1 好友 2仅自己 ）
	 */
	private int authority;
	/**
	 * @return the albumId
	 */
	public String getAlbumId() {
		return albumId;
	}
	/**
	 * @param albumId the albumId to set
	 */
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	/**
	 * @return the albumName
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * @param albumName the albumName to set
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}
	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the albumDesc
	 */
	public String getAlbumDesc() {
		return albumDesc;
	}
	/**
	 * @param albumDesc the albumDesc to set
	 */
	public void setAlbumDesc(String albumDesc) {
		this.albumDesc = albumDesc;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the authority
	 */
	public int getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	
	
}
