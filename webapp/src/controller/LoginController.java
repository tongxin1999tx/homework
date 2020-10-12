package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import model.dao.UserDao;
import model.vo.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns="/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	 //������������ʽΪutf-8
		request.setCharacterEncoding("utf-8");
		//1.���Ʊ��ĸ�Ԫ�ص�name����ֵ��ȡ���������
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String vcode = request.getParameter("userCode");
		//��ȡHttpSession����
		HttpSession session= request.getSession();
		//ȡ��CreateVerifyCodeImageController�д�ŵ���֤���ַ���
		String saveVcode=(String)session.getAttribute("vcodeImg");
		String forwardPath="";
		//�Ƚ��������֤����������ɵ���֤���Ƿ�һ��
		if(!vcode.equalsIgnoreCase(saveVcode)) {
			request.setAttribute("info", "��֤�����");
			forwardPath="/error.jsp";
			
		}else {
			UserDao userDao = new UserDao();
			User user=userDao.get(userName);
			System.out.println(userName);
			if(user==null) {
				request.setAttribute("info", "��������û������ڣ�");
				forwardPath="/error.jsp";
			}else {//�û�������
				if(!user.getPassword().equals(password)) {//�������
					//User currentUser = userDao.get(userName, password);
					request.setAttribute("info", "����������벻��ȷ!");
					forwardPath="/error.jsp";
				}
				else {
					session.setAttribute("currentUser", user);
					request.setAttribute("userName",userName);
					forwardPath="/main.jsp";
				}
			}			
		}
		
		RequestDispatcher rd= request.getRequestDispatcher(forwardPath);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
