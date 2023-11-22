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
            url = "/index.jsp"; // the "index" page
            //url = null;
           // action = "loadProducts";
        } 
        else if (action.equals("cart")) {
            String productCode = request.getParameter("productCode");
            
            String quantityString = request.getParameter("quantity");
            String vouchergot = request.getParameter("voucherCode");
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }
            else{
                String check = request.getParameter("addNew");
                if(check!= null && check.equals("yes")){
                    if(cart.getOneItemsByID(cart.getItems(), productCode) != null){
                    LineItem temp = cart.getOneItemsByID(cart.getItems(), productCode);
                    
                    int currentQuality = temp.getQuantity();

                     quantityString = String.valueOf(currentQuality + 1);
                    }
                }
            }
//            else{
//                if(cart.getOneItemsByID(cart.getItems(), productCode) != null){
//                    LineItem temp = cart.getOneItemsByID(cart.getItems(), productCode);
//                    int currentQuality = temp.getQuantity();
//                   // int cartQuality = Integer.parseInt(quantityString);
////                    if(currentQuality == cartQuality+1 || cartQuality+1 == currentQuality){
////                    }
//                    
//                    
//                    quantityString = String.valueOf(currentQuality + 1);
//
//                }
//            }

            //if the user enters a negative or invalid quantity,
            //the quantity is automatically reset to 1.
            int quantity;
            try {
                quantity = Integer.parseInt(quantityString);
                if (quantity < 0) {
                    quantity = 1;
                }
            } catch (NumberFormatException nfe) {
                quantity = 1;
            }

            String path = sc.getRealPath("/WEB-INF/products.txt");
            Product product = ProductIO.getProduct(productCode, path);

            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);
            if (quantity > 0) {
                cart.addItem(lineItem);
            } else if (quantity == 0) {
                cart.removeItem(lineItem);
            }
            String voucherCode = "GIAMGIA20";
            double percent = 0.2;
            Voucher voucher = new Voucher(voucherCode, percent);
            if(voucher.isVoucherValid(vouchergot)){
                voucher.applyDiscount(lineItem.getTotal());
            }
            else
            {
                percent = 1;
            }
            session.setAttribute("cart", cart);
            url = "/cart.jsp";
        }
        else if (action.equals("checkout")) {
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