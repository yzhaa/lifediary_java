package yzh.lifediary.util;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import yzh.lifediary.config.MySecurityConfig;
import yzh.lifediary.entity.Diary;
import yzh.lifediary.entity.MyMessage;
import yzh.lifediary.entity.User;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PictureUtil {

    static Log log = LogFactory.getLog(PictureUtil.class);
  public   static final String INITIAL_PATH = "pic/initial/icon_test.png";


    public static String uploadImage(MultipartFile image, String subDirectory) {
        try {
            String name = image.getOriginalFilename();
            InputStream inputStream = image.getInputStream();

            String fileName = FileCreateNameUtils.toCreateName();
            String fileType = name.substring(name.indexOf("."));

            String Directory = StaticUtils.CATALOGUE + "/" + subDirectory;
            Path directory = Paths.get(Directory);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            File f = new File(Directory + fileName + fileType);
            while (f.exists()) {
                fileName = FileCreateNameUtils.toCreateName();
                f = new File(Directory + fileName + fileType);
            }
            Files.copy(inputStream, directory.resolve(fileName + fileType));
            return StaticUtils.PREFIX + "/" + subDirectory + "/" + fileName + fileType;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }


    }

    public static boolean deleteFile(String path) {
        File file = new File(StaticUtils.CATALOGUE + path.substring(path.indexOf("pic") + 3));
        return deleteFile(file);

    }

    public static boolean deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    if (!deleteFile(f)) return false;
                }
            }
            return  file.delete();
        }
        return false;
    }

    public static List<MultipartFile> getMultipartFile(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mhsr = ((MultipartHttpServletRequest) request);
            return mhsr.getFiles("images");

        }

        return null;
    }

    //问题
    public static int uploadPics(List<MultipartFile> file) {
        int index = 0;
        for (MultipartFile multipartFile : file) {
            if (file.isEmpty()) {
                continue;
            }
            try {
                //工具类生成文件名
                String fileName = FileCreateNameUtils.toCreateName();
                String originalName = multipartFile.getOriginalFilename();
                //获取文件后缀名
                String fileType = originalName.substring(originalName.indexOf("."));
                File f = new File(StaticUtils.CATALOGUE + fileName + fileType);
                if (f.exists()) {
                    //判断这个文件是否存在，若存在则变换文件名 防止覆盖
                    fileName = FileCreateNameUtils.toCreateName();
                }
                byte[] bytes = multipartFile.getBytes();
                Path path = Paths.get(StaticUtils.CATALOGUE + fileName + fileType);
                //写入磁盘
                Files.write(path, bytes);
                index++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return index;
    }


    //使用流将图片输出，未使用。使用了地址映射

//    public static void getImage(HttpServletResponse response, String name) throws IOException {
//        response.setContentType("image/jpeg;charset=utf-8");
//        response.setHeader("Content-Disposition", "inline; filename=girls.png");
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(Files.readAllBytes(Paths.get("/").resolve(name)));
//        outputStream.flush();
//        outputStream.close();
    //}
}
