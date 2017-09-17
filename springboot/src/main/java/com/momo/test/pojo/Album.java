package com.momo.test.pojo;
// 相册实体类

import java.io.Serializable;
import java.time.LocalDateTime;
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
	private LocalDateTime createDate;
	/**
	 * 修改日期
	 */
	@Column(nullable = false, unique = false)
	private LocalDateTime updateDate;
	/**
	 * 描述
	 */
	@Column(nullable = false, unique = false)
	private String albumDesc;
	/**
	 *  用户id
	 */
	@Column(nullable = true, unique = false)
	private String userId;
	/**
	 * 相册状态（0 启用 1删除）
	 */
	@Column(nullable = true, unique = false)
	private int status;
	/**
	 * 分类
	 */
	@Column(nullable = true, unique = false)
	private int albumClassify;
	/**
	 * @return the albumClassify
	 */
	public int getAlbumClassify() {
		return albumClassify;
	}
	/**
	 * @param albumClassify the albumClassify to set
	 */
	public void setAlbumClassify(int albumClassify) {
		this.albumClassify = albumClassify;
	}
	/**
	 * 相册权限（0 公开 1 好友 2仅自己 ）
	 */
	@Column(nullable = true, unique = false)
	private int systemAuthority;
	@Column(nullable = false, unique = false)
	private int imageSize;
	// 其他权限 是否允许转载等
	@Column(nullable = false, unique = false)
	private int otherAuthority;
	/**
	 * @return the systemAuthority
	 */
	public int getSystemAuthority() {
		return systemAuthority;
	}
	/**
	 * @param systemAuthority the systemAuthority to set
	 */
	public void setSystemAuthority(int systemAuthority) {
		this.systemAuthority = systemAuthority;
	}
	/**
	 * @return the otherAuthority
	 */
	public int getOtherAuthority() {
		return otherAuthority;
	}
	/**
	 * @param otherAuthority the otherAuthority to set
	 */
	public void setOtherAuthority(int otherAuthority) {
		this.otherAuthority = otherAuthority;
	}
	/**
	 * @return the albumId
	 */
	public String getAlbumId() {
		return albumId;
	}
	/**
	 * @return the imageSize
	 */
	public int getImageSize() {
		return imageSize;
	}
	/**
	 * @param imageSize the imageSize to set
	 */
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
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
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(LocalDateTime createDate) {
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
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	
	
	
}
