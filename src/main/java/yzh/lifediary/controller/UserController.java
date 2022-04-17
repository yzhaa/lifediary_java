package yzh.lifediary.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import yzh.lifediary.config.MySecurityConfig;
import yzh.lifediary.entity.MyMessage;
import yzh.lifediary.entity.User;
import yzh.lifediary.service.UserService;
import yzh.lifediary.util.PictureUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzh
 * @since 2022-03-13
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public void login() {


    }

    @GetMapping("/some")
    public String getSome() {
        return "some";
    }

    @PostMapping("/change")
    public void changeIcon() {

    }

    @GetMapping("/isExpire")
    public MyMessage<String> isExpire() {
        return new MyMessage<>(0, null, "没有过期");
    }

//
//    @PostMapping("/modify")
//    public MyMessage<User> modify(HttpSession session, @RequestParam("id") int id) {
//        User user = (User) MySecurityConfig.currentUser(session);
//
//        userService.updateById(user.setIcon(pictureService.getById(id)).setIconId(id));
//        return new MyMessage<>(0, user, "修改成功");
//    }

    @PostMapping("/modify/info")
    public MyMessage<User> modifyInfo(HttpSession session, @RequestParam String username, @RequestParam String password) {
        User user = (User) MySecurityConfig.currentUser(session);
        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            userService.updateById(user.setName(username).setPassword(passwordEncoder.encode(password)));
            return new MyMessage<>(0, user, "修改成功");
        }
        return new MyMessage<>(0, null, "修改失败");

    }


    @PostMapping("/modify/icon")
    public MyMessage<User> modifyIcon(HttpSession session, HttpServletRequest request) {
        User user = (User) MySecurityConfig.currentUser(session);

        List<MultipartFile> list = PictureUtil.getMultipartFile(request);
        if (list.size() != 0) {
            MultipartFile f = list.get(0);
            String path;
            if (!(path = PictureUtil.uploadImage(f, "icon")).equals("")) {
                PictureUtil.deleteFile(user.getIconPath());
                user.setIconPath(path);
                userService.updateById(user);
            }
        }


        return new MyMessage<>(0, user, "修改成功");
    }


}

