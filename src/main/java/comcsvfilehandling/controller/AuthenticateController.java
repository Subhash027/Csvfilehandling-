package comcsvfilehandling.controller;


import comcsvfilehandling.model.JwtRequest;
import comcsvfilehandling.model.JwtResponse;
import comcsvfilehandling.service.UserService;
import comcsvfilehandling.utility.Jwtutility;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 * get credentials key
 */
@RestController
public class AuthenticateController {
    @Autowired
    private Jwtutility jwtutility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    /***
     *
     * @param jwtRequest
     * @return
     */
    @PostMapping("/getauth")
    @Operation(summary = "Get access credential key to access", description = "GET A TOKEN TO USING USERNAME/PASSWORD")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        final UserDetails userDetails=userService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtutility.generateToken(userDetails);
        return  new JwtResponse(token);
    }
}
