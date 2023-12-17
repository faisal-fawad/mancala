package mancala;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A class which saves and loads objects
 */
public class Saver {
    /**
     * Saves an object to the assets folder.
     * 
     * @param toSave The object to save.
     * @param filename The file name for the object.
     * @throws IOException If the object was unable to saved.
     */
    public static void saveObject(final Serializable toSave, final String filename) throws IOException {
        try (FileOutputStream file = new FileOutputStream("assets/" + filename);
        ObjectOutputStream stream = new ObjectOutputStream(file)) {
            stream.writeObject(toSave);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Loads an object from the assets folder.
     * 
     * @param filename The file name for the object.
     * @return The loaded object.
     * @throws IOException If the object was unable to be loaded.
     */
    public static Serializable loadObject(final String filename) throws IOException {
        Serializable toLoad;
        try (FileInputStream file = new FileInputStream("assets/" + filename);
        ObjectInputStream stream = new ObjectInputStream(file)) {
            toLoad = (Serializable) stream.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        return toLoad;
    }
}