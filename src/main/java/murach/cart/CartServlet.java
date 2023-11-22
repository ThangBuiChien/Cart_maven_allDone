package murach.cart;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import murach.data.*;
import murach.business.*;

public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletContext sc = getServletContext();
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "cart";  // default action
        }

        // perform action and set URL to appropriate page
        String url = "/index.jsp";
        if (action.equals("shop")) {            
            url = "/index.jsp";    // the "index" page
        } 
        else if (action.equals("cart")) {
            String productCode = request.getParameter("productCode");
            String quantityString = request.getParameter("quantity");
            
            
            
            String giamGia = request.getParameter("giamgia");

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            //if the user enters a negative or invalid quantity,
            //the quantity is automatically reset to 1.
            int quantity;
            

            int giamGiaDouble=0;
            try {
                quantity = Integer.parseInt(quantityString);

                if (quantity < 0) {
                    quantity = 1;
                }
                giamGiaDouble =  Integer.parseInt(giamGia);
                
//                if (giamGiaDouble < 0 && giamGiaDouble > 100) {
//                    giamGiaDouble = 0;
//                }
                

            } catch (NumberFormatException nfe) {
                quantity = 1;giamGiaDouble = 0;
                
                
            }
            
            
            //console.log(giamGiaDouble);
            	System.out.println("giamGiaDouble email: " + giamGiaDouble);
            



            String path = sc.getRealPath("/WEB-INF/products.txt");
            Product product = ProductIO.getProduct(productCode, path);

            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);
            lineItem.setGiamgia(giamGiaDouble);
         

            
            if (quantity > 0) {
                
             cart.addItem(lineItem);
             System.out.println("reading from lineItem email: " + lineItem.getTotalCurrencyFormat());
               
            
            System.out.println("THIS NOTE DELETE AS QUALITY =  " + quantity);

            } else if (quantity == 0) {
                System.out.println("THIS DELETE FUNCTION WAS RUNNING " + quantity);

                cart.removeItem(lineItem);
                
            }

            session.setAttribute("cart", cart);
            url = "/cart.jsp";
        }
        else if (action.equals("checkout")) {
            HttpSession session = request.getSession();

            
            Cart currentCart = (Cart)session.getAttribute("cart");
            float tempTotal = currentCart.totalCart();
            if(tempTotal >= 50 && tempTotal <100){
                tempTotal = (float) (tempTotal*0.9);
            }
            if( tempTotal >=100  ){
                tempTotal = (float) (tempTotal*0.8);

            }
            request.setAttribute("thisIsTotal", tempTotal);
            url = "/checkout.jsp";
        }
        
                
        sc.getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
}