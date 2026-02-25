package DAO;

import util.DatabaseUtil;
import util.Products;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {
    static String tableName = "products";
    public static int getProductCount() {
        String q = "SELECT COUNT(*) as count FROM " + tableName;
        try (Connection conn = DatabaseUtil.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(q)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Products> getAll() {
        List<Products> products = new ArrayList<>();
        String q = "SELECT productid, name, imagefile, price FROM " + tableName + " ORDER BY name";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(q)) {
            while (rs.next()) {
                products.add(helper(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public static List<Products> searchProduct(String searchTerm) {
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            List<Products> products = new ArrayList<>();
            String q = "SELECT productid, name, imagefile, price FROM " + tableName + " WHERE name LIKE ? ORDER BY name";
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement statement = conn.prepareStatement(q)) {
                statement.setString(1, "%" + searchTerm + "%");
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        products.add(helper(rs));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return products;
        } else {

            return getAll();
        }
    }

    public static Products getProductById(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return null;
        }
        String q = "SELECT productid, name, imagefile, price FROM " + tableName + " WHERE productid = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(q)) {
            statement.setString(1, productId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return helper(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Products helper(ResultSet rs) throws SQLException {
        Products product = new Products();
        product.setProductId(rs.getString("productid"));
        product.setName(rs.getString("name"));
        product.setImageFile(rs.getString("imagefile"));
        product.setPrice(rs.getDouble("price"));
        return product;
    }
    public static boolean productInStock(String productId) {
        return getProductById(productId) != null;
    }
    public static void setTableName(String tableName) {
        ProductsDAO.tableName = tableName;
    }
}