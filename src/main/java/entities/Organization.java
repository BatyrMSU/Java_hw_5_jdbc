package entities;

import org.jetbrains.annotations.NotNull;

public class Organization {

    private int id;
    private @NotNull String name;
    private @NotNull int inn;
    private @NotNull int account;

    public Organization(int id, @NotNull String name, int inn, int account) {
        this.id = id;
        this.name = name;
        this.inn = inn;
        this.account = account;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public @NotNull String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inn=" + inn +
                ", account=" + account +
                '}';
    }

}
