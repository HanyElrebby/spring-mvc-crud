package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class EmpController {
    @Autowired
    private EmployeeDao employeeDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model) {
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/newEmployee", method = RequestMethod.GET)
    public ModelAndView newContact(ModelAndView model) {
        Employee employee = new Employee();
        model.addObject("employee", employee);
        model.setViewName("empForm");
        return model;
    }

    @RequestMapping(value="/viewEmployees")
    public ModelAndView getEmployees(HttpServletRequest request, ModelAndView model) throws IOException {
        int total = 6;
        int pageId = Integer.parseInt(request.getParameter("pageId"));
        List<Employee> employees = employeeDao.getEmployees(pageId, total);
        model.addObject("employees", employees);
        model.setViewName("viewEmp");
        return model;
    }

    @RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
    public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
        employeeDao.saveOrUpdate(employee);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
    public ModelAndView editEmployee(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        ModelAndView model = new ModelAndView("empForm");
        Employee employee = employeeDao.getEmployee(id);
        model.addObject("employee", employee);
        return model;
    }

    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET)
    public ModelAndView deleteEmployee(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        employeeDao.delete(id);
        return new ModelAndView("redirect:/");
    }
}
