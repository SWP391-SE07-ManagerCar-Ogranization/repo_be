package com.example.service.account;

import com.example.dto.ReqRes;
import com.example.entity.Account;
import com.example.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UsersManagementService {

    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    public ReqRes register(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            if (ourUserDetailsService.findByEmail(registrationRequest.getEmail()) == null) {
                Account ourUser = new Account();
                ourUser.setDob(registrationRequest.getDob());
                ourUser.setAddress(registrationRequest.getAddress());
                ourUser.setRole(roleService.findById(2));
                ourUser.setName(registrationRequest.getName());
                ourUser.setImage(registrationRequest.getImage());
                ourUser.setStatus(true);
                ourUser.setEmail(registrationRequest.getEmail());
                ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
                ourUser.setCreatedAt(new Date());
                Account accountResult = ourUserDetailsService.addAccount(ourUser);
                if (accountResult.getAccountId() > 0) {
                    resp.setAccount((accountResult));
                    resp.setMessage("User Saved Successfully");
                    resp.setStatusCode(200);
                } else {
                    resp.setMessage("Account already exists");
                }
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes login(ReqRes loginRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            setReqRes(loginRequest, response);

        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Login fail");
        }
        return response;
    }

    public ReqRes loginOauth2(ReqRes loginRequest) {
        ReqRes response = new ReqRes();
        try {
            String email = loginRequest.getEmail();
            Account user = ourUserDetailsService.findByEmail(email);
            if (user == null) {
                user = new Account();
                user.setEmail(email);
                user.setImage(loginRequest.getImage());
                user.setName(loginRequest.getName());
                user.setRole(roleService.findById(1));
                user.setStatus(true);
                user.setCreatedAt(new Date());
                ourUserDetailsService.addAccount(user);
            }
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            response.setAccount(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In via OAuth2");
            System.out.println(response);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ReqRes forgotPassword(String phone, boolean flag) {
        ReqRes response = new ReqRes();
        try {
            if (flag) {
                if (ourUserDetailsService.findByPhone(phone) != null) {
                    var user = ourUserDetailsService.findByPhone(phone);
                    var jwt = jwtUtils.generateToken(user);
                    var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
                    response.setStatusCode(200);
                    response.setToken(jwt);
                    response.setRole(user.getRole());
                    response.setImage(user.getImage());
                    response.setRefreshToken(refreshToken);
                    response.setExpirationTime("24Hrs");
                    response.setMessage("Successfully Logged In");
                } else {
                    response.setStatusCode(400);
                    response.setMessage("Can't not find account");
                }
            } else {
                response.setStatusCode(400);
                response.setMessage("Invalid OTP");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        System.out.println(response);
        return response;
    }

    private void setReqRes(ReqRes reqRes, ReqRes response) {
        var user = ourUserDetailsService.findByEmail(reqRes.getEmail());
        var jwt = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
        response.setStatusCode(200);
        response.setToken(jwt);
        response.setRole(user.getRole());
        response.setImage(user.getImage());
        response.setRefreshToken(refreshToken);
        response.setExpirationTime("24Hrs");
        response.setMessage("Successfully Logged In");
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        try {
            String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            Account users = ourUserDetailsService.findByEmail(ourEmail);
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public ReqRes changeNewPassword(ReqRes reqRes) {
        ReqRes response = new ReqRes();
        try {
            String ourEmail = jwtUtils.extractUsername(reqRes.getToken());
            Account users = ourUserDetailsService.findByEmail(ourEmail);
            if (jwtUtils.isTokenValid(reqRes.getToken(), users)) {
                users.setPassword(passwordEncoder.encode(reqRes.getPassword()));
                Account savedUser = ourUserDetailsService.addAccount(users);
                reqRes.setAccount(savedUser);
                response.setMessage("Successfully Change Password");
                response.setStatusCode(200);
            } else {
                response.setMessage("Bad Request");
                response.setStatusCode(400);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<Account> result = ourUserDetailsService.getAllAccount();
            if (!result.isEmpty()) {
                reqRes.setAccountList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            Account usersById = ourUserDetailsService.findById(id);
            reqRes.setAccount(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Account userOptional = ourUserDetailsService.findById(userId);
            if (userOptional != null) {
                ourUserDetailsService.deleteAccount(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, Account updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Account account = ourUserDetailsService.findById(userId);
            if (account != null) {
                account.setEmail(updatedUser.getEmail());
                account.setName(updatedUser.getName());
                System.out.println("ROLE IS: "  +account.getRole());
                account.setRole(account.getRole());
                account.setImage(updatedUser.getImage());
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    account.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                Account savedUser = ourUserDetailsService.addAccount(account);
                reqRes.setAccount(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getMyInfo(String email) {
        ReqRes reqRes = new ReqRes();
        try {
            Account account = ourUserDetailsService.findByEmail(email);
            if (account != null) {
                reqRes.setAccount(account);
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

    public ReqRes getAllCustomer(){
        ReqRes reqRes = new ReqRes();
        try{
            List<Account> result = ourUserDetailsService.getAllAccountByRoleId(1);
            if (!result.isEmpty()){
                reqRes.setAccountList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            }else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No customer found");
            }
        }
        catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return  reqRes;
    }
}
