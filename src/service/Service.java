package service;

import model.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Service<T extends Entity, ID extends Number> {
    private final String fileName;

    public Service(String fileName) {
        this.fileName = fileName;
        File file = new File(fileName);
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Tạo file sau đây bị lỗi: " + fileName);
        }
    }

    public List<T> findAll() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<T> list = (List<T>) ois.readObject();
            ois.close();
            return list;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return new ArrayList<>();
        }
    }

    public Long getNewId() {
        List<T> list = findAll();
        Long maxId = 0L;
        for (T t : list) {
            if ((Long) t.getId() > maxId) {
                maxId = (Long) t.getId();
            }
        }
        return maxId + 1;
    }

    public boolean save(T t) {
        List<T> list = findAll();
//        T l =findById((ID) t.getId());
//        if (l == null) {
//            list.add(t);
//        } else {
//            int index = list.indexOf(t);
//            list.set(index, t);
//        }

        for (T e : list) {
            if (t.getId().equals(e.getId())) {
                int indexOf = list.indexOf(e);
                list.set(indexOf, t);
                return updateData(list, this.fileName);
            }
        }
        list.add(t);
        return updateData(list, this.fileName);
    }

    public T findById(ID id) {
        List<T> list = findAll();
        for (T t : list) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public boolean deleteById(ID id) {
        List<T> list = findAll();
//        T t = findById(id);
//        if (t == null) {
//            return false;
//        } else {
//            list.remove(t);
//            return updateData(list, this.fileName);
//        }
        for (T t : list) {
            if (t.getId().equals(id)) {
                list.remove(t);
                return updateData(list, this.fileName);
            }
        }
        return false;
    }

    public boolean saveOne(T t) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(t);
            outputStream.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean updateData(List<T> list, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public T getOne() {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            T t = (T) inputStream.readObject();
            inputStream.close();
            return t;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            return null;
        }

    }

}