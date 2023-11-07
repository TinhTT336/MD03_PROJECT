import model.user.User;
import service.userService.UserService;
import view.user.HomeView;

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