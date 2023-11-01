package config;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Config <T>{
    public static Scanner scanner() {
        return new Scanner(System.in);
    }



    public void writeFile(String FILE_NAME, T t){
        File file=new File(FILE_NAME);
        try {
            file.createNewFile();
            FileOutputStream fos=new FileOutputStream(file);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.close();
        } catch (IOException e) {
            System.out.println("Lỗi ghi file!!!");
        }
    }

    public T readFile(String FILE_NAME){
        File file=new File(FILE_NAME);
        T t = null;
        try{
            file.createNewFile();
            FileInputStream fis=new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            t= (T) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi đọc file!!!");;
        }
        return t;
    }

}
