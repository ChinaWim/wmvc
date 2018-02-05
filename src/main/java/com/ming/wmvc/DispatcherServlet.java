package com.ming.wmvc;

import com.ming.wmvc.annotation.RequestMapping;
import com.ming.wmvc.bean.Handler;
import com.ming.wmvc.enums.RequestMethod;
import com.ming.wmvc.helper.IocHelper;
import com.ming.wmvc.helper.ScannerHelper;
import com.ming.wmvc.helper.HandlerHelper;
import com.ming.wmvc.utils.PropertiesUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Ming on 2018/2/2.
 */
@WebServlet(urlPatterns = "/*")
public class DispatcherServlet extends javax.servlet.http.HttpServlet {
    private final Map<String ,Object> instances = new HashMap<>();
    private final Map<String ,Handler> methods = new HashMap<>();

    @Override
    public void init( ServletConfig config) throws ServletException {
        try {
            //包扫描 得到所有.class文件对象
            Map<String, Class<?>> classMap = ScannerHelper.scannerClass(PropertiesUtil.getValue("wmvc.basePackage"));
            //对.class文件对象控制反转
            IocHelper.init(classMap,instances,methods);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestURI = request.getRequestURI();
            String context = request.getContextPath();
            requestURI =  requestURI.substring(context.length());

            //根据当前的请求获取 Handler
            Handler handler = methods.get(requestURI);

            if(handler == null) {
                response.sendError(404); return;
            }
            //请求的方法
            String requestMethod = request.getMethod();
            RequestMethod annoMethod = handler.getMethod().getAnnotation(RequestMapping.class).method();
            if(annoMethod.getMethod().equals("GET_POST") || annoMethod.getMethod().equals(requestMethod))
                HandlerHelper.invokeHandler(handler,instances,request,response);
            else {
                throw  new RuntimeException("请求方式错误!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
