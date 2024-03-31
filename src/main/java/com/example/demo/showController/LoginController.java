package com.example.demo.showController;

import com.example.demo.dao.ClientAppKeyDao;
import com.example.demo.entity.ClientAppKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    ClientAppKeyDao clientAppKeyDao;

    @RequestMapping("/login")
    public String login(ClientAppKey clientAppKey, HttpServletRequest request,HttpSession session){
        if(clientAppKeyDao.findByAppKey(clientAppKey.getAppKey())==null){
            request.setAttribute("loginMsg","无效appKey");
            return "login";
        }
        String password = clientAppKeyDao.findByAppKey(clientAppKey.getAppKey()).getPassword();
        if(clientAppKey.getPassword().equals(password)){
          session.setAttribute("appKey",clientAppKey.getAppKey());
            return "redirect:/index";
        } else{
            request.setAttribute("loginMsg","密码错误");
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("appKey",null);
        return "redirect:/loginPage";
    }
    @RequestMapping("/modifypw")
    public String modify(ClientAppKey clientAppKey){
        ClientAppKey clientAppKey1 = clientAppKeyDao.findByAppKey(clientAppKey.getAppKey());
        clientAppKey1.setPassword(clientAppKey.getPassword());
        clientAppKeyDao.save(clientAppKey1);
        return "修改成功";
    }
}
