package net.ibaixin.chat.api.model;

/**
 * 文件的dto
 * @author tiger
 *
 */
public class FileDto {
	/**
	 * 文件名
	 */
	private String filename;
	
	/**
	 * 是否是缩略图，只有图片文件才有缩略图
	 */
	private boolean isThumb;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isThumb() {
		return isThumb;
	}

	public void setThumb(boolean isThumb) {
		this.isThumb = isThumb;
	}

	@Override
	public String toString() {
		return "FileDto [filename=" + filename + ", isThumb=" + isThumb + "]";
	}
}
