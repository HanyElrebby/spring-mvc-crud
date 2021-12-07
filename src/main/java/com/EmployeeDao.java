package com;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDao {
    private JdbcTemplate jdbcTemplate;

    public EmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveOrUpdate(Employee employee) {
        if(employee.getId()>0) {
            String sql = "UPDATE emp99 SET name=?, salary=?, designation=? WHERE id=?";
            jdbcTemplate.update(sql, employee.getName(),
                    employee.getSalary(), employee.getDesignation(), employee.getId());
        } else {
            String sql = "INSERT INTO emp99 (name, salary, designation) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, employee.getName(),
                    employee.getSalary(), employee.getDesignation());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM emp99 WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    public Employee getEmployee(int id) {
        String sql = "SELECT * FROM emp99 WHERE id='"+id+"'";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Employee>() {
            @Override
            public Employee extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setId(rs.getInt(1));
                    employee.setName(rs.getString(2));
                    employee.setSalary(rs.getDouble(3));
                    employee.setDesignation(rs.getString(4));
                    return employee;
                }
                return null;
            }

        });
    }

    public List<Employee> getEmployees(int start, int total) {
        String sql = "SELECT * FROM emp99 limit "+(start-1)+","+total;
        return jdbcTemplate.query(sql, new RowMapper<Employee>(){
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee employee = new Employee();
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setSalary(rs.getDouble(3));
                employee.setDesignation(rs.getString(4));
                return employee;
            }
        });
    }
}
