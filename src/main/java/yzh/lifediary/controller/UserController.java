package yzh.lifediary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import yzh.lifediary.config.MySecurityConfig;
import yzh.lifediary.entity.MyMessage;
import yzh.lifediary.entity.User;
import yzh.lifediary.entity.ov.DiaryItemOv;
import yzh.lifediary.entity.ov.Search;
import yzh.lifediary.entity.ov.UserFollowOV;
import yzh.lifediary.service.impl.DiaryServiceImpl;
import yzh.lifediary.service.impl.UserServiceImpl;
import yzh.lifediary.util.PictureUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    UserServiceImpl userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    DiaryServiceImpl diaryService;

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

    @PostMapping("/modify/username")
    public MyMessage<User> modifyInfo(HttpSession session, @RequestParam String username) {
        User user = (User) MySecurityConfig.currentUser(session);
        if (username != null &&!username.equals("") ) {
            userService.updateById(user.setName(username));
            return new MyMessage<>(0, user, "修改成功");
        }
        return new MyMessage<>(-1, null, "修改失败");

    }

    @PostMapping("/modify/password")
    public MyMessage<User> modifyInfo(HttpSession session, @RequestParam("newpw") String password,@RequestParam("oldpw") String oldPW) {
        User user = (User) MySecurityConfig.currentUser(session);
        if (passwordEncoder.matches(oldPW, user.getPassword())) {
            if (password != null && !password.equals("")) {
                userService.updateById(user.setPassword(passwordEncoder.encode(password)));
                return new MyMessage<>(0, user, "修改成功");
            } else {
                return new MyMessage<>(-1, null, "修改失败");
            }
        } else {
            return new MyMessage<>(-1, null, "原密码错误");
        }

    }

    @PostMapping("/modify/icon")
    public MyMessage<User> modifyIcon(HttpSession session, HttpServletRequest request) {
        User user = (User) MySecurityConfig.currentUser(session);

        List<MultipartFile> list = PictureUtil.getMultipartFile(request);
        if (list.size() != 0) {
            MultipartFile f = list.get(0);
            String path;
            if (!(path = PictureUtil.uploadImage(f, "icon")).equals("")) {
                if (!user.getIconPath().equals(PictureUtil.INITIAL_PATH)) PictureUtil.deleteFile(user.getIconPath());
                user.setIconPath(path);
                userService.updateById(user);
            }
        }


        return new MyMessage<>(0, user, "修改成功");
    }

    @GetMapping("/follow")
    public MyMessage<List<UserFollowOV>> getFollow(HttpSession session) {
        User user = (User) MySecurityConfig.currentUser(session);
        return new MyMessage<>(0, userService.getBaseMapper().getFollow(user.getId()), "查询成功");
    }


    @PostMapping("/register")
    public MyMessage<User> register(@RequestParam int account, @RequestParam String username, @RequestParam String password) {
        User user = userService.getOne(new QueryWrapper<User>().eq("account", account));
        if (user == null) {
            userService.getBaseMapper().addUser(account, username, passwordEncoder.encode(password), PictureUtil.INITIAL_PATH);
            return new MyMessage<>(0, null, "注册成功");

        }
        return new MyMessage<>(-1, user, "该帐号已存在");

    }

    @GetMapping("search/r/{condition}")
    public MyMessage<Search> searchResult(HttpSession session,@PathVariable("condition") String content) {
        User user = (User) MySecurityConfig.currentUser(session);
        List<UserFollowOV> users = userService.getBaseMapper().getUserAndIsFollow(content,user.getId());
        List<DiaryItemOv> diaryItemOvs = diaryService.getBaseMapper().getSearchTitle(content, user.getId());
        return new MyMessage<>(0, new Search().setDiarys(diaryItemOvs).setUserFollowOVS(users), "查询成功");
    }

    @GetMapping("search/m/{condition}")
    public MyMessage<List<String>> searchMatch(HttpSession session,@PathVariable("condition") String content) {
        List<String> list = new LinkedList<>();
        List<String> users = userService.getBaseMapper().getNames(content);
        List<String> diaryItemOvs = diaryService.getBaseMapper().getTitle(content);
        list.addAll(users);
        list.addAll(diaryItemOvs);

        return new MyMessage<>(0, list.stream().distinct().collect(Collectors.toList()), "查询成功");
    }

}

