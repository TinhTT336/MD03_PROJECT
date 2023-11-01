package service.userService;

import config.Config;
import constant.FileName;
import model.user.User;
import service.IService;
import service.Service;
import view.user.HomeView;

import java.util.List;

import static config.Color.*;

public class UserService implements IService<User, Long> {
    private Service<User, Long> userService;
    private Service<User, Long> loginService;


    public UserService() {
        this.userService = new Service<>(FileName.USER);
        this.loginService = new Service<>(FileName.LOGIN);
    }

    @Override
    public List<User> findAll() {
        return userService.findAll();

    }

    @Override
    public Long getNewId() {
        return userService.getNewId();
    }

    @Override
    public boolean save(User user) {
        return userService.save(user);
    }

    @Override
    public User findById(Long id) {
        return userService.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return userService.deleteById(id);
    }

    public boolean checkEmailExist(String email) {
        List<User> users = userService.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUsernameExist(String username) {
        List<User> users = userService.findAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User checkLogin(String username, String password) {
        List<User> users = userService.findAll();
        if(userService.findAll().isEmpty()||userService.findAll()==null){
            System.out.println(RED+"Tài khoản chưa tồn tại, vui lòng đăng ký"+RESET);
            new HomeView().register();
            return null;
        }else{
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    loginService.saveOne(user);
                    return user;
                }
            }
        }
        return null;
    }

    public User getCurrentUser() {
//        return userService.getOne();
        return new Config<User>().readFile(FileName.LOGIN);
    }

}
