package mart;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MartServlet extends HttpServlet {
	
	private PrintWriter out;
	private Cart cart;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html; charset=UTF-8");
		
		out = response.getWriter();
		
		HttpSession session = request.getSession();			
		synchronized (session)
		{
			cart = (Cart) session.getAttribute("cart");
			if (cart == null)
				cart = new Cart();
			
			session.setAttribute("cart", cart);
		}
		
		createHTMLHead();
		createHeader();
		
		out.println("<div class='content panel'>");
		
		String type = request.getParameter("type");
		
		if ("pay".equals(type))
		{
			out.println("<h1>Payment</h1>");
			out.println("<div class='column small'>");
			out.println("<div class='cart'>");
			printCart(true);		
			out.println("</div>");	// close div.cart
			out.println("</div>");	// close div.column small
		
			out.println("<div class='column large'>");			 
			createPaymentPage();
			out.println("</div>");	// close div.column large
		}
		else if ("about".equals(type))
		{
			createAboutPage();
		}
		else if (type == null) 
		{
			createHomePage();
		}
		else if (Inventory.getINTType(type) != -1)
		{
			String name = request.getParameter("name");
			
			if (name == null || Inventory.getINTName(type, name) == -1 || request.getParameter("submit") != null || request.getParameter("remove") != null)
				createItemIndexPage(type);
			else 
				createItemDetailsPage(type, name);
		}
		else 
		{
			printErrorMessage(2);
		}
		
		out.println("</div>");	// close div.content panel
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{	
		out = response.getWriter();
		Stockroom s = Stockroom.getInstance();		
		HttpSession session = request.getSession();
		
		if ("Submit".equals(request.getParameter("cardDetails")))
		{
			createHTMLHead();
			createHeader();
		
			out.println("<div class='content panel'>");
			out.println("<h1>Payment</h1>");
			out.println("<div class='column small'>");
			out.println("<div class='cart'>");
			printCart(true);		
			out.println("</div>");	// close div.cart
			out.println("</div>");	// close div.column small
		
			out.println("<div class='column large'>");	
			createFinishPayment(request);
			out.println("</div>");	// close div.column large
			out.println("</div>");	// close div.content panel
			out.println("</body>");
			out.println("</html>");
		}
		else
		{		
			String type = request.getParameter("type");
			String name = request.getParameter("name");
			
			int t = Inventory.getINTType(type);
			int n = Inventory.getINTName(type, name);
			
			synchronized (session)
			{
				cart = (Cart) session.getAttribute("cart");
				if (cart == null)
					cart = new Cart();
				
				Item i = s.get(t, n);
				if (request.getParameter("remove") != null)
				{
					cart.removeToCart(i);
				}
				else // add an item to the cart
				{
					int amount = Integer.parseInt(request.getParameter("amount"));
					if (s.getStockOf(t, n) >= (amount + cart.quantityOf(i)) && amount > 0)
					{
						cart.addToCart(i, amount);
					}
				}
				
				session.setAttribute("cart", cart);
			}		
			doGet(request, response);
		}
	}
	
	private void createHTMLHead()
	{
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Pok&eacute; Mart</title>");
		out.println("<link rel='stylesheet' type='text/css' href='css/style.css'>");
		out.println("<link rel='shortcut icon' type='image/ico' href='images/favicon.ico'>");
		out.println("<script src='scripts/script.js'></script>");
		out.println("</head>");
		out.println("<body>");
	}
	
	private void createHeader() 
	{
		out.println("<div class='header panel'>");
		out.println("<a class='tab' href='mart'>Home</a>");
		
		String[] types = Inventory.GETTypes();
		String[] displayTypes = Inventory.displayTypes();
		for (int i = 0; i < types.length; i++) {
			out.println("<a class='tab' href='mart?type=" + types[i] + "'>" + displayTypes[i] + "</a>");
		}
		
		out.println("<a class='tab' href='mart?type=about'>About</a>");
		
		out.println("</div>");	// end div.header panel
	}
	
	private void createHomePage() 
	{
		out.println("<div class='home'>");
		out.println("<h1>Welcome</h1>");
		out.println("<img src='images/pokemart.png' />");
		out.println("<br>");
		out.println("Welcome to the Jors Town Pok&eacute; Mart!");
		out.println("<p>We offer a wide variety of products that are necessary for Pok&eacute;mon training. When you buy our products, your journey to become the very best like no one ever was becomes easier and faster. We have items that could help you when faced with great dangers inside those dungeons. Our Pok&eacute; balls are designed to catch every Pok&eacute;mon there is, as long as training them is your cause. In fact, we are the only Pok&eacute; Mart that offers the Legendary Master Ball! Be sure to grab it while supply lasts.</p>");
		out.println("<p>Furthermore, if your Pok&eacute;mon are injured or you just want to stock up on medicines, we also sell different kinds of medicinal items that could aid you during your battles especially with those gym bosses.</p>");
		out.println("<p>Just click on the tabs above and use those to navigate between the products that you want to buy. A shopping cart is located at your left to give you a summary of the items that you have already chosen to buy. After that, just proceed with the payment by clicking the button located at the bottom part of your shopping cart. Please be ready with your bank account details as you will be needing them to finalize your transactions. When everything is done, just wait for your products to be delivered to you at your doorstep. Yes, you do not need to supply your address anymore because our delivery crew, The Team Rocket, is the best in the world.</p>");
		out.println("Have fun shopping!");
		out.println("</div>");
	}
	
	private void createAboutPage()
	{
		out.println("<h1>Pok&eacute; Mart</h1>");
		
		out.println("<div class='large about'>");
		out.println("In partial fulfillment<br>");
		out.println("of the requirements in<br>");
		out.println("<h4>CMSC 121</h4>");
		out.println("<hr>");
		out.println("Damian Fadri<br>");
		out.println("Marron Marasigan<br>");
		out.println("Reinier Maristela<br>");
		out.println("Reniel Estanilao<br>");
		out.println("<hr>");
		out.println("Presented to<br>");
		out.println("<h4>Joanna Marielle Jimenez</h4>");		
		out.println("</div>");	// end div.center large	
		
		out.println("<div class='large disclaimer'>");
		out.println("Pok&eacute;mon is a registered trademark of Nintendo, Creatures, Game Freak and The Pok&eacute;mon Company.<br>");
		out.println("Copyright &copy; 1995-2015 All rights reserved.");
		out.println("</div>");	// end div.disclaimer
		
		
	}
	
	private void createItemIndexPage(String type) 
	{
		int t = Inventory.getINTType(type);
		Stockroom s = Stockroom.getInstance();
		
		
		String[] GETNames = Inventory.GETNames(t);
		String[] displayNames = Inventory.displayNames(t);
		
		out.println("<h1>" + Inventory.getDisplayType(t) + "</h1>");
		out.println("<div class='column small'>");
		out.println("<div class='cart'>");
		printCart(false);		
		out.println("</div>");	// end div.cart
		out.println("</div>");	// end div.column small
		
		out.println("<div class='column large'>");
		
		for (int i = 0; i < GETNames.length; i++)
		{
			Item item = s.get(t, i);
			out.println("<div class='entry'>");
			out.println("<div class='thumb'>");
			
			out.println("<img class='thumb' src='" + item.thumbURL() +  "'><br>");
			out.println("<h3>" + Inventory.getDisplayName(t, i) + "</h3>");
			out.println("P" + item.price());
			out.println("<form method='post' action='mart'>");
			out.println("<input type='hidden' name='type' value='" + Inventory.getGETType(item.type()) + "'>");
			out.println("<input type='hidden' name='name' value='" + Inventory.getGETName(item.type(), item.name()) + "'>");
			
			out.println("<input type='button' value='-' onClick='decrement(" + i + ")'>");
			out.println("<input type='text' name='amount' value='0' size='1' readonly>");
			out.println("<input type='button' value='+' onClick='increment(" + i + ", " + (item.stock() - cart.quantityOf(item)) + ")'><br>");
			out.println("<input type='submit' name='submit' value='Add to Cart'>");
			out.println("</form>");
			out.println("</div>");	// end div.thumb
			
			out.println("<a target='_blank' href='" + "mart?type=" + type + "&name=" + Inventory.getGETName(t,i) + "'>Learn more!</a>");
			out.println( "</div>");	// end div.entry
		}
		out.println("</div>");	// end div.column large
	}
	
	private void createItemDetailsPage(String type, String name) 
	{
		int t = Inventory.getINTType(type);
		int n = Inventory.getINTName(type, name);
		Stockroom s = Stockroom.getInstance();
		Item i = s.get(t, n);
		
		out.println("<h1>" + Inventory.getDisplayName(t,n) + "</h1>");
		out.println("<div class='column equal'>");
		out.println("<div class='info'>");
		out.println("<img class='full' src='" + i.fullURL() + "'><br><br>");
		out.println("<hr>");
		out.println("<h4>Price: </h4>");
		out.println("P" + i.price() + "<br>");
		out.println("<h4>Stock: </h4>");
		if (i.stock() == 0)
			out.println("Out of stock");
		else
			out.println(i.stock());
		
		out.println("</div>");	// end div.info
		out.println("</div>");	// end div.column equal
		
		// Second column
		out.println("<div class='column equal'>");
		
		out.println("<fieldset>");
		out.println("<legend>Description</legend>");
		out.println("<p>" + i.description() + "</p>");
		out.println("</fieldset>");	// end fieldset
		out.println("<fieldset>");
		out.println("<legend>Rarity</legend>");
		out.println(i.rarity());
		out.println("</fieldset>");	// end fieldset
		
		out.println("</div>");	// end div.column equal
	}
	
	private void printCart(boolean isDisabled) 
	{
		out.println("<div class='cart_contents'>");	
		
		for (Item item : cart.iterator())
		{
			int qty = cart.quantityOf(item);
			int price = item.price() * qty;
			
			out.println("<div class='cart_item'>");	
			out.println("<img src='" + item.spriteURL() + "'>");
			out.println("<span class='item_name'>" + Inventory.getDisplayName(item.type(), item.name()) + "</span>");
			out.println("<span class='item_qty'>Qty: " + qty + "</span>");
			out.println("<span class='item_price'>P" + price + "</span>");
			
			out.println("</div>");	// end div.cart_item
			
			if (!isDisabled)
			{
				out.println("<form class='remove' method='post' action='mart'>");
				
				out.println("<input type='hidden' name='type' value='" + Inventory.getGETType(item.type()) + "'>");
				out.println("<input type='hidden' name='name' value='" + Inventory.getGETName(item.type(), item.name()) + "'>");
				out.println("<input type='submit' name='remove' value='X'>");
				
				out.println("</form>");	// end form
			}
		}
		
		out.println("</div>");	// end div.cart_contents
		
		out.println("<hr>");
		out.println("<div class='bottom'>");
		
		out.println("Total Price: P" + cart.total());
		if (!(isDisabled || cart.total() == 0))
		{
			out.println("<form method='get' action='mart'>");
			out.println("<input type='hidden' name='type' value='pay'>");
			out.println("<input type='submit' value='Proceed to payment'>");
			out.println("</form>");	// end form
		}
		out.println("</div>");	// end div.bottom
	}
	
	private void createPaymentPage() 
	{		
		out.println("<form method='post' action='mart'>");
		out.println("<fieldset>");
		
		out.println("<legend>Payment Options</legend>");		
		out.println("<input type='radio' name='card' value='Visa' checked>Visa");		
		out.println("<input type='radio' name='card' value='MasterCard'>MasterCard");
		
		out.println("</fieldset>");	// close fieldset		
		
		out.println("<fieldset>");
		
		out.println("<legend>Card Details</legend>");
		out.println("Card No: <input type='text' name='cardNumber' maxlength='16' size='16'><br>");
		out.println("Expiry Date: ");
		out.println("<select name='month'>");
		out.println("<option value='1'>January</option>");
		out.println("<option value='2'>February</option>");
		out.println("<option value='3'>March</option>");
		out.println("<option value='4'>April</option>");
		out.println("<option value='5'>May</option>");
		out.println("<option value='6'>June</option>");
		out.println("<option value='7'>July</option>");
		out.println("<option value='8'>August</option>");
		out.println("<option value='9'>September</option>");
		out.println("<option value='10'>October</option>");
		out.println("<option value='11'>November</option>");
		out.println("<option value='12'>December</option>");
		out.println("</select>");
		out.println("<select name='year'>");
		out.println("<option value='2015'>2015</option>");
		out.println("<option value='2016'>2016</option>");
		out.println("<option value='2017'>2017</option>");
		out.println("<option value='2018'>2018</option>");
		out.println("<option value='2019'>2019</option>");
		out.println("<option value='2020'>2020</option>");
		out.println("</select><br>");
		out.println("Card Verification Code: <input type='text' name='cvc' maxlength='3' size='3'>");
		out.println("<br>");
		
		out.println("</fieldset>");	// close fieldset
		
		out.print("<input type='submit' name='cardDetails' value='Submit'");
		if (cart.size() == 0)
			out.print(" disabled");
		out.println(">");
		out.println("</form>"); // close form
	}
	
	private void createFinishPayment(HttpServletRequest request)
	{
		String card = request.getParameter("card");
		String cardNumber = request.getParameter("cardNumber");
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		String cvc = request.getParameter("cvc");
		
		HttpSession session = request.getSession();
		if (session != null && "5123456789012346".equals(cardNumber.trim()) && checkIfExpired(month, year) && "111".equals(cvc.trim())) 
		{
			synchronized (session)
			{
				cart = (Cart) session.getAttribute("cart");
				if (cart == null)
					cart = new Cart();
				
				if (cart.size() != 0 && deductStock()) 
				{
					session.invalidate();
					
					out.println("<h3>Payment Successful!</h3>");
					out.println("<h4>Thank you for purchasing from the Pok&eacute; Mart!</h4>");
					out.println("<br><br>");
					out.println("<p>Here are the credentials used for the purchase:</p>");
					
					out.println("Card: " + card + "<br>");
					out.println("Card Number: " + cardNumber + "<br>");
					out.println("CVC: " + cvc + "<br>");
					out.println("<br><br>");
				}
				else 
				{
					printErrorMessage(0);
					out.println("<a href='mart?type=pay'>Try again.</a><br>");
				}
			}
		}
		else 
		{	
			printErrorMessage(1);
			out.println("<a href='mart?type=pay'>Try again.</a><br>");
		}
		
		out.println("<a href='mart'>Buy more!</a>");
	}
	
	private void printErrorMessage(int error)
	{
		switch (error)
		{
			case 0:
			{
				out.println("<h3>Oh no!</h3>");
				out.println("We could not complete your payment process.\n");
				out.println("<p>Something went from from our side.\n");
				out.println("Either an item went out of stock or your shopping session has expired.");
				out.println("Please clear your cache and try again.</p>");
				
				break;
			}
			case 1:
			{
				out.println("<h3>Oops!</h3>");
				out.println("We could not complete your payment process.\n");
				out.println("<p>Perhaps you mistyped a field or the information is invalid.");
				out.println("Please recheck your inputs and try again.</p>");
				
				break;
			}
			case 2:
			{
				out.println("<h3>Uh oh!</h3>");
				out.println("This page does not exist!");
				out.println("<p>It appears you are quite lost.");
				out.println("Please click on the tabs above to get back on track.</p>");
				
				break;
			}
			
		}
		
	}
	
	private boolean isNumeric(String str) 
	{
		for (char c : str.toCharArray()) 
		{
			if (!Character.isDigit(c)) 
				return false;
		}
		return true;
	}
	
	private boolean checkIfExpired(String month, String year) {
		if (year.equals("2017")) {
			if (Integer.parseInt(month) == 5) {
				return true;
			}
		}
		return false;
	}
	
	private boolean deductStock() {
		Stockroom s = Stockroom.getInstance();
		for (Item i : cart.iterator()) {
			int qty = cart.quantityOf(i);
			if (!(s.getStock(i.type(), i.name(), qty)))
				return false;
		}
		return true;
	}
	
}