package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form  フォーム
	 * @return　ログイン画面(リダイレクト)
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		
		administrator.setName(form.getName());
		administrator.setMailAddress(form.getMailAddress());
		administrator.setPassword(form.getPassword());
	
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
	/**
	 * ログイン画面を表示する.
	 * 
	 * @return　ログイン画面を表示
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	@RequestMapping("/login")
	public String login(LoginForm form,Model model) {
		Administrator administrator = 
				administratorService.login(form.getMailAddress(),form.getPassword());
		if(administrator == null) {
			model.addAttribute("loginError","メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}
		session.setAttribute("administratorName",administrator.getName());
		return "forward:/employee/showList";		
	}
	
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
}