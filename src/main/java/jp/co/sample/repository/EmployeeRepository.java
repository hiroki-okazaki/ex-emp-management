package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		// ここに結果の処理を書く
		Employee employee = new Employee();

		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hireDate"));
		employee.setMailAddress(rs.getString("mailAddress"));
		employee.setZipCode(rs.getString("zipCode"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependentsCount"));

		return employee;
	};

	public List<Employee> findAll() {
		String sql = "select id,name,image,gender,hireDate,mailAddress,zipCode,address,telephone,salary,characteristics,dependentsCount FROM employees ORDER BY hireDate";

		List<Employee> employee = template.query(sql, EMPLOYEE_ROW_MAPPER); // ←ここに実行の処理を書く

		return employee;
	}

	public Employee load(Integer id) {
		String sql = "select id,name,age,dep_id from employees where id = :id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id); // ←ここにプレースホルダにセットする処理を書く

		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER); // ←ここに実行処理を書く

		return employee;
	}

	public Employee update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		if (employee.getId() != null) {
			String insertSql = "INSERT INTO employees(dependentsCount) VALUES(:dependentsCount)";

			template.update(insertSql, param);
		}

		return employee;
	}

}
