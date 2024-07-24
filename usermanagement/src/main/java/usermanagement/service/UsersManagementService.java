package usermanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import usermanagement.dto.ReqRes;
import usermanagement.entity.OurUsers;
import usermanagement.repository.UsersRepo;

@Service
public class UsersManagementService {
	/*
	 * 
	 * @Autowired private UsersRepo usersRepo;
	 * 
	 * @Autowired private JWTUtils jwtUtils;
	 * 
	 * @Autowired private AuthenticationManager authenticationManager;
	 * 
	 * @Autowired private PasswordEncoder passwordEncoder;
	 * 
	 * public ReqRes register(ReqRes registrationRequest) { ReqRes resp = new
	 * ReqRes(); try {
	 * 
	 * OurUsers ourUsers = new OurUsers();
	 * ourUsers.setEmail(registrationRequest.getEmail());
	 * ourUsers.setCity(registrationRequest.getCity());
	 * ourUsers.setRole(registrationRequest.getRole());
	 * ourUsers.setName(registrationRequest.getName());
	 * ourUsers.setPassword(passwordEncoder.encode(
	 * registrationRequest.getPassword())); OurUsers ourUsersResult =
	 * usersRepo.save(ourUsers);
	 * 
	 * if(ourUsersResult.getId()>0) {
	 * 
	 * resp.setOurUsers((ourUsersResult));
	 * resp.setMessage("User Saved Successfully"); resp.setStatusCode(200); }
	 * 
	 * }catch (Exception e) { resp.setStatusCode(500);
	 * resp.setError(e.getMessage());
	 * 
	 * }
	 * 
	 * return resp; }
	 * 
	 * public ReqRes login(ReqRes loginReqRes) { ReqRes response = new ReqRes(); try
	 * {
	 * 
	 * authenticationManager .authenticate(new
	 * UsernamePasswordAuthenticationToken(loginReqRes.getEmail(),
	 * loginReqRes.getPassword()));
	 * 
	 * var user = usersRepo.findByEmail(loginReqRes.getEmail()).orElseThrow(); var
	 * jwt = jwtUtils.generateToken(user); var refresToken =
	 * jwtUtils.generateRefreshToken(new HashMap<>(), user);
	 * 
	 * response.setStatusCode(200); response.setToken(jwt);
	 * response.setRefreshToken(refresToken); response.setExpirationTime("24Hrs");
	 * response.setMessage("Successfuly Loged in ");
	 * 
	 * } catch (Exception e) { response.setStatusCode(500);
	 * response.setMessage(e.getMessage()); } return response; }
	 * 
	 * 
	 * public ReqRes refreshToken(ReqRes refreshTokenReqiest) { ReqRes response =
	 * new ReqRes(); try {
	 * 
	 * String ourEmail = jwtUtils.extractusername(refreshTokenReqiest.getToken());
	 * OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
	 * 
	 * 
	 * if(jwtUtils.isTokenValid(refreshTokenReqiest.getToken(),users)) {
	 * 
	 * var jwt = jwtUtils.generateToken(users); response.setStatusCode(200);
	 * response.setToken(jwt); response.setRefreshToken(
	 * refreshTokenReqiest.getToken()); response.setExpirationTime("24Hrs");
	 * response.setMessage("Successfuly Loged in "); } response.setStatusCode(200);
	 * return response;
	 * 
	 * } catch (Exception e) { response.setStatusCode(500);
	 * response.setMessage(e.getMessage()); return response; }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * // get information of all users public ReqRes getAllUsers() { ReqRes reqRes =
	 * new ReqRes();
	 * 
	 * try { List<OurUsers> result = usersRepo.findAll(); if(!result.isEmpty()) {
	 * reqRes.setOurUsersList(result); reqRes.setStatusCode(200);
	 * reqRes.setMessage("Successful ");
	 * 
	 * } else { reqRes.setStatusCode(500); reqRes.setMessage("No user found "); }
	 * return reqRes;
	 * 
	 * 
	 * } catch (Exception e) { reqRes.setStatusCode(500);
	 * reqRes.setMessage("Error Accurred  "+e.getMessage()); return reqRes; } }
	 * 
	 * 
	 * // Get users by id public ReqRes getUsersById(Integer id) { ReqRes reqRes =
	 * new ReqRes();
	 * 
	 * try { OurUsers usersByid = usersRepo.findById(id).orElseThrow(()->new
	 * RuntimeErrorException(null, "User Not Found "));
	 * reqRes.setOurUsers(usersByid); reqRes.setStatusCode(200);
	 * reqRes.setMessage("Users with id  "+id+" Found Successfully");
	 * 
	 * 
	 * } catch (Exception e) {
	 * 
	 * reqRes.setStatusCode(500);
	 * reqRes.setMessage("Error Accurred  "+e.getMessage());
	 * 
	 * } return reqRes; }
	 * 
	 * 
	 * // delete user public ReqRes deleteUsers(Integer id) { ReqRes reqRes = new
	 * ReqRes();
	 * 
	 * try { Optional<OurUsers> usOptional = usersRepo.findById(id);
	 * 
	 * if(usOptional.isPresent()) {
	 * 
	 * usersRepo.deleteById(id); reqRes.setStatusCode(200);
	 * reqRes.setMessage("Users  deleted  Successfully");
	 * 
	 * }else { reqRes.setStatusCode(404); reqRes.setMessage("User Not Found ");
	 * 
	 * }
	 * 
	 * 
	 * } catch (Exception e) {
	 * 
	 * reqRes.setStatusCode(500);
	 * reqRes.setMessage("Error Accurred  "+e.getMessage());
	 * 
	 * } return reqRes; }
	 * 
	 * // update user
	 * 
	 * public ReqRes Updateuser(Integer userId, OurUsers updateUser) { ReqRes reqRes
	 * = new ReqRes();
	 * 
	 * try { Optional<OurUsers> usersOptional = usersRepo.findById(userId);
	 * if(usersOptional.isPresent()) { OurUsers existingUser = usersOptional.get();
	 * existingUser.setEmail(updateUser.getEmail());
	 * existingUser.setName(updateUser.getName());
	 * existingUser.setCity(updateUser.getCity());
	 * existingUser.setRole(updateUser.getRole());
	 * 
	 * // check if password is present in the request or only want to update data
	 * onley if(updateUser.getPassword() != null &&
	 * !updateUser.getPassword().isEmpty()) { // Encode the password // and update
	 * it
	 * existingUser.setPassword(passwordEncoder.encode(updateUser.getPassword())); }
	 * 
	 * OurUsers saveUser = usersRepo.save(existingUser);
	 * reqRes.setOurUsers(saveUser); reqRes.setStatusCode(200);
	 * reqRes.setMessage("Users  Updated  Successfully");
	 * 
	 * 
	 * }else { reqRes.setStatusCode(404);
	 * reqRes.setMessage("User Not Found for Udation  ");
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * reqRes.setStatusCode(500);
	 * reqRes.setMessage("Error Accurred while updating User "+e.getMessage());
	 * 
	 * }
	 * 
	 * return reqRes; }
	 * 
	 * 
	 * public ReqRes getMyinfo(String email) {
	 * 
	 * ReqRes reqRes = new ReqRes();
	 * 
	 * try { Optional<OurUsers> usersOptional = usersRepo.findByEmail(email);
	 * if(usersOptional.isPresent()) {
	 * 
	 * reqRes.setOurUsers(usersOptional.get()); reqRes.setStatusCode(200);
	 * reqRes.setMessage("  Successfully");
	 * 
	 * }else { reqRes.setStatusCode(404); reqRes.setMessage("User Not Found   ");
	 * 
	 * }
	 * 
	 * 
	 * 
	 * } catch (Exception e) { reqRes.setStatusCode(500);
	 * reqRes.setMessage("Error Accurred while geting User Information  "+
	 * e.getMessage());
	 * 
	 * } return reqRes; }
	 * 
	 */
	


    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ReqRes register(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            OurUsers ourUser = new OurUsers();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setCity(registrationRequest.getCity());
            ourUser.setRole(registrationRequest.getRole());
            ourUser.setName(registrationRequest.getName());
            ourUser.setPassword(registrationRequest.getPassword());
            System.out.print("password : : --------"+passwordEncoder.encode(registrationRequest.getPassword()));
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            
            
            OurUsers ourUsersResult = usersRepo.save(ourUser);
            
            System.out.print(ourUsersResult.getPassword());
            if (ourUsersResult.getId()>0) {
                resp.setOurUsers((ourUsersResult));
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }





    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<OurUsers> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
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
            OurUsers usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUsers(usersById);
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
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
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

    public ReqRes updateUser(Integer userId, OurUsers updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                OurUsers existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUsers savedUser = usersRepo.save(existingUser);
                reqRes.setOurUsers(savedUser);
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


    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
    
}
