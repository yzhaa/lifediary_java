package yzh.lifediary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import yzh.lifediary.config.MySecurityConfig;
import yzh.lifediary.entity.Follow;
import yzh.lifediary.entity.MyMessage;
import yzh.lifediary.entity.User;
import yzh.lifediary.service.impl.FollowServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yzh
 * @since 2022-04-12
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    FollowServiceImpl followService;

    @PostMapping("/up")
    public MyMessage<Follow> add(HttpSession session, @RequestParam("userId") int userId) {
        User user = (User) MySecurityConfig.currentUser(session);
        if (userId == user.getId()) return new MyMessage<>(-1, null, "不能关注自己");
        Follow follow = followService.getOne(new QueryWrapper<Follow>().eq("yid", user.getId()).eq("mid", userId));
        if (follow != null) return new MyMessage<>(-1, null, "已经关注过了");
        follow = new Follow().setYid(user.getId()).setMid(userId);
        followService.getBaseMapper().insert(follow);
        return new MyMessage<>(0, follow, "成功关注");
    }

    @PostMapping("/d")
    public MyMessage<String> delete(HttpSession session, @RequestParam("userId") int userId) {
        User user = (User) MySecurityConfig.currentUser(session);
        Follow follow = followService.getOne(new QueryWrapper<Follow>().eq("yid", user.getId()).eq("mid", userId));
        if (follow == null) return new MyMessage<>(-1, null, "没有关注过");
        followService.removeById(follow.getId());
        return new MyMessage<>(0, null, "取消关注成功");
    }

    //http://127.0.0.1:8081/follow/get/10   。（不是以健值对）
    @GetMapping("/get/{userId}")
    public MyMessage<Follow> get(HttpSession session, @PathVariable String userId) {
        User user = (User) MySecurityConfig.currentUser(session);
        Follow follow = followService.getOne(new QueryWrapper<Follow>().eq("yid", user.getId()).eq("mid", userId));

        //明明有配置fastjson 解析空字符串时，为什么？？？
        if (follow == null) {
            return new MyMessage<>(0, null, "获取成功");
        }
        return new MyMessage<>(0, follow, "获取成功");
    }

    @PostMapping("/ds")
    public MyMessage<String> deleteMore(HttpSession session, @RequestParam("userIds") String ids) {

        System.out.println(ids);
        //substring 不包括endIndex那个位置
        String userIds = ids.substring(1, ids.length() - 1);
        List<Integer> listIds = new ArrayList<>();
        for (String s : userIds.split(",")) {
            listIds.add(Integer.valueOf(s));
        }
        User user = (User) MySecurityConfig.currentUser(session);
        boolean isDelete = followService.remove(new QueryWrapper<Follow>().eq("yid", user.getId()).in("mid", listIds));
        if (isDelete) return new MyMessage<>(0, null, "取消关注成功");
        return new MyMessage<>(-1, null, "没有关注过");
    }

}
