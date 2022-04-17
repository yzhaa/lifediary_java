package yzh.lifediary.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import yzh.lifediary.entity.MyMessage;
import yzh.lifediary.util.PictureUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzh
 * @since 2022-04-01
 */
@RestController
@RequestMapping("/pic")
public class PictureController {



//    @GetMapping("/get/{name}")
//    public void get(HttpServletResponse response, @PathVariable("name") String name) throws IOException {
//        PictureUtil.getImage(response, name);
//    }


//    @PostMapping(value = "/up/{type}")
//    public MyMessage upload(HttpServletRequest request, @PathVariable("type") String type) {
//        if (!type.equals("icon")) {
//            return  new MyMessage(-1, null,"请输入正确上传类型");
//        }
//
//        if (request instanceof MultipartHttpServletRequest ) {
//            MultipartHttpServletRequest mhsr = ((MultipartHttpServletRequest) request);
//            List<MultipartFile> list = mhsr.getFiles("images");
//            List<Picture> urls = new ArrayList<>(list.size());
//            if (list.size() != 0) {
//                Picture picture = new Picture();
//                for (MultipartFile f : list) {
//                    String path;
//                    if (!( path= PictureUtil.uploadImage(f,type)).equals("")) {
//                        picture.setPath(path);
//                        picture.setId(pictureService.getBaseMapper().insert(picture));
//                        urls.add(picture);
//                    }
//                }
//
//            }
//
//            return new MyMessage<>(0, urls, "上传成功");
//        }
//
//        return  new MyMessage<>(-1,null, "请上传图片");
//    }
}
