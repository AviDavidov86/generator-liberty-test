package com.serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Set;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import io.jsonwebtoken.Claims;
import javax.annotation.security.DeclareRoles;
import javax.json.JsonObject;
import javax.security.auth.Subject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.json.java.JSONObject;
import com.ibm.websphere.security.WSSecurityException;
import com.ibm.websphere.security.auth.WSSubject;

/**
 * Servlet implementation class ProtectedServlet
 */

public class ProtectedServlet extends HttpServlet {
		
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProtectedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=utf-8");
		  PrintWriter out = response.getWriter();
		try{
			Subject wasSubj;
			try{ wasSubj = WSSubject.getRunAsSubject(); }
			catch(WSSecurityException e){throw new IOException(e);}
	        Set<Hashtable> creds = wasSubj.getPrivateCredentials(Hashtable.class);
	        Hashtable payLoad = null;
	        for(Hashtable t : creds){
	        	if(t.containsKey("access_token")){
	        		payLoad = t;	
	        	}
	        }
	        if(payLoad!=null){
	        	HttpSession session = request.getSession();
	        	session.setAttribute("id_token",getTokenPayload(payLoad.get("id_token").toString()));
	        	session.setAttribute("access_token",getTokenPayload(payLoad.get("access_token").toString()));
        		JSONObject object = new JSONObject(); 
        		object = JSONObject.parse(getTokenPayload(payLoad.get("id_token").toString()));
        		String username = object.get("name").toString();
        		request.setAttribute("username",username);
        		String userImage = object.get("picture").toString();
        		session.setAttribute("userImage", userImage);
        		
	        }else{
	        	out.println("No access_token located via security context");
	        }
		}catch(Exception e){
			e.printStackTrace(out);}
		 request.getRequestDispatcher("/WEB-INF/Welcome.jsp").forward(request, response);
		 
		
	}
	
	private String getTokenPayload(String token) {
		String parts[] = token.split("\\.");
		String payload64 = parts[1];
		String payload = new String( Base64.decodeBase64(payload64) );
		return payload;
	}
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
