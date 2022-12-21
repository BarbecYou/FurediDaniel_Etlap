package hu.home.etlap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
    Connection conn;
    public static String DB_PORT = "3306";
    public static String DB_DRIVER = "mysql";
    public static String DB_HOST = "localhost";
    public static String DB_DBNAME = "etlapdb";
    public static String DB_USER = "root";
    public static String DB_PASS = "";

    public Db() throws SQLException {
        String url = String.format("jdbc:%s://%s:%s/%s", DB_DRIVER, DB_HOST, DB_PORT, DB_DBNAME);
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }

    public boolean createData(Food food) throws SQLException {
        String sql = "INSERT INTO etlapdb(nev, leiras, ar, kategoria) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, food.getName());
        stmt.setString(2, food.getDescription());
        stmt.setInt(3, food.getPrice());
        stmt.setString(4, food.getCategory());

        return stmt.executeUpdate() > 0;
    }
    public List<Food> readData() throws SQLException {
        List<Food> foodList = new ArrayList<>();
        String sql = "SELECT * FROM etlapdb";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()){
            int id = result.getInt("id");
            String name = result.getString("nev");
            String description = result.getString("leiras");
            int price = result.getInt("ar");
            String category = result.getString("kategoria");
            Food food = new Food(id, name, description, price, category);
            foodList.add(food);
        }
        return foodList;
    }
    public void updateData(){

    }
    public void deleteData(){

    }
}
