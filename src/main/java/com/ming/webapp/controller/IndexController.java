package com.ming.webapp.controller;

import com.ming.webapp.pojo.Student;
import com.ming.webapp.service.StudentService;
import com.ming.wmvc.annotation.*;
import com.ming.wmvc.enums.RequestMethod;
import com.ming.wmvc.enums.ScopeValue;

/**
 * Created by Ming on 2018/2/3.
 */
@Controller
@RequestMapping("/ming")
@Scope(ScopeValue.PROTOTYPE)
public class IndexController {
    Integer times = 0;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public void index(Student student,String arg1,Integer arg2,@RequestParam("arg33") Long arg3){
        System.out.println("times：" + ++times);
        System.out.println("student变量:"+student);
        studentService.add();
        studentService.delete(student.getSid());
        studentService.update(student);
        studentService.search(student.getSid());
        System.out.println("arg2变量:"+arg2 +" arg3变量:" + arg3);
    }
}
