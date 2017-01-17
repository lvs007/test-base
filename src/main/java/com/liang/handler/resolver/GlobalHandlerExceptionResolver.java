package com.liang.handler.resolver;

import com.liang.commons.ErrorField;
import com.liang.commons.Response;
import com.liang.exception.ServiceException;
import com.liang.exception.ServiceKeyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 全局异常统一处理类,将错误信息转换成合适的JSON格式返回。
 *
 * @author skyfalling
 */
public class GlobalHandlerExceptionResolver extends DefaultHandlerExceptionResolver {

    public GlobalHandlerExceptionResolver() {
        setOrder(0);
    }

    /**
     * 处理异常，返回null则不进行处理
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpRequest,
                                         HttpServletResponse httpResponse, Object handler, Exception e) {
        e.printStackTrace();
        Locale locale = RequestContextUtils.getLocaleResolver(httpRequest).resolveLocale(httpRequest);
        WebApplicationContext context = RequestContextUtils.getWebApplicationContext(httpRequest);

        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);
        view.addStaticAttribute("view", bindError(httpResponse, context, e, locale));
        return new ModelAndView(view);
    }


    /**
     * 绑定错误信息
     *
     * @param context
     * @param e
     * @param locale
     * @return
     */
    protected Response bindError(HttpServletResponse httpResponse, ApplicationContext context, Exception e, Locale locale) {
        Response response = new Response();
        Errors errors = getErrors(e);
        if (e instanceof MessageSourceResolvable) {
            response.setMessage(context.getMessage((MessageSourceResolvable) e, locale));
        } else if (e instanceof ServiceException) {
            response.setMessage(e.getMessage());
        } else if (e instanceof ServiceKeyException) {
            ServiceKeyException keyException = (ServiceKeyException) e;
            response.setMessage(context.getMessage(keyException.getMessage(), keyException.getParams(), locale));
        } else if (errors == null) {
            response.setMessage("");
        }
        httpResponse.setStatus(httpStatus(e, errors).value());
        response.setErrorId(1);
        response.setFields(renderErrors(context, errors, locale));
        return response;
    }


    /**
     * 获取HttpStatus状态码
     *
     * @param e
     * @param errors
     * @return
     */
    private HttpStatus httpStatus(Exception e, Errors errors) {
        if (e instanceof MessageSourceResolvable
                || e instanceof ServiceException
                || e instanceof ServiceKeyException || errors != null) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * 渲染错误消息
     *
     * @param context
     * @param locale
     * @return
     */
    protected List<ErrorField> renderErrors(ApplicationContext context, Errors errors, Locale locale) {
        List<ErrorField> list = new ArrayList<ErrorField>();
        if (errors != null) {
            for (ObjectError error : errors.getAllErrors()) {
                String fieldName = error.getObjectName();
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    if (StringUtils.isNotEmpty(fieldError.getField())) {
                        fieldName = ((FieldError) error).getField();
                    }
                }
                list.add(new ErrorField(fieldName, context.getMessage(error, locale)));
            }
        }

        return list;
    }

    /**
     * 从异常中获取错误消息
     *
     * @param e
     * @return
     */
    private Errors getErrors(Exception e) {
        Method method = ReflectionUtils.findMethod(e.getClass(), "getBindingResult");
        if (method != null) {
            ReflectionUtils.makeAccessible(method);
            return (Errors) ReflectionUtils.invokeMethod(method, e);
        }
        return null;
    }
}