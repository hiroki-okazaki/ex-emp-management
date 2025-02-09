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

/**
 * administratorsテーブルを操作するリポジトリ.
 * @author hirokiokazaki
 */
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
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));

		return employee;
	};
	
	

	/**
	 * 全従業員の情報を取得
	 * 
	 * @return　従業員情報
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees ORDER BY hire_date";

		List<Employee> employee = template.query(sql, EMPLOYEE_ROW_MAPPER); 

		return employee;
	}
	

	/**
	 * 指定したidの従業員情報を取得
	 * @param id　主キー
	 * @return　従業員情報
	 */
	public Employee load(Integer id) {
		String sql = "select id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count from employees where id = :id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return employee;
	}

	/**
	 * 指定したidの従業員情報の内、扶養人数を変更する
	 * @param employee　従業員情報のドメイン
	 * @return
	 */
	public Employee update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		if (employee.getId() != null) {
			String insertSql = "UPDATE employees SET dependents_count = :dependentsCount WHERE id = :id";

			template.update(insertSql, param);
		}

		return employee;
	}

}
