package com.momo.test.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
@Entity
public class Image implements Serializable{
	
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
	private String imageId;
	/**
	 * 照片原始文件名字
	 */
	@Column(nullable = false, unique = false)
	private String originalName;
	/**
	 * 上传日期
	 */
	@Column(nullable = false, unique = false)
	private Date createTime;
	/**
	 * 照片描述
	 */
	@Column(nullable = true, unique = false)
	private String imageDesc;
	/**
	 * 相册id
	 */
	@Column(nullable = true, unique = false)
	private String albumId;
	/**
	 *  状态(0 启用 1删除)
	 */
	@Column(nullable = true, unique = false)
	private int status;
	/**
	 * 照片地址
	 */
	@Column(nullable = true, unique = false)
	private String imageUrl;
	/**
	 * 图片大小
	 */
	@Column(nullable = true, unique = false)
	private String imageSize;
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
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the imageSize
	 */
	public String getImageSize() {
		return imageSize;
	}

	/**
	 * @param imageSize the imageSize to set
	 */
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	/**
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	/**
	 * @return the originalName
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @param originalName the originalName to set
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the imageDesc
	 */
	public String getImageDesc() {
		return imageDesc;
	}

	/**
	 * @param imageDesc the imageDesc to set
	 */
	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}

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

	
	
}
