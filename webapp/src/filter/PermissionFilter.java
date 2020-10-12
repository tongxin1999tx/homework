package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
//@WebFilter(urlPatterns= {"*.do","*.jsp"})
public class PermissionFilter implements Filter {
 private String notCheckFilter;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		 //TODO Auto-generated method stub
 /*�ӻػ�session�л�ȡcurentUser�����ݣ���Ϊ�գ����ʾ������
  
  ��Щ������Ҫ���˵�
  ���Ȼ�ȡ�����urI
  */
		
		
		HttpServletRequest request = (HttpServletRequest)req;//�õ��������
		
		String path =request.getServletPath();
		System.out.println("�����ַurl:"+path);
		
		if(notCheckFilter.indexOf(path) == -1) {
			HttpSession session=request.getSession();
			if(session.getAttribute("currentUser")==null) {
				request.setAttribute("info", "û�з���Ȩ��");
				request.getRequestDispatcher("/error.jsp").forward(request, resp);
			}else {
				chain.doFilter(req, resp); //�Ѿ���¼ֱ�ӷ���
			}
		}else {
			chain.doFilter(req, resp);//�����ַ����Ҫ���ˣ�ֱ�ӷ���
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		notCheckFilter= config.getInitParameter("notCheckFilter");

	}

}
