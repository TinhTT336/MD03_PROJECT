package service.commentservice;

import constant.FileName;
import model.comment.Comment;
import service.IService;
import service.Service;

import java.util.List;

public class CommentService implements IService <Comment,Long> {

    public CommentService() {
        Service<Comment, Long> commentService = new Service<>(FileName.COMMENT);
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Long getNewId() {
        return null;
    }

    @Override
    public boolean save(Comment comment) {
        return false;
    }

    @Override
    public Comment findById(Long aLong) {
        return null;
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
