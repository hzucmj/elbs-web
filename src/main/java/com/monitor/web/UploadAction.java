/**
 * 
 */
package com.monitor.web;

import java.io.File;
import java.util.logging.Logger;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipartAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;
import org.zengsource.util.IDUtil;

import com.monitor.Functions;
import com.monitor.model.User;
import com.monitor.service.UserService;

/**
 * @author hzucmj
 * 
 */
public class UploadAction extends MultipartAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String UPLOAD_SUCCESS = "上传成功！";
	private final String UPLOAD_FAILURE = "上传失败！";
	private final String UPLOAD_ERROR = "上传不能为空！";
	
	private String currentUser;
	private String uid;
	
	UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AbstractView doService() throws MvcException {
		String result = this.uploadImage();
		if (result.equals(this.UPLOAD_SUCCESS)) {
			return new HtmlView("<script>window.location.href='./profile.jxp?id=" + uid + "#r_uploadAvatar'</script>");
		} else {
			return new HtmlView("<script>alert('" + result + "');window.location.href='./profile.jxp?id=" + uid + "#r_uploadAvatar'</script>");
		}
	}

	public String uploadImage() {
		this.currentUser = Functions.getLocalUser();
		Criterion[] criterions = new Criterion[]{
				Restrictions.eq("username", currentUser)
		};
		User user = (User) this.userService.queryUser(criterions);
		this.uid = user.getUid();
		//获取网站根目录 
		String rootPath = getContext().getRootPath();
		//创建存储路径
		File usrDir = new File(rootPath + "/images/avatar/");
		if (!usrDir.exists() || usrDir.isFile()){
			usrDir.mkdirs();
		}
		String filename = "";
		//从前台获取文件avatar
		FileItem picItem = getFileItem("avatar");
		if (picItem != null) {
			//如果上传不为空
			String uploadName = picItem.getName();
			//用户名为uid加后缀名
			filename = this.uid + uploadName.substring(uploadName.lastIndexOf("."));
			logger.info("==========\nuploadname: " + uploadName + "\nfilename: " + filename + "\n==========\n");
			logger.info("upload path: " + usrDir + "/" + filename);
			File diskFile = saveFile("avatar", usrDir + "/" + this.uid);
			if (diskFile != null) {
				user.setPhoto(filename.toLowerCase());
				this.userService.updateUser(user);
				return this.UPLOAD_SUCCESS;
			} else {
				return this.UPLOAD_FAILURE;
			}
		} else {
			return this.UPLOAD_ERROR;
		}
	}

	public boolean uploadFile() {
		return false;
	}

}
