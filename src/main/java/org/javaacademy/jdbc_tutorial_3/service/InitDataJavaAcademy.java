package org.javaacademy.jdbc_tutorial_3.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.javaacademy.jdbc_tutorial_3.entity.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("java-academy")
public class InitDataJavaAcademy {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    @PostConstruct
    public void init() {


        String orderNo = "7F";
        String sql = "insert into orders (order_no, is_paid) values ('" + orderNo + "', false)";

        String sqlCreateOrderDetails = """
                insert into order_details (name, qty, order_id) values (
                	'бигмак',
                	1,
                	(select id from orders where order_no = '%s' order by id desc limit 1)
                	)
                	""".formatted(orderNo);

        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            Object savepoint = transactionStatus.createSavepoint();
            int updated = jdbcTemplate.update(sql);
            int updatedOrderDetails = jdbcTemplate.update(sqlCreateOrderDetails);
            System.out.println("rows updated " + updated);
            System.out.println("rows updated " + updatedOrderDetails);
            if (updated != updatedOrderDetails) {
                transactionStatus.rollbackToSavepoint(savepoint);
            }
        });
    }


}
