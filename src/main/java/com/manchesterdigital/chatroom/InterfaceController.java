package com.manchesterdigital.chatroom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;

/**
 * Created by benyonj on 09/11/2016.
 */
@Controller
public class InterfaceController {

    @RequestMapping("/")
    public String index() throws FileNotFoundException {


        return "index.html";
    }
}
