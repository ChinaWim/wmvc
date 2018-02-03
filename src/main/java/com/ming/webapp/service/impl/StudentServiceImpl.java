package com.ming.webapp.service.impl;

import com.ming.webapp.pojo.Student;
import com.ming.webapp.service.StudentService;
import com.ming.wmvc.annotation.Service;

/**
 * Created by Ming on 2018/2/3.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public void add() {
        System.out.println("添加一个学生");
    }

    @Override
    public void delete(String sid) {
        System.out.println("根据id："+sid+"删除一个学生");
    }

    @Override
    public void update(Student student) {
        System.out.println("更新该学生的信息"+student);
    }

    @Override
    public Student search(String sid) {
        System.out.println("根据sid"+sid+"查找该学生");
        return null;
    }
}
