package view.commentview;

import config.Validation;
import constant.FileName;
import model.comment.Comment;
import model.product.Product;
import model.user.User;
import service.Service;
import view.user.HomeView;

import java.util.List;
import java.util.stream.Collectors;

import static config.Color.*;

public class CommentView {
    private Service<Comment, Long> commentService;
    private Service<User, Long> userService;

    public CommentView() {
        this.commentService = new Service<>(FileName.COMMENT);
        this.userService = new Service<>(FileName.USER);
    }

    public void showComment(Product product) {
        List<Comment>productCommentList=  commentService.findAll().stream().filter(c->c.getProductId().equals(product.getId())).collect(Collectors.toList());

        if (productCommentList.isEmpty()) {
            System.out.println(WHITE_BRIGHT+"Sản phẩm "+product.getProductName()+" chưa có bình luận nào!"+RESET);
            return;
        }
        System.out.println(WHITE_BRIGHT+"Sản phẩm "+product.getProductName()+"  có "+productCommentList.size()+" bình luận!"+RESET);
        for (Comment comment : productCommentList) {
            if (product.getId().equals(comment.getProductId())) {
                System.out.println(PURPLE +(userService.findById(comment.getUserId()).getId().equals(HomeView.userLogin.getId())?("Bạn"):userService.findById(comment.getUserId()).getFullName())  + " đã bình luận: " + comment.getComment() + RESET);
            }
        }

    }
    public void addNewComment(Product product){
        System.out.println("Thêm bình luận cho sản phẩm: ");
        Comment newComment=new Comment();
        newComment.setId(commentService.getNewId());
        newComment.setUserId(HomeView.userLogin.getId());
        newComment.setProductId(product.getId());
        newComment.setComment(Validation.validateString());

        commentService.save(newComment);
        System.out.println(PURPLE_BRIGHT+"Bạn đã bình luận cho sản phẩm!"+RESET);
    }
}
