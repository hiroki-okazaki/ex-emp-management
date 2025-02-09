package jp.co.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	
	@Autowired
	private EmployeeService employeeService;
	
	@ModelAttribute
	public UpdateEmployeeForm setUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	@RequestMapping("/showList")
	public String syowList(Model model) {
		model.addAttribute("employeeList",employeeService.showList());
		return "employee/list.html";
	}
	
	@RequestMapping("/showDetail")
	public String showDetail(String id,Model model) {
		int integerID = Integer.parseInt(id);
		model.addAttribute("showDetail",employeeService.showDetail(integerID));
		return "employee/detail";
	}
	
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		Employee employee = new Employee();
		int integerID = Integer.parseInt(form.getId());
		int integerDependentsCount = Integer.parseInt(form.getDependentsCount());
		
		employee.setId(integerID);
		employee.setDependentsCount(integerDependentsCount);
		
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
}