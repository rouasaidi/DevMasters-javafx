package services;

import entites.user;

import java.sql.SQLException;
import java.util.List;

public interface userservice<T>{

    public void ajouteruser(T t) throws SQLException;
    public  void modifieruser (T t ) throws  SQLException ;
    public void supprimer(T t ) throws  SQLException ;
    public  List<T> afficheruser ()throws  SQLException ;
    public boolean isValidLogin(String email, String password);
   // public user recupererInfosUtilisateur(String email);
   public void updatefront(user user);
    public void deletefront(user users) throws SQLException;
    public boolean existeEmail(String email) throws SQLException;

}
