package me.chon.boot.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.chon.boot.bean.Employee;
import me.chon.boot.bean.HttpResult;
import me.chon.boot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理员工CRUD
 */
@RestController
@SuppressWarnings("unchecked")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 查询员工数据（分页） 引入pageHelper
     * @return
     */
//    @GetMapping("/emps")
//    public String getEmps(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum, Model model) {
//
//        // 在查询之前 使用pageHelper
//        PageHelper.startPage(pageNum, 5);
//
//        // startPage 后面紧跟的这个查询就是分页查询
//        List<Employee> emps = employeeService.getAll();
//
//        // 使用pageInfo 包装后的结果，封装了详细的分页信息
//        PageInfo pageInfo = new PageInfo(emps, 5);
//
//        model.addAttribute("pageInfo", pageInfo);
//
//        return "list";
//    }


    @GetMapping("/emps")
    public HttpResult<PageInfo> getEmpsWithJson(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum) {

        // 在查询之前 使用pageHelper
        PageHelper.startPage(pageNum, 5);

        // startPage 后面紧跟的这个查询就是分页查询
        List<Employee> emps = employeeService.getAll();

        // 使用pageInfo 包装后的结果，封装了详细的分页信息
        PageInfo pageInfo = new PageInfo(emps, 5);

        return HttpResult.success(pageInfo);
    }

    @PostMapping(value = "/emp")
    public HttpResult<List<FieldError>> addEmp(@Valid Employee employee, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return null;
//            return HttpResult.fail(bindingResult.getFieldErrors());
        }

        employeeService.addEmp(employee);
        return HttpResult.success();
    }

    @GetMapping(value = "/emp/{id}")
    public HttpResult<Employee> getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return HttpResult.success(employee);
    }

    @PutMapping(value = "/emp/{empId}")
    public HttpResult<Integer> updateEmp(Employee employee) {
        return HttpResult.success(employeeService.updateEmp(employee));
    }

    @DeleteMapping("/emp/{empIds}")
    public HttpResult<Integer> delEmp (@PathVariable("empIds") String empIds) {

        HttpResult httpResult = HttpResult.success();
        try {
            if (empIds.contains("-")) {
                ArrayList<Integer> list = new ArrayList();
                String[] empIdsArr = empIds.split("-");
                for (String empIdStr : empIdsArr) {
                    list.add(Integer.parseInt(empIdStr));
                }

                int count = employeeService.delEmpByIds(list);
                httpResult = HttpResult.success(count);
            } else {
                int empId = Integer.parseInt(empIds);
                httpResult.setData(employeeService.delEmpById(empId));
            }
        } catch (NumberFormatException e) {
            httpResult = HttpResult.fail(0);
            httpResult.setMessage("参数有误");
        }

        return httpResult;
    }

    @PostMapping(value = "/checkuser")
    public HttpResult<Boolean> checkUser(@RequestParam(value = "empName") String empName) {
        boolean isUserNameExist = employeeService.isUserNameExist(empName);

        String regex = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        HttpResult httpResult;
        if (!empName.matches(regex)) {
            httpResult = HttpResult.fail(false);
            httpResult.setMessage("用户名可以是2-5位中文或者6-16位英文和数字的组合<后端>");
            return httpResult;
        }

        if (isUserNameExist) {
            httpResult = HttpResult.fail(false);
            httpResult.setMessage("用户名已存在");
        } else {
            httpResult = HttpResult.success(true);
            httpResult.setMessage("");
        }

        return httpResult;
    }
}
