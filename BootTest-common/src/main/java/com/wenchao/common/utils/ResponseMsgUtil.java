package com.wenchao.common.utils;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;


import com.wenchao.common.bean.Result;
import com.wenchao.common.enums.EnumResultCode;



public class ResponseMsgUtil implements Serializable{
	
//	private static  Logger log=LoggerFactory.getLogger(ResponseMsgUtil.class);

    /**
	 * 
	 */
	private static final long serialVersionUID = 6636374262168023014L;
	
	public static final String SUCCESS_MESSAGE="操作成功";
	
	public static final String FAIL_MESSAGE="操作失败";

	/**
     * 根据消息码等生成接口返回对象
     *
     * @param code 结果返回码
     * @param msg  结果返回消息
     * @param data 数据对象
     * @param <T>
     * @return
     */
    public static <T> Result<T> response(int code, String msg, T data) {
        Result<T> res = new Result<T>();
        res.setResCode(code);
        res.setResMsg(msg);
        res.setData(data);
        return res;
    }
    public static <T> Result<T> responseSuccess(T data) {
        Result<T> res = new Result<T>();
        res.setResCode(EnumResultCode.SUCCESS.getCode());
        res.setResMsg(SUCCESS_MESSAGE);
        res.setData(data);
        return res;
    }
    public static <T> Result<T> responseFail(String msg) {
        Result<T> res = new Result<T>();
        res.setResCode(EnumResultCode.FAIL.getCode());
        res.setResMsg(msg);
        return res;
    }
    public static <T> Result<T> responseFail(int code,String msg) {
        Result<T> res = new Result<T>();
        res.setResCode(code);
        res.setResMsg(msg);
        return res;
    }
    public static <T> Result<T> responseFail(EnumResultCode errorNnum) {
        Result<T> res = new Result<T>();
        res.setResCode(errorNnum.getCode());
        res.setResMsg(errorNnum.getMessage());
        return res;
    }
    /**
     * 请求异常返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> exception() {
        return response(EnumResultCode.INTERNAL_SERVER_ERROR.getCode(), "服务异常", null);
    }

    /* *
     * @Description 封装response  统一json返回
     * @Param [encoding, outStr, response]
     * @Return void
     */
    public static void responseWrite(String outStr, HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter =response.getWriter();
            printWriter.write(outStr);
        }catch (Exception e) {
        	e.printStackTrace();
        }finally {
            if (null != printWriter) {
                printWriter.close();
            }
        }
    }
    
}
