package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;


@Repository
public class AdministratorRepository {

	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		// ここに結果の処理を書く
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mailAddress"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	public Administrator insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);

		if (administrator.getId() == null) {
			String insertSql = "INSERT INTO administrators(name, mailAddress, password) VALUES(:name, :mailAddress, :password)";

			template.update(insertSql, param);

		}

		return administrator;
	}

	public Administrator findByMailAddressAndPassword(String mailAddress,String password) {
		
		String sql = "SELECT id,name,mailAddress,password FROM administrators WHERE mailAddress = :mailAddress AND password = :password";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password", password);
		
		Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
		
		return administrator;
	}
	
	
}
