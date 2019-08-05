package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;


/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author hirokiokazaki
 *
 */
@Repository
public class AdministratorRepository {

	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mailAddress"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	/**
	 * 管理者情報を登録する.
	 * IDがnullのrowに名前、メールアドレス、パスワードを挿入(idは自動生成)
	 * 
	 * @param administrator　管理者情報のドメイン
	 * @return　管理者情報
	 */
	public Administrator insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);

		if (administrator.getId() == null) {
			String insertSql = "INSERT INTO administrators(name, mail_address, password) VALUES(:name, :mailAddress, :password)";

			template.update(insertSql, param);

		}

		return administrator;
	}

	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 管理者情報(存在しない場合はnullが返る)
	 * 
	 */
//	public Administrator findByMailAddressAndPassword(String mailAddress,String password) {
//		
//		String sql = "SELECT id,name,mail_address,password FROM administrators WHERE mail_address = :mailAddress AND password = :password";
//		
//		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password", password);
//		
//		Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
//		
//		
//		return administrator;
//	}
	public Administrator findByMailAddressAndPassword(String mailAddress,String password) {
		String sql = "select id,name,mail_address,password from administrators where mail_address=:mailAddress AND password=:password" ;
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",password);
		List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
		if (administratorList.size() == 0) {
			return null;
		}
		return administratorList.get(0);
	}
	
}
