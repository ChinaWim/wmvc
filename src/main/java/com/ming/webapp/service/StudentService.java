package com.ming.webapp.service;

import com.ming.webapp.pojo.Student;

/**
 * Created by Ming on 2018/2/3.
 */
public interface StudentService {

    void add();
    void delete(String sid);
    void update(Student student);
    Student search(String sid);


}
