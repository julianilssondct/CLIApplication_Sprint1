package com.company;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBC {
    private final String url = "jdbc:postgresql://localhost:5432/";
    private final String user = "postgres";
    private final String pass = "example";

    public Connection connect () throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
    public List<User> getUsers(String sqlQuery) {
        List<User> userList = new ArrayList<User>();
        try (Connection conn = connect();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sqlQuery)){
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public int addUser(User user){
        try (Connection conn = connect()){
            PreparedStatement statement = conn.prepareStatement("insert into users (email, first_name, last_name) values (?,?,?)");
            statement.setString(1, user.getEmail());
            statement.setString(2,user.getFirstName());
            statement.setString(3, user.getLastName());
            int affectedRows = statement.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int deleteUser(String id) {
        try (Connection conn = connect()){
            /*PreparedStatement statement = conn.prepareStatement("delete from users where id = ?");
            statement.setString(1, id);*/
            //OVAN: Allt fungerar förutom att jag inte kan använda mig av id: org.postgresql.util.PSQLException: ERROR: operator does not exist: uuid = character varying

            Statement statement = conn.createStatement();
            String sqlQuery = "delete from users where id = '"+id+"'";
            int affectedRows = statement.executeUpdate(sqlQuery);
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int updateUser(String id, User user) {
        try (Connection conn = connect()){
            PreparedStatement statement = conn.prepareStatement("update users set email = ?, first_name = ?, last_name = ?" +
                    "where id = '" + id + "'");
            statement.setString(1, user.getEmail());
            statement.setString(2,user.getFirstName());
            statement.setString(3, user.getLastName());
            int affectedRows = statement.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
