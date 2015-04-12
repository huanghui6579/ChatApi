package net.ibaixin.chat.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 附件信息
 * @author tiger
 *
 */
public class Attachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 唯一主键，该主键根据发送者_接受者_文件的md5值一起进行md5加密来的
	 */
	private String id;
	
	/**
	 * 文件发送者
	 */
	private String sender;
	
	/**
	 * 文件接受者
	 */
	private String receiver;
	
	/**
	 * 文件的原始名称
	 */
	private String fileName;
	
	/**
	 * 文件的创建时间
	 */
	private Date creationDate;
	
	/**
	 * 文件是否有缩略图，主要是图片文件有缩略图
	 */
	private boolean hasThumb;
	
	/**
	 * 文件的mime类型
	 */
	private String mimeType;
	
	/**
	 * 文件在本地的存放名称，以UUID的字符串来进行名称
	 */
	private String sotreName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isHasThumb() {
		return hasThumb;
	}

	public void setHasThumb(boolean hasThumb) {
		this.hasThumb = hasThumb;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getSotreName() {
		return sotreName;
	}

	public void setSotreName(String sotreName) {
		this.sotreName = sotreName;
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", sender=" + sender + ", receiver="
				+ receiver + ", fileName=" + fileName + ", creationDate="
				+ creationDate + ", hasThumb=" + hasThumb + ", mimeType="
				+ mimeType + ", sotreName=" + sotreName + "]";
	}
}
