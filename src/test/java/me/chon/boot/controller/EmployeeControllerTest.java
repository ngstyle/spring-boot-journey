package me.chon.boot.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import me.chon.boot.bean.Employee;
import me.chon.boot.bean.HttpResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.*;

/**
 * mvn clean package                                maven打包执行单元测试
 * mvn clean package -Dmaven.test.skip=true         maven打包跳过单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getEmps() throws Exception{

        // 请求响应码
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/emps")
//                .param("pageNum", "10"))
//                .andExpect(MockMvcResultMatchers.status().isOk());

        // 模拟请求 拿到返回值
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/emps")
                        .param("pageNum", "10"))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        ObjectMapper mapper = new ObjectMapper();
        JavaType pageInfoJavaType = mapper.getTypeFactory().constructParametricType(PageInfo.class, Employee.class);
        JavaType httpResultJavaType = mapper.getTypeFactory().constructParametricType(HttpResult.class, pageInfoJavaType);
        HttpResult<PageInfo<Employee>> httpResult = mapper.readValue(response.getContentAsString(), httpResultJavaType);

        PageInfo<Employee> pageInfo = httpResult.getData();

        System.out.println("当前页码：" + pageInfo.getPageNum());
        System.out.println("总页码：" + pageInfo.getPages());
        System.out.println("总记录数：" + pageInfo.getTotal());
        System.out.println("在页面需要连续显示的页码：");

        int[] navigatepageNums = pageInfo.getNavigatepageNums();
        for (int num : navigatepageNums) {
            System.out.print(" " + num);
        }

        System.out.println("");

        List<Employee> emps = pageInfo.getList();
        for (Employee emp : emps) {
            System.out.println(emp);
        }

    }
}