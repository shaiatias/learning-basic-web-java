package repositories;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by shai on 11/7/2017.
 */
public class CrudRepository<T> {

    private final String filename;
    private File file;

    protected CrudRepository(String filename) {
        this.filename = filename;
        file = new File(filename);
        verifyFile();
    }

    private void verifyFile() {

        if (!file.exists()) {
            throw new RuntimeException("file: " + filename + " doesn't exist");
        }

        if (file.isDirectory()) {
            throw new RuntimeException("file: " + filename + " is a folder");
        }

        if (!file.canRead() || !file.canWrite()) {
            throw new RuntimeException("file: " + filename + " cannot be open for read\\write");
        }
    }

    protected Optional<T> getOne(Predicate<T> filter) {
        return getAll().stream().filter(filter).findFirst();
    }

    public List<T> getAll() {

        verifyFile();

        List<T> objects = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            try {
                objects = (List<T>) ois.readObject();
            } catch (IOException ex) {
                throw ex;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                ois.close();
            }
        }

        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        return objects;
    }

    protected void save(T entity) {

        verifyFile();

        List<T> all;

        try {
            all = getAll();
        } catch (RuntimeException e) {
            all = new ArrayList<>();
        }

        all.add(entity);

        try {

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            try {
                oos.writeObject(all);
            } finally {
                oos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void delete(Predicate<T> filter) {

        verifyFile();

        List<T> all = getAll()
                .stream()
                .filter(t -> !filter.test(t))
                .collect(Collectors.toList());

        try {

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            try {
                oos.writeObject(all);
            } finally {
                oos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
