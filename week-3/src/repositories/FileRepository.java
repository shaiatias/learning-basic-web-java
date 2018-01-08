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
public abstract class FileRepository<T extends Entity<ID>, ID> implements CrudRepository<T, ID> {

    private final String filename;
    private File file;

    protected FileRepository(String entityName) {
        this.filename = "C:\\Users\\Benjamin\\IdeaProjects\\hw3\\" + entityName + ".dat";
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

    public Optional<T> getOne(Predicate<T> filter) {
        return getAll().stream().filter(filter).findFirst();
    }

    public List<T> getAll() {

        verifyFile();

        List<T> objects;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            try {
                objects = (List<T>) ois.readObject();
            } catch (IOException ex) {
                throw ex;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                ois.close();
            }

        } catch (EOFException e) {

            // empty file
            objects = new ArrayList<>();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return objects;
    }

    public void save(T entity) {

        List<T> singleItemList = new ArrayList<>();
        singleItemList.add(entity);

        save(singleItemList);
    }

    public void save(List<T> entities) {

        verifyFile();

        List<T> all;

        try {
            all = getAll();
        } catch (RuntimeException e) {
            all = new ArrayList<>();
        }

        all.addAll(entities);

        try {

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            try {
                oos.writeObject(entities);
            } finally {
                oos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Predicate<T> filter) {

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

    @Override
    public FileRepository<T, ID> truncateData() {

        try {

            if (!file.delete()) {
                System.out.println("ERR: failed to delete file, " + filename);
                return this;
            }

            if (!file.createNewFile()) {
                System.out.println("ERR: failed to create file, " + filename);
                return this;
            }

        } catch (Exception e) {
            System.err.println("ERR: exception during truncate");
            e.printStackTrace();
        }

        return this;
    }
}
