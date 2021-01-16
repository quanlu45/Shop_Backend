package com.cdd.eshop.controller;

import com.cdd.eshop.bean.dto.ResponseDTO;
import com.cdd.eshop.common.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 文件控制器
 *
 * @author quan
 * @date 2021/01/08
 */
@Slf4j
@RestController
@RequestMapping("/v1/file")
public class FileController {


//    @Autowired
//    StorageClient storageClient;

    private static final String PrePath = System.getProperty("user.dir") + File.separator +"static";

    @PostMapping("upload")
    public ResponseDTO upload(@RequestParam(value = "file",required = true) MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                return ResponseDTO.error(StatusEnum.PARAM_ERROR,"文件名不能为空");
            }
            int index = fileName.lastIndexOf(".");
            if (index<0) {
                return ResponseDTO.error(StatusEnum.PARAM_ERROR,"后缀名不能为空");
            }
            String suffix =fileName.substring(index+1);
            if (StringUtils.isEmpty(suffix)) {
                return ResponseDTO.error(StatusEnum.PARAM_ERROR,"后缀名不能为空");
            }
            //  StorePath storePath =  storageClient.uploadFile(file.getInputStream(),file.getSize(),suffix,null);
            fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(PrePath +File.separator+fileName)));
            out.write(file.getBytes());
            out.flush();
            out.close();

            return ResponseDTO.success().withKeyValueData("path",File.separator+fileName);
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseDTO.error().msg("上传失败！"+e.toString());
        }
    }


}
