package service.likeservice;

import constant.FileName;
import model.like.Like;
import service.IService;
import service.Service;

import java.util.List;

public class LikeService implements IService <Like,Long> {
    private Service<Like,Long>likeService;

    public LikeService() {
        this.likeService=new Service<>(FileName.LIKE);
    }

    @Override
    public List<Like> findAll() {
        return likeService.findAll();
    }

    @Override
    public Long getNewId() {
        return likeService.getNewId();
    }

    @Override
    public boolean save(Like like) {
        return likeService.save(like);
    }

    @Override
    public Like findById(Long id) {
        return likeService.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return likeService.deleteById(id);
    }
}
