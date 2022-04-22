package yzh.lifediary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import yzh.lifediary.config.MySecurityConfig;
import yzh.lifediary.entity.Diary;
import yzh.lifediary.entity.Like;
import yzh.lifediary.entity.MyMessage;
import yzh.lifediary.entity.User;
import yzh.lifediary.service.impl.DiaryServiceImpl;
import yzh.lifediary.service.impl.LikeServiceImpl;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikeServiceImpl likeService;
    @Autowired
    DiaryServiceImpl diaryService;

    @PostMapping("/up")

    public MyMessage<Like> like(HttpSession session, @RequestParam("diaryId") int diaryId) {
        //判断一下，后台是否已经点赞了，健壮性，真实性。不仅前端要保持，后台也要保持
        User user = (User) MySecurityConfig.currentUser(session);
        Like like = likeService.getOne(new QueryWrapper<Like>().eq("d_id", diaryId).eq("u_id", user.getId()));
        if (like != null) {
            return new MyMessage<Like>().setCode(-1).setMessage("已经赞过");
        }
        like = new Like();
        likeService.getBaseMapper().insert(like.setDId(diaryId).setUId(user.getId()));

        //为了保证原子行，diary的点赞数量，+1操作，应该一气呵成，获得点赞数量，跟+1. 可以在mysql中保证（触发器，或者调用函数）
        diaryService.getBaseMapper().addLkeCount(diaryId);
        return new MyMessage<>(0, like, "成功赞了");
    }

    @PostMapping("/d")
    public MyMessage<String> unLike(HttpSession session, @RequestParam("diaryId") int diaryId) {
        User user = (User) MySecurityConfig.currentUser(session);
        Like like = likeService.getOne(new QueryWrapper<Like>().eq("d_id", diaryId).eq("u_id", user.getId()));
        if (like==null) {
            return new MyMessage<String>().setCode(-1).setMessage("没有赞过");
        }

        likeService.removeById(like.getId());
        //为了保证原子行，diary的点赞数量，+1操作，应该一气呵成，获得点赞数量，跟+1. 可以在mysql中保证（触发器，或者调用函数）
        diaryService.getBaseMapper().deLkeCount(diaryId);
        return new MyMessage<>(0,null , "成功取消");
    }

}
