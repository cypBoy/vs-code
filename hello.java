package com.ssm.controller;

import com.ssm.model.Applicant;
import com.ssm.model.Company;
import com.ssm.model.PageDataUtil;
import com.ssm.service.IAdminstratorsService;
import com.ssm.service.IApplicantService;
import com.ssm.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "adminstrators")
public class AdminstratorsController {
    @Autowired
    private IAdminstratorsService adminstratorsService;
    @Autowired
    private IApplicantService iApplicantService;
    @Autowired
    private ICompanyService iCompanyService;

    @RequestMapping(value = "ListByPageServlet",method = RequestMethod.POST)
    @ResponseBody
    public PageDataUtil<Applicant> listByPage(HttpServletRequest request){
        //初始化的参数
        int curPage=1;
        int  pageSize=2;
        //页面传过的当前的页码
        String cur= request.getParameter("curPage");
        //页面闯过的当前的页数量
        String pageS= request.getParameter("pageSize");
        if(cur!=null&&cur.trim().length()!=0){
            curPage=Integer.parseInt(cur);
        }
        if(pageS!=null && pageS.trim().length()!=0){
            pageSize=Integer.parseInt(pageS);
        }

        String a_status=request.getParameter("a_status");
        String a_phone=request.getParameter("a_phone");

        Map<String,Object> map=new HashMap<String,Object>();
        if(a_status!=null&& a_status.trim().length()!=0){
            map.put("a_status", a_status);
        }
        if(a_phone!=null&& a_phone.trim().length()!=0){
            a_phone = "%" + a_phone + "%";
            map.put("a_phone", a_phone);
        }
        PageDataUtil<Applicant> data=  adminstratorsService.showDataByPage(curPage, pageSize, map);
        return data;
    }

    @RequestMapping(value = "LoadUpdateUserServlet",method = RequestMethod.GET)
    public String loadUpdateUser(HttpServletRequest request) {
        String  a_id= request.getParameter("a_id");

        Applicant applicant=iApplicantService.findById(Integer.parseInt(a_id));
        //PrintWriter pw = response.getWriter();
        //pw.write("<script language='javascript'>alert('test');</script>");
        if(applicant!=null){
            request.setAttribute("applicant", applicant);
            //request.getRequestDispatcher("adminstrators/ApplicantUpdateStatus.jsp").forward(request, response);

        }
        return "adminstrators/ApplicantUpdateStatus";
    }
    @RequestMapping(value = "LoadUpdateUserPwdServlet",method = RequestMethod.GET)
    public String loadUpdatePwd(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String a_id = request.getParameter("a_id");
        //
        if (a_id != null) {
            boolean flag = iApplicantService.resetPwd(Integer.parseInt(a_id));
            if (flag) {
                //PrintWriter pw = response.getWriter();
                //pw.write("<script language='javascript'>alert('重置成功');</script>");
                //response.sendRedirect("adminstrators/ApplicantSelectMode.jsp");
                return "adminstrators/ApplicantSelectMode";
            } else {
                //PrintWriter pw = response.getWriter();
                //pw.write("<script language='javascript'>alert('重置失败');</script>");
            }
        }
        return "";
    }

    @RequestMapping(value = "UpdateStatusServlet",method = RequestMethod.POST)
    public String updateStatus(HttpServletRequest request){
        String a_id= request.getParameter("id");
        String a_status= request.getParameter("a_status");

        System.out.println("a_id"+a_id);
        System.out.println("a_status"+a_status);
        if(a_status!=null && a_id!=null){
            boolean flag= iApplicantService.updateApplicantStatus(Integer.parseInt(a_id), Integer.parseInt(a_status));
            if(flag){
                //JOptionPane.showMessageDialog(null, "修改成功！");

                //response.sendRedirect("adminstrators/ApplicantSelectMode.jsp");
                return "adminstrators/ApplicantSelectMode";
            }else{
                String msg="修改失败！";
                request.setAttribute("msg", msg);
                //request.getRequestDispatcher("adminstrators/ApplicantUpdateStatus.jsp").forward(request, response);
                return "adminstrators/ApplicantUpdateStatus";
            }
        }
        return "";
    }

    @RequestMapping(value = "CompanyListByPageServlet",method = RequestMethod.POST)
    @ResponseBody
    public PageDataUtil<Company> companyList(HttpServletRequest request){
        //初始化的参数
        int curPage=1;
        int  pageSize=2;
        //页面传过的当前的页码
        String cur= request.getParameter("curPage");
        //页面闯过的当前的页数量
        String pageS= request.getParameter("pageSize");
        if(cur!=null&&cur.trim().length()!=0){
            curPage=Integer.parseInt(cur);
        }
        if(pageS!=null && pageS.trim().length()!=0){
            pageSize=Integer.parseInt(pageS);
        }

        String c_status=request.getParameter("c_status");
        String c_phone=request.getParameter("c_phone");

        Map<String,Object> map=new HashMap<String,Object>();
        if(c_status!=null&& c_status.trim().length()!=0){
            map.put("c_status", c_status);
        }
        if(c_phone!=null&& c_phone.trim().length()!=0){
            c_phone = "%" + c_phone + "%";
            map.put("c_phone", c_phone);
        }
        PageDataUtil<Company> data=  adminstratorsService.showCompanyDataByPage(curPage,pageSize,map);
        return data;
    }

    @RequestMapping(value = "LoadUpdateCompanyServlet",method = RequestMethod.GET)
    public String loadUpdateCompany(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String  c_id= request.getParameter("c_id");

        Company company=iCompanyService.getCompanyById(Integer.parseInt(c_id));
        //PrintWriter pw = response.getWriter();
        //pw.write("<script language='javascript'>alert('test');</script>");
        if(company!=null){
            request.setAttribute("company", company);
            //request.getRequestDispatcher("adminstrators/CompanyUpdateStatus.jsp").forward(request, response);
        }
        return "adminstrators/CompanyUpdateStatus";
    }
    @RequestMapping(value = "LoadUpdateCompanyPwdServlet",method = RequestMethod.GET)
    public String loadUpdateCompanyPwd(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String c_id = request.getParameter("c_id");
        //
        if (c_id != null) {
            boolean flag = iCompanyService.resetPwd(Integer.parseInt(c_id));
            //JOptionPane.showConfirmDialog(new JFrame().getContentPane(),"用户名已注册？", "系统信息",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (flag) {
                //PrintWriter pw = response.getWriter();
                //pw.write("<script language='javascript'>alert('重置成功');</script>");
                //response.sendRedirect("adminstrators/CompanySelectMode.jsp");
                return "adminstrators/CompanySelectMode";
            } else {
                //PrintWriter pw = response.getWriter();
                //pw.write("<script language='javascript'>alert('重置失败');</script>");
            }
        }
        return "";
    }
    @RequestMapping(value = "UpdateCompanyStatusServle",method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        String c_id= request.getParameter("id");
        String c_status= request.getParameter("c_status");

        if(c_status!=null && c_id!=null){
            boolean flag= iCompanyService.updateCompanyStatus(Integer.parseInt(c_id), Integer.parseInt(c_status));
            if(flag){
                //JOptionPane.showMessageDialog(null, "修改成功！");
                return "adminstrators/CompanySelectMode";
                //response.sendRedirect("adminstrators/CompanySelectMode.jsp");
            }else{
                String msg="修改失败！";
                request.setAttribute("msg", msg);
                //request.getRequestDispatcher("adminstrators/CompanyUpdateStatus.jsp").forward(request, response);
                return "adminstrators/CompanyUpdateStatus";
            }
        }
        return "";
    }
}
