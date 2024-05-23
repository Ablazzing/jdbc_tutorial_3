package org.javaacademy.jdbc_tutorial_3.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.javaacademy.jdbc_tutorial_3.entity.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("business")
public class InitDataBusiness {
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {

//        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
//        result.forEach(System.out::println);
        printProductsByName("хлеб");
    }

    private void printProductsByName(String productName) {
        String sql = "select * from shop.product where name = ?";
        List<Product> productsByName = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setString(1, productName),
                this::productRowMapper);
        System.out.println(productsByName);
    }

    private void printAllProducts() {
        String sql = "SELECT * FROM shop.product";
        List<Product> products = jdbcTemplate.query(
                sql,
                this::productRowMapper);
        System.out.println(products);
    }

    private Product productRowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setCompanyId(resultSet.getInt("company_id"));
        return product;
    }
}
