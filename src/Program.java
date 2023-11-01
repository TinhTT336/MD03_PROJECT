import constant.Role;
import model.user.User;
import service.userService.UserService;
import view.user.AdminView;
import view.user.HomeView;
import view.user.UserView;

public class Program {
    public static void main(String[] args) {
        User currentUser = new UserService().getCurrentUser();
        if (currentUser != null) {
            new HomeView().checkRole(currentUser);
        }else{
            new HomeView().showMenuHome();
        }
    }
}