package murach.cart;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;

import murach.data.ProductIO;
import murach.business.Product;

public class ProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String path = getServletContext().getRealPath("/WEB-INF/products.txt");
        ArrayList<Product> products = ProductIO.getProducts(path);
        session.setAttribute("products", products);
        
        request.setAttribute("result", "This is the result of the servlet call");
        
        try{
        String tokenUrl = "http://169.254.169.254/latest/api/token";
        URL tokenEndpoint = new URL(tokenUrl);
        HttpURLConnection tokenConnection = (HttpURLConnection) tokenEndpoint.openConnection();
        tokenConnection.setRequestMethod("PUT");
        tokenConnection.setRequestProperty("X-aws-ec2-metadata-token-ttl-seconds", "21600");

        BufferedReader tokenReader = new BufferedReader(new InputStreamReader(tokenConnection.getInputStream()));
        String token = tokenReader.readLine();

        // Use the session token to access the EC2 instance's profile metadata
        String instanceIdUrl = "http://169.254.169.254/latest/meta-data/instance-id";
        URL instanceIdEndpoint = new URL(instanceIdUrl);
        HttpURLConnection instanceIdConnection = (HttpURLConnection) instanceIdEndpoint.openConnection();
        instanceIdConnection.setRequestProperty("X-aws-ec2-metadata-token", token);

        BufferedReader instanceIdReader = new BufferedReader(new InputStreamReader(instanceIdConnection.getInputStream()));
        String instanceId = instanceIdReader.readLine();

        String availabilityZoneUrl = "http://169.254.169.254/latest/meta-data/placement/availability-zone";
        URL availabilityZoneEndpoint = new URL(availabilityZoneUrl);
        HttpURLConnection availabilityZoneConnection = (HttpURLConnection) availabilityZoneEndpoint.openConnection();
        availabilityZoneConnection.setRequestProperty("X-aws-ec2-metadata-token", token);

        BufferedReader availabilityZoneReader = new BufferedReader(new InputStreamReader(availabilityZoneConnection.getInputStream()));
        String availabilityZone = availabilityZoneReader.readLine();

        // Create the HTML content with instance information
        
        
//        String pathindex = this.getServletContext().getRealPath("/index.jsp");
//        
//        System.out.println("path index.jsp: " + pathindex);
        
        request.setAttribute("availabilityZone", availabilityZone);
        request.setAttribute("instanceId", instanceId);
        }
        catch(IOException e){
            
        }


                
                
        String url = "/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
