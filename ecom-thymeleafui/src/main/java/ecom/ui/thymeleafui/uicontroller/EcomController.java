package ecom.ui.thymeleafui.uicontroller;

import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import ecom.ui.thymeleafui.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequestMapping("/ui")
public class EcomController {

    private static Map<String, Boolean> map = new ConcurrentHashMap<>();

    private String imgArr[] = {
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/red-book-hi8d6431a.png",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/indexa51d5d7.jpeg",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/blue-book-hic09def7.png",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/blue-book-reading-hid3b6f09.png",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/green-book-reading-hiec1b149.png",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/closed-book-cartoon-vector-symbol-icon-design-beautiful-illustr-illustration-isolated-white-background-975033320bc2a72.jpeg",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/inex290acda.jpeg",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/f958c0ca1c1701d236796ed90542a21940742f7.jpeg",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/index5848f8e.png",
            "https://s3-ap-southeast-1.amazonaws.com/he-public-data/2511916-orange-book-cartoon6cc76e1.jpeg"
    };



    @GetMapping("/home")
    public String sayHello(Model model) {
        model.addAttribute("date", new java.util.Date());
        return "home";
    }

    @GetMapping("/login")
    public String doLogin(Model model) {
        /*RestTemplate restTemplate = new RestTemplate();
        Boolean response = restTemplate.getForObject("http://localhost:8082/cart/authenticate?email=ashteo@gmail.com&password=12345", Boolean.class);
        log.info("user Logged in: {}", response);*/
        //model.addAttribute("login", response);
        return "login";
    }

    @GetMapping("/showloginform")
    public String showLogin(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        /*Boolean response = restTemplate.getForObject("http://localhost:8082/cart/authenticate?email=ashteo@gmail.com&password=12345", Boolean.class);
        log.info("user Logged in: {}", response);*/
        UserLogin credentials = new UserLogin();

        model.addAttribute("credentials", credentials);
        return "loginform";
    }

    @PostMapping("/authenticate")
    public String userAuthenticate(@ModelAttribute("credentials") UserLogin credential) {
        log.info("Uesr Request Credentials: {}", credential);
        final String email = credential.getEmail();
        final String pass = credential.getPassword();
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://EcomUserServices-env.eba-muw4m3u2.ap-south-1.elasticbeanstalk.com/cart/authenticate?email="+email+"&password="+pass;
        log.info("URL: "+uri);
        Boolean response = restTemplate.getForObject(uri, Boolean.class);
        log.info("user Logged in: {}", response);

        if (response) {
            map.put(email, true);
            return "redirect:/ui/loginsucces";
        }
        map.put(email, false);
        return "redirect:/ui/loginfail";
    }

    @GetMapping("/loginfail")
    public String authenticateFail(Model model) {
        return "loginformfailed";
    }

    @GetMapping("/loginsucces")
    public String authenticateSuccess(Model model) {
        return "redirect:/ui/catalog";
    }


    @GetMapping("/catalog")
    public String catalog(Model model) {
        SearchFields searchFields = new SearchFields();


        String pageNumber = searchFields.getPageNumber();
        String pageSize = searchFields.getPageSize();
        String order= searchFields.getOrder();
        String sortBy = searchFields.getSortBy();
        String title;
        String languageCode= searchFields.getLanguageCode();



        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://Bookservice-env.eba-irtamiwp.ap-south-1.elasticbeanstalk.com/bookapi?pageNumber="+pageNumber+"&pageSize="+pageSize+"&sortBy="+sortBy+"&sortDirection="+order;
        log.info("URL: "+uri);

        ResponseEntity<List<BookData>> rateResponse =
                restTemplate.exchange(uri,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<BookData>>() {});
        List<BookData> responseBody = rateResponse.getBody();
        log.info("Size: {}", responseBody);
        for (BookData b : responseBody) {
            b.setUrl(imgArr[getRandomInt()]);
        }

        model.addAttribute("books", responseBody);
        model.addAttribute("searchfilters", searchFields);

        return "showBooks";
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(9);
    }

    private int getRandomInt(int n) {
        Random random = new Random();
        return random.nextInt(n);
    }


    @GetMapping("/cart")
    public String cartShow(Model model) {

        String email = "ashteo@gmail.com";

        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://EcomUserServices-env.eba-muw4m3u2.ap-south-1.elasticbeanstalk.com/cart/"+email;
        log.info("URL: "+uri);
        String response = restTemplate.getForObject(uri, String.class);
        log.info("user Items in cart: {}", response);
        if (response!=null){

            RestTemplate restTemplate1 = new RestTemplate();

            uri = "http://Bookservice-env.eba-irtamiwp.ap-south-1.elasticbeanstalk.com/bookapi/getbylist?bookIDs="+response;
            log.info("URL for book service: "+uri);

            ResponseEntity<List<BookData>> bookResponse =
                    restTemplate.exchange(uri,
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<BookData>>() {});
            List<BookData> responseBody = bookResponse.getBody();
            log.info("Item from cart: {}", responseBody);
            log.info("Size: {}", responseBody.size());
            for (BookData b : responseBody) {
                b.setUrl(imgArr[getRandomInt()]);
            }
            model.addAttribute("books", responseBody);

            double totalAmt = responseBody.stream().mapToDouble(BookData::getPrice).sum();
            log.info("Total cart Value {}", totalAmt);

            model.addAttribute("totalcartvalue", totalAmt);

            return "cartpage";
        } else {
            if(map.containsKey(email) && map.get(email)){
                return "emptycart";
            } else {
                return "redirect:/ui/showloginform";
            }
        }
    }

    @PostMapping("/showfilteredres")
    public String showFilterBooks(@ModelAttribute("searchfilters") SearchFields searchFields, Model model) {
        log.info("Search Filter: {}", searchFields);

        String pageNumber = searchFields.getPageNumber();
        String pageSize = searchFields.getPageSize();
        String order= searchFields.getOrder();
        String sortBy = searchFields.getSortBy();
        String title = searchFields.getTitle();
        String languageCode = searchFields.getLanguageCode();
        String authors = searchFields.getAuthors();



        if(title!=null && (languageCode == null || languageCode.equals(""))) {
            languageCode ="en";
        }

        RestTemplate restTemplate = new RestTemplate();
        String uri = null;
        if(title==null || title.equals("") || languageCode==null || languageCode.equals("")) {
            uri = "http://Bookservice-env.eba-irtamiwp.ap-south-1.elasticbeanstalk.com/bookapi?pageNumber=" + pageNumber + "&pageSize=" + pageSize + "&sortBy=" + sortBy + "&sortDirection=" + order;
        } else {
            uri = "http://Bookservice-env.eba-irtamiwp.ap-south-1.elasticbeanstalk.com/bookapi?pageNumber=" + pageNumber +
                                                "&pageSize=" + pageSize +
                                                "&sortBy=" + sortBy +
                                                "&sortDirection=" + order +
                                                "&title="+ title +
                                                "&languageCode="+languageCode +
                                                "&authors="+authors;
        }

        log.info("URL: "+uri);

        ResponseEntity<List<BookData>> rateResponse =
                restTemplate.exchange(uri,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<BookData>>() {});
        List<BookData> responseBody = rateResponse.getBody();
        log.info("Size: {}", responseBody);
        for (BookData b : responseBody) {
            b.setUrl(imgArr[getRandomInt()]);
        }

        model.addAttribute("books", responseBody);
        model.addAttribute("searchfilters", searchFields);
        return "showFilteredbooks";

    }

    @GetMapping("/addtocart")
    public String addBookCart(String bookid, Model model) {
        log.info("Add to card nbook: "+bookid);
        String email = "ashteo@gmail.com";

        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://EcomUserServices-env.eba-muw4m3u2.ap-south-1.elasticbeanstalk.com/cart/"+email;
        log.info("URL: "+uri);
        String response = restTemplate.getForObject(uri, String.class);
        response = response+","+bookid;
        log.info("Update Card: {}", response);


        CartData cartData = CartData.builder().email(email).itemIDs(response).build();
        uri = "http://EcomUserServices-env.eba-muw4m3u2.ap-south-1.elasticbeanstalk.com/cart/";

        /*ResponseEntity<List<BookData>> rateResponse =
                restTemplate.exchange(uri,
                        HttpMethod.POST, cartData, BookData>>() {});
        List<BookData> responseBody = rateResponse.getBody();*/

        ResponseEntity<Boolean> added = restTemplate.postForEntity(uri, cartData, Boolean.class);
        log.info("cart updated: {}", added);

        return "redirect:/ui/cart";
    }

    @GetMapping("/removecartitem")
    public String removeBookCart(String bookid, Model model) {
        log.info("Remove cart book: "+bookid);
        String email = "ashteo@gmail.com";

        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://EcomUserServices-env.eba-muw4m3u2.ap-south-1.elasticbeanstalk.com/cart/"+email;
        log.info("URL: "+uri);
        String response = restTemplate.getForObject(uri, String.class);
        log.info("Existing Card: {}", response);

        String newItem = "";
        String arrItem[] = response.split(",");
        for (String s:arrItem) {
            if(s.equals(bookid)){continue;}
            if(newItem.equals("")){
                newItem = s;
            }else {
                newItem = newItem + "," + s;
            }
        }

        log.info("Update Card: {}", newItem);


        CartData cartData = CartData.builder().email(email).itemIDs(newItem).build();
        uri = "http://EcomUserServices-env.eba-muw4m3u2.ap-south-1.elasticbeanstalk.com/cart/";

        /*ResponseEntity<List<BookData>> rateResponse =
                restTemplate.exchange(uri,
                        HttpMethod.POST, cartData, BookData>>() {});
        List<BookData> responseBody = rateResponse.getBody();*/

        ResponseEntity<Boolean> added = restTemplate.postForEntity(uri, cartData, Boolean.class);
        log.info("cart updated: {}", added);

        return "redirect:/ui/cart";
    }

    @GetMapping("/transactionsuccess")
    public String successGetOrder(PaymentDetails details, Model model) {
        model.addAttribute("paydetails", details);
        return "transactionsuccess";
    }

    @PostMapping("/transactionsuccess")
    public String successPostOrder(Object obj) {
        return "transactionsuccess";
    }

    @GetMapping("/makepayment")
    public RedirectView createOrder(double amount) {
        RedirectView redirectView = new RedirectView();
        ApiContext context = ApiContext.create("test_S5Geexj0XilN54zX2bFr8NNZ5eEQzWmhJ04",
                "test_5WulkeAvd9vDvp7TuXBwRhG3arqZeDJwQYPLF4RACAOQqPteWDRt8tlHhxKkIvkZOrnBQSkiDEVQjma8zfeZaQmyXTw2aeeEwvbT7PoRtijlHL23DYMiWiQixCI",
                ApiContext.Mode.TEST);
        Instamojo api = new InstamojoImpl(context);
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setName("Ashish");
        paymentOrder.setEmail("ashishteotia98@gmail.com");
        paymentOrder.setPhone("9033088547");
        paymentOrder.setCurrency("INR");
        paymentOrder.setAmount(amount);
        paymentOrder.setDescription("This is a test transaction.");
        paymentOrder.setRedirectUrl("http://uiweb-env.eba-jbhtxqmy.ap-south-1.elasticbeanstalk.com/ui/transactionsuccess");
        //paymentOrder.setWebhookUrl("http://www.someurl.com/");
        paymentOrder.setTransactionId("ashishtransac"+getRandomInt(10000));

        log.info("ORDER: {}"+paymentOrder.toString());

        try {
            PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(paymentOrder);

            log.info("Payment Response: {}", paymentOrderResponse.toString());
            log.info("Payment Status: {}", paymentOrderResponse.getPaymentOrder().getStatus());


            String url = paymentOrderResponse.getPaymentOptions().getPaymentUrl();
            redirectView.setUrl(url);
            return redirectView;

        } catch (HTTPException e) {
            log.error("Error Code: {}", e.getStatusCode());
            log.error("Error MSG: {}", e.getMessage());
            log.error("Error Payload: {}", e.getJsonPayload());

        } catch (ConnectionException e) {
            log.error("Error e: {}",e.getMessage());
        }
        redirectView.setUrl("http://uiweb-env.eba-jbhtxqmy.ap-south-1.elasticbeanstalk.com/ui/onerr");
        return redirectView;
    }

    @GetMapping("/google")
    public RedirectView goGoole() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://www.yahoo.com");
        return redirectView;
    }

    @GetMapping("/onerr")
    public String onErr() {
        return "renamethisfatal";
    }


}
