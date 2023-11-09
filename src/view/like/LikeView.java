package view.like;

import constant.FileName;
import model.like.Like;
import model.product.Product;
import model.user.User;
import service.Service;
import view.user.HomeView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static config.Color.*;

public class LikeView {
    private final Service<Like, Long> likeService;

    public LikeView() {
        this.likeService = new Service<>(FileName.LIKE);
    }

    public List<Like> getProductLikeList(Product product) {
        List<Like> productLikeList;
        if (likeService.findAll().isEmpty() || likeService.findAll() == null) {
            productLikeList = new ArrayList<>();
        } else {
            productLikeList = likeService.findAll().stream().filter(l -> l.getProductId().equals(product.getId())).filter(Like::isLikeStatus).collect(Collectors.toList());
        }
        return productLikeList;
    }

    public void countLikeProduct(Product product, User user) {
        List<Like> productLikeList = getProductLikeList(product);
//        if(user==null){
//            return;
//        }
        if (user!=null&&likeService.findAll().isEmpty()) {
            System.out.println(WHITE_BRIGHT + "Sản phẩm " + product.getProductName() + " chưa có likes nào!" + RESET);
            System.out.println(PURPLE + "Bạn chưa like sản phẩm!" + RESET);
            return;
        }
        System.out.println(WHITE_BRIGHT + "Sản phẩm " + product.getProductName() + " có " + productLikeList.size() + " likes " + RESET);
       if(HomeView.userLogin!=null){
           if (findLikeById(productLikeList, user) != null) {
               System.out.println(PURPLE + "Bạn đã like sản phẩm!" + RESET);
           } else {
               System.out.println(PURPLE + "Bạn chưa like sản phẩm!" + RESET);
           }
       }
    }

    public void handleLikePro(Product product, User user) {
        if(HomeView.userLogin==null){
            System.out.println(RED + "Vui lòng đăng nhập để thực hiện chức năng này!!!" + RESET);
            System.out.println();
            new HomeView().login();
            return;
        }
        List<Like> productLikeList = getProductLikeList(product);
        Like like = findLikeById(productLikeList, user);
        if (productLikeList.isEmpty() || like == null) {
            Like newLike = new Like();
            newLike.setId(likeService.getNewId());
            newLike.setProductId(product.getId());
            newLike.setUserId(user.getId());
            newLike.setLikeStatus(true);
            likeService.save(newLike);
            System.out.println(PURPLE_BRIGHT + "Bạn đã like sản phẩm!" + RESET);
            return;
        }
        like.setLikeStatus(!like.isLikeStatus());
        if (like.isLikeStatus()) {
            System.out.println(PURPLE_BRIGHT + "Bạn đã like sản phẩm!" + RESET);
        } else {
            System.out.println(PURPLE_BRIGHT + "Bạn đã unlike sản phẩm!" + RESET);
        }
        likeService.save(like);
    }

    public Like findLikeById(List<Like> productLikeList, User user) {
        if(user!=null){
            for (Like like : productLikeList) {
                if (like.getUserId().equals(user.getId()) && like.isLikeStatus()) {
                    return like;
                }
            }
        }
        return null;
    }
}
