package com.waterelephant.controller.system;

import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.utils.CdnUploadTools;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.SystemConstant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-06 13:40
 **/
@Controller
@RequestMapping("/system/upload")
public class UploadController {

    private Logger logger = Logger.getLogger(UploadController.class);
    private static final String IMG_URL = "https://waterelephant.oss-cn-shanghai.aliyuncs.com/";

    /*@ResponseBody
    @RequestMapping(value = "uploadImage.do")
    public BjuiDto uploadImage( HttpServletResponse response,@RequestParam(value = "upfile", required = false) MultipartFile file) throws Exception {
        BjuiDto bjuiDto = new BjuiDto();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        long fileSize = file.getSize();
        if (!("".equals(fileType) || "gif".equalsIgnoreCase(fileType) || "jpg".equalsIgnoreCase(fileType)
                || "jpeg".equalsIgnoreCase(fileType) || "png".equalsIgnoreCase(fileType))) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("图片格式错误，仅支持jpg/png/gif/jpeg");
            return bjuiDto;
        } else if (fileSize > 2 * 1024 * 1024) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("图片大小不能超过2M");
            return bjuiDto;
        } else {
            InputStream fileInputStream = null;
            String curDate = CommUtils.convertDateToString(new Date(), "yyyy-MM-dd");
            String path = SystemConstant.IMG_PATH + "upload/cms/" + curDate;
            try {
                fileName = CommUtils.getUUID() + "." + fileType;
                // 上传到oss
                this.upLoadOss(file, "beadwalletmallImg/upload/cms/" + curDate + "/" + fileName);
                File targetFile = new File(path, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                file.transferTo(targetFile);
            } catch (Exception e) {
                bjuiDto.setStatusCode("300");
                bjuiDto.setMessage("上传失败");
                logger.error("", e);
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        bjuiDto.setStatusCode("300");
                        bjuiDto.setMessage("系统错误");
                        logger.error("", e);
                    }
                }
            }
            String remoteFilePath = IMG_URL + "beadwalletmallImg/upload/cms/" + curDate + "/" + fileName;
            bjuiDto.setStatusCode("200");
            bjuiDto.setFilename(remoteFilePath);
            return bjuiDto;
        }
    }*/

    /**
     * 上传内容图片
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "uploadImage.do", method = RequestMethod.POST)
    public void uploadImage(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "imgFile", required = false) MultipartFile file) throws Exception {
        String state = "SUCCESS";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        long fileSize = file.getSize();
        if (!("".equals(fileType) || "gif".equalsIgnoreCase(fileType) || "bmp".equalsIgnoreCase(fileType)
                || "jpg".equalsIgnoreCase(fileType) || "jpeg".equalsIgnoreCase(fileType)
                || "png".equalsIgnoreCase(fileType) || "mp3".equalsIgnoreCase(fileType))) {
            state = "图片格式错误，仅支持jpg/png/gif/jpeg/bmp";
        } else if (fileSize > 10 * 1024 * 1024) {
            state = "图片大小不能超过2M";
        } else {
            InputStream fileInputStream = null;
            String curDate = CommUtils.convertDateToString(new Date(), "yyyy-MM-dd");
            String path = SystemConstant.IMG_PATH + "image/cms/" + curDate;
            try {
                fileName = CommUtils.getUUID() + "." + fileType;
                upLoadOss(file, "beadwalletImg/image/cms/" + curDate + "/" + fileName);
                File targetFile = new File(path, fileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                file.transferTo(targetFile);
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }

            String remoteFilePath = SystemConstant.IMG_URL + "beadwalletImg/image/cms/" + curDate + "/" + fileName;

            String callback = request.getParameter("callback");

           /* String result = "{\"name\":\"" + fileName + "\", \"originalName\": \"" + file.getOriginalFilename()
                    + "\", \"size\": " + fileSize + ", \"state\": \"" + state + "\", \"type\": \"" + fileType
                    + "\", \"url\": \"" + remoteFilePath + "\"}";*/

            String result = "{\"error\":0, \"url\": \"" + remoteFilePath + "\"}";

            result = result.replaceAll("\\\\", "\\\\");

            if (callback == null) {
                response.getWriter().print(result);
            } else {
                response.getWriter().print("<script>" + callback + "(" + result + ")</script>");
            }
        }
    }

    //图片上传
    private String upLoadOss(MultipartFile file, String fileName) {
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
        // // 上传
        CdnUploadTools.uploadPic(is, fileName);
        return "";
    }

}
