package com.example.demo.showController;

import com.example.demo.dao.ClientAppKeyDao;
import com.example.demo.entity.ClientAppKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/RegistIn")
public class RegisterController {

    @Autowired
    ClientAppKeyDao clientAppKeyDao;
    @Autowired
    MongoTemplate mongoTemplate;
    @RequestMapping(value = "getRegister",method = RequestMethod.POST)
    public String register(HttpServletRequest request){
        String AppKey = request.getParameter("registerUsername");
        String Password = request.getParameter("registerPassword");
        String EmailAddress=request.getParameter("registerEmail");
        //System.out.println(AppKey);
        //System.out.println(Password);
        ClientAppKey clientAppKey = new ClientAppKey();
        clientAppKey.setPassword(Password);
        clientAppKey.setAppKey(AppKey);
        if(clientAppKeyDao.findByAppKey(AppKey)==null){
            mongoTemplate.save(clientAppKey,"ClientAppKey");
            return "redirect:/login";
        }
        else
        {
            request.setAttribute("loginMsg","用户已存在");
            return "register";
        }
    }
}
