package com.gcm.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.*;
import com.google.android.gcm.server.Message.Builder;

public class Transfer extends HttpServlet {

	// public static String GCM_SENDER_ID = "146531582959";
	public static String GCM_API_KEY = "AIzaSyBVwL6lgVPHzHye9E0TZrbzGFTA6MJ25ug";

	/**
	 * Constructor of the object.
	 */
	public Transfer() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String text = request.getParameter("message");
		Sender sender = new Sender(GCM_API_KEY);
		Message message = new Message.Builder().addData("msg", text).build();
		Result result = sender.send(message, Register.RegisterID, 5);
		PrintWriter writer = response.getWriter();
		if (result.getMessageId() != null) {
			String canonicalRegId = result.getCanonicalRegistrationId();
			if (canonicalRegId != null) {
				// same device has more than on registration ID: update database
			}
			writer.write(result.getMessageId() + "---");
		} else {
			String error = result.getErrorCodeName();

			if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
				// application has been removed from device - unregister
				// database
			}

			writer.write(error + "***");
		}

		writer.write("Send to " + Register.RegisterID + "is OK" + "+++");
		writer.close();

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
