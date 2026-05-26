package gym_in_tech.controller;

public class MainController {
    private final UserRepository userRepository;

    @Autowired
    MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Home page
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    // Logging in
    @GetMapping("/mylogin")
    public String login() {
        return "login";
    }

    @PostMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }


    // Signing up
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    // Dashboards
    @GetMapping("/userDashboard")
    public String userDashboard(Authentication authentication, Model model) {
        model.addAttribute("name", authentication.getName());
        return "userDashboard";
    }

    @GetMapping("/adminDashboard")
    public String adminDashboard(Authentication authentication, Model model) {
        model.addAttribute("name", authentication.getName());
        return "adminDashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        String returnPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            returnPage = "forward:adminDashboard";
        } else {
            returnPage = "forward:userDashboard";
        }
        return returnPage;
    }

    // Logging out
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

}
