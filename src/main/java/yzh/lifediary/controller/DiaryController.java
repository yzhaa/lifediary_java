package yzh.lifediary.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import yzh.lifediary.config.MySecurityConfig;
import yzh.lifediary.entity.*;
import yzh.lifediary.entity.ov.DiaryDetails;
import yzh.lifediary.entity.ov.DiaryItemOv;
import yzh.lifediary.service.impl.DiaryImageServiceImpl;
import yzh.lifediary.service.impl.DiaryServiceImpl;
import yzh.lifediary.service.impl.LikeServiceImpl;
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
 * @since 2022-04-12
 */
@RestController
@RequestMapping("/diary")
public class DiaryController {
    @Autowired
    DiaryServiceImpl diaryService;
    @Autowired
    DiaryImageServiceImpl diaryImageService;


    @Autowired
    LikeServiceImpl likeService;

    @PostMapping("/up")
    public MyMessage<Diary> upload(HttpSession session, HttpServletRequest request, @RequestParam("title") String title, @RequestParam("content") String content) {
        boolean isFirst = true;
        List<MultipartFile> list = PictureUtil.getMultipartFile(request);
        if (!title.equals("") && !content.equals("") && list.size() > 0) {
            User user = (User) MySecurityConfig.currentUser(session);

            Diary diary = new Diary();
            diaryService.getBaseMapper().insert(diary.setContent(content).setTitle(title).setUserId(user.getId()));
            for (MultipartFile f : list) {
                String path;
                if (!(path = PictureUtil.uploadImage(f, "diary/" + user.getId() + "/" + diary.getId())).equals("")) {
                    System.out.println("path:" + path);
                    DiaryImage diaryImage = new DiaryImage().setDiaryId(diary.getId()).setPath(path);
                    if (isFirst) {
                        diaryImage.setMain(true);
                        isFirst = false;
                    }
                    diaryImageService.getBaseMapper().insert(diaryImage);

                }

            }
            return new MyMessage<>(0, diary, "上传成功");
        }
        return new MyMessage<>(0, null, "上传失败");
    }

    @PostMapping("/delete")
    public MyMessage<String> delete(@RequestParam("id") String id, HttpSession session) {
        if (!id.equals("")) {
            likeService.remove(new QueryWrapper<Like>().eq("d_id", id));
            User user = (User) MySecurityConfig.currentUser(session);
            Diary diary = diaryService.getOne(new QueryWrapper<Diary>().eq("id", Integer.valueOf(id)).eq("user_id", user.getId()));

            if (diary != null) {
                diaryImageService.getBaseMapper().delete(new QueryWrapper<DiaryImage>().eq("diary_id", diary.getId()));
                PictureUtil.deleteFile("pic/diary/" + user.getId() + "/" + id);
                diaryService.getBaseMapper().deleteById(diary.getId());
                return new MyMessage<>(0, null, "删除成功");
            }

        }
        return new MyMessage<>(0, null, "删除失败");
    }

    @PostMapping("/update")
    public MyMessage<String> update(@RequestParam("id") String id, HttpSession session, @RequestParam("title") String title, @RequestParam("content") String content) {
        if (!id.equals("") && !title.equals("") && !content.equals("")) {
            User user = (User) MySecurityConfig.currentUser(session);
            Diary diary = diaryService.getOne(new QueryWrapper<Diary>().eq("id", Integer.valueOf(id)).eq("userid", user.getId()));

            if (diary != null) {

                diaryService.getBaseMapper().updateById(diary.setTitle(title).setContent(content));
            }

        }
        return new MyMessage<>(0, null, "删除失败");
    }

    //详情,获得很多
    @GetMapping("/get")
    public MyMessage<List<DiaryItemOv>> getDiaryOV(HttpSession session) {
        User user = (User) MySecurityConfig.currentUser(session);

        return new MyMessage<>(0, diaryService.getBaseMapper().getDiaryOV(user.getId()), "查询成功");
    }


    @GetMapping("/d/{id}")
    public MyMessage<DiaryDetails> getBanner(@PathVariable("id") int id) {

        List<DiaryImage> imageList = diaryImageService.list(new QueryWrapper<DiaryImage>().eq("diary_id", id));
        Diary diary = diaryService.getOne(new QueryWrapper<Diary>().eq("id", id));
        return new MyMessage<>(0, new DiaryDetails().setImages(imageList).setDiary(diary), "查询成功");
    }

    @GetMapping("/collect")
    public MyMessage<List<DiaryItemOv>> getCollect(HttpSession session) {
        User user = (User) MySecurityConfig.currentUser(session);
        return new MyMessage<>(0, diaryService.getBaseMapper().getCollect(user.getId()), "查询成功");
    }


    @GetMapping("/person/{id}")
    public MyMessage<List<DiaryItemOv>> getPerson(@PathVariable("id") int id) {
        return new MyMessage<>(0, diaryService.getBaseMapper().getPerson(id), "查询成功");
    }

}

