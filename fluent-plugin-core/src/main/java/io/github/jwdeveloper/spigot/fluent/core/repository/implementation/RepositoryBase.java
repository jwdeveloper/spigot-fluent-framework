package io.github.jwdeveloper.spigot.fluent.core.repository.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.java.ObjectUtility;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.files.FileUtility;
import io.github.jwdeveloper.spigot.fluent.core.files.json.JsonUtility;
import io.github.jwdeveloper.spigot.fluent.core.repository.api.CustomFile;
import io.github.jwdeveloper.spigot.fluent.core.repository.api.DataModel;
import io.github.jwdeveloper.spigot.fluent.core.repository.api.Repository;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Stream;

public class RepositoryBase<T extends DataModel> implements Repository<T, UUID>, CustomFile {
    private ArrayList<T> content;
    private String fileName;
    private final String path;
    private final Class<T> entityClass;

    public RepositoryBase(String path, Class<T> entityClass) {
        this.content = new ArrayList<>();
        this.path = path;
        this.fileName = entityClass.getSimpleName();
        this.entityClass = entityClass;
    }

    public RepositoryBase(String path, Class<T> entityClass, String filename) {
        this(path, entityClass);
        this.fileName = filename;
    }

    public Stream<T> query() {
        return content.stream();
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public Optional<T> findById(UUID id) {
        return content
                .stream()
                .filter(p -> p.getUuid().equals(id))
                .findFirst();
    }

    public Optional<T> getOne(UUID id) {
        return content
                .stream()
                .filter(p -> p.getUuid().equals(id))
                .findFirst();
    }

    public Optional<T> getOneByName(String name) {
        return content
                .stream()
                .filter(p -> ChatColor.stripColor(p.getName())
                        .equalsIgnoreCase(name))
                .findFirst();
    }

    public ArrayList<T> findAll() {
        return content;
    }

    @Override
    public boolean insert(T data) {
        return insertOne(data);
    }


    public boolean insertOne(T data) {
        if (data == null) {
            return false;
        }
        if (content.contains(data)) {
            return false;
        }
        if (data.getUuid() == null)
            data.setUuid(UUID.randomUUID());

        content.add(data);

        return true;

    }

    @Override
    public boolean update(UUID id, T data) {
        return updateOne(id, data);
    }

    public boolean updateOne(UUID id, T data) {
        var optional = getOne(id);
        if (optional.isEmpty())
            return false;

        try {
            ObjectUtility.copyToObjectDeep(data, optional.get());
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to update element in " + getClass().getSimpleName() + " repository", e);
            return false;
        }
    }

    public boolean insertMany(List<T> data) {
        data.forEach(this::insertOne);
        return true;
    }


    public boolean updateMany(HashMap<UUID, T> data) {
        data.forEach(this::updateOne);
        return true;
    }

    @Override
    public boolean deleteOne(T data) {
        if (content.contains(data)) {
            content.remove(data);
            return true;
        }
        return false;
    }

    public boolean deleteOneById(UUID uuid) {
        var data = getOne(uuid);
        if (data.isEmpty())
            return false;

        content.remove(data.get());
        return true;
    }

    @Override
    public boolean deleteMany(List<T> data) {
        data.forEach(a -> this.deleteOneById(a.getUuid()));
        return true;
    }

    public void deleteAll() {
        content.clear();
    }

    public int getSize() {
        return content.size();
    }

    public boolean contains(T data) {
        return content.contains(data);
    }

    @Override
    public boolean load() {
        try {
            content = JsonUtility.loadList(path, fileName, entityClass);
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Repository load error " + fileName + " " + entityClass.getName(), e);
            return false;
        }
    }

    @Override
    public boolean save() {
        try {
            JsonUtility.save(content, path, fileName);
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Repository save error " + fileName + " " + entityClass.getName(), e);
            return false;
        }
    }

    public static String getDefaultPath(JavaPlugin plugin) {
        var path = FileUtility.pluginPath(plugin) + FileUtility.separator() + "data";
        FileUtility.ensurePath(path);
        return path;
    }
}
