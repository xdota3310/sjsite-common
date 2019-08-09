package com.lr.common.Excel;

//import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * excel导出Aspect
 *
 * @date 2019-3-18 10:26
 */
@Aspect
@Component
//@Slf4j
public class ExportExcelAspect {

    /**
     * 导出拦截处理
     */
    @Around("@annotation(com.ucar.pms.annotation.ExportExcel)")
    public Object exportExcel(ProceedingJoinPoint pjp) throws Throwable {
        // 1. 获取exportContext
        Object[] args = pjp.getArgs();
        PageQuery query = null;
        for (Object arg : args) {
            if (arg instanceof PageQuery) {
                query = ((PageQuery) arg);
                break;
            }

        }
        Object obj = pjp.proceed();

        if (query == null || query.exportContext == null || query.exportContext.getColumnList().isEmpty()) {
            return obj;
        }

        // 2. 获取拦截方法返回结果
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        if (!methodSignature.getReturnType().equals(Object.class)) {
            log.error("列表查询方法的返回类型请设置为Object，导出需要!!!");
            throw new BusinessRuntimeException("列表查询方法的返回类型请设置为Object");
        }
        if (!(obj instanceof Result) || !((Result) obj).isSuccess()) {
            log.error("目标方法返回类型不匹配：{}", obj.getClass());
            return obj;
        }

        // 3. 封装modelAndView
        // 3.1 数据列表
        ModelAndView mav = new ModelAndView();
        Object re = ((Result) obj).getRe();
        if (re instanceof List) {
            mav.addObject(ExportContext.KEY_LIST, re);
        } else if (re instanceof Page) {
            mav.addObject(ExportContext.KEY_LIST, ((Page) re).getRows());
        }

        // 3.2 导出上下文
        mav.addObject(ExportContext.KEY_EXPORT_CONTEXT, query.exportContext);

        // 3.3 view
        ExportExcel exportExcel = methodSignature.getMethod().getAnnotation(ExportExcel.class);
        String className = exportExcel.excelView().getSimpleName();
        mav.setViewName(className.substring(0, 1).toLowerCase() + className.substring(1));
        return mav;
    }
}
