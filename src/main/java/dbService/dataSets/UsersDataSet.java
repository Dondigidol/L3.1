package dbService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UsersDataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="login", unique = true, updatable = false)
    private String login;

    @Column(name="password")
    private String pass;

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(){

    }

    @SuppressWarnings("UnusedDeclaration")
    public UsersDataSet(long id, String name){
        this.setId(id);
        this.setName(name);
    }

    public UsersDataSet(String name, String pass){
        this.setId(-1);
        this.setName(name);
        this.setPassword(pass);
    }

    public void setId(long id){
        this.id = id;
    }

    public void setName(String name){
        this.login=name;
    }

    public void setPassword(String pass) {
        this.pass = pass;
    }

    public long getId(){
        return this.id;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getName(){
        return this.login;
    }

    @Override
    public String toString(){
        return "UserDataSet{" +
                "id=" + id +
                ", name='" + login + "'" +
                "}";
    }



}
