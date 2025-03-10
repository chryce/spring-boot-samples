package com.example.web;

import com.example.bean.Person;
import com.example.properties.ServerHostProperties;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

/*
 * 在controller上加注解@Controller 和@RestController都可以在前端调通接口，但是二者的区别在于，
当用前者的时候在方法上必须添加注解@ResponseBody，如果不添加@ResponseBody，就会报上面错误，
因为当使用@Controller 注解时，spring默认方法返回的是view对象（页面）。
而加上@ResponseBody，则方法返回的就是具体对象了。
@RestController的作用就相当于@Controller+@ResponseBody的结合体
 *
 */
//template might not exist or might not be accessible by any of the configured Template Resolvers
@Controller
@RequestMapping(value = "/v1")
public class StudentV1Controller {

  @Autowired
  private SpringTemplateEngine templateEngine;



  @Autowired
  private ServerHostProperties serverHostProperties;

  @ResponseBody
  @RequestMapping(value = "/hello")
  public String Hello() {
    return "Hello World,世界你好! ! !";
  }

  @RequestMapping("/sayHi")
  public String helloWorld(Model model) {
    model.addAttribute("mav", "HelloWorldController ,Spring Boot!");
    //视图(view)的位置和名称，视图位于example文件夹下，视图文件为hello.html。
    return "example/hello";
  }

  @RequestMapping(value = "/index")
  public ModelAndView index() {
    ModelAndView model = new ModelAndView();
    model.setViewName("/student/index");
    return model;
  }

  @RequestMapping(value = "/welcome")
  public String welcome(Model model) {
    model.addAttribute("name", "小祖宗");
    return "/default/welcome";
  }

  @RequestMapping(value = "/test")
  public String test(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    WebContext ctx = new WebContext(request, response, request.getServletContext());
    ctx.setVariable("book", "福尔摩斯探案集");
    session.setAttribute("city", "上海松江区");
    return "/default/testThymeleafObjects";
  }

  @RequestMapping("/showServerHost")
  public String serverHost(Model model) {
    List<ServerHostProperties.InetAddress> inetAddresses = new ArrayList<>();
    inetAddresses.add(serverHostProperties.getInetAddressA());
    inetAddresses.add(serverHostProperties.getInetAddressB());
    inetAddresses.add(serverHostProperties.getInetAddressC());
    model.addAttribute("inetAddresses", inetAddresses);
    return "/default/showServerHost";
  }

  @RequestMapping("person")
  public String index(Model model) {
    List<Person> people = new ArrayList<Person>();
    Person p1 = new Person("bb", 2);
    Person p2 = new Person("cc", 3);
    Person p3 = new Person("dd", 4);
    people.add(p1);
    people.add(p2);
    people.add(p3);
    model.addAttribute("people", people);

    Person single = new Person("aa", 1);
    model.addAttribute("singlePerson", single);
    return "/default/person";
  }

}
