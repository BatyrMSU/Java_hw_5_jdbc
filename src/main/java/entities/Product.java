package entities;

import org.jetbrains.annotations.NotNull;

public final class Product {

    private int id;
    private @NotNull String name;
    private @NotNull int code;

    public Product(int id, @NotNull String name, @NotNull int code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull int getCode() {
        return code;
    }

    public void setCode(@NotNull int code) {
        this.code = code;
    }

    @Override
    public @NotNull String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }

}
