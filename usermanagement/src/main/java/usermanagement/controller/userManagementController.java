package usermanagement.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import usermanagement.dto.ReqRes;
import usermanagement.entity.OurUsers;
import usermanagement.service.UsersManagementService;



@RestController
public class userManagementController {
	/*
	 * 
	 * @Autowired private UsersManagementService usersManagementService;
	 * 
	 * @PostMapping("/auth/register") public ResponseEntity<ReqRes>
	 * regiester(@RequestBody ReqRes reg){ return
	 * ResponseEntity.ok(usersManagementService.register(reg));
	 * 
	 * }
	 * 
	 * 
	 * @PostMapping("/auth/login") public ResponseEntity<ReqRes> login(@RequestBody
	 * ReqRes reg){ return ResponseEntity.ok(usersManagementService.login(reg));
	 * 
	 * }
	 * 
	 * 
	 * @PostMapping("/auth/refresh") public ResponseEntity<ReqRes>
	 * refreshToken(@RequestBody ReqRes reg){ return
	 * ResponseEntity.ok(usersManagementService.refreshToken(reg));
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @GetMapping("/admin/get-all-users") public ResponseEntity<ReqRes>
	 * getAllUsers(@RequestBody ReqRes reg){ return
	 * ResponseEntity.ok(usersManagementService.getAllUsers());
	 * 
	 * }
	 * 
	 * @GetMapping("/admin/get-users/{userId}") public ResponseEntity<ReqRes>
	 * getUsersById(@PathVariable Integer userId){ return
	 * ResponseEntity.ok(usersManagementService.getUsersById(userId));
	 * 
	 * }
	 * 
	 * 
	 * @PutMapping("/admin/update/{userId}") public ResponseEntity<ReqRes>
	 * updateUser(@PathVariable Integer userId,@RequestBody OurUsers reqres){ return
	 * ResponseEntity.ok(usersManagementService.Updateuser(userId,reqres));
	 * 
	 * }
	 * 
	 * @GetMapping("/adminuser/get-profile") public ResponseEntity<ReqRes>
	 * getprofile(){ org.springframework.security.core.Authentication authentication
	 * = SecurityContextHolder.getContext().getAuthentication();
	 * 
	 * String email = authentication.getName(); ReqRes response =
	 * usersManagementService.getMyinfo(email);
	 * 
	 * return ResponseEntity.status(response.getStatusCode()).body(response);
	 * 
	 * }
	 * 
	 * @DeleteMapping("/admin/update/{userId}") public ResponseEntity<ReqRes>
	 * deleteUser(@PathVariable Integer userId){ return
	 * ResponseEntity.ok(usersManagementService.deleteUsers(userId));
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 */
	

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> regeister(@RequestBody ReqRes reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody OurUsers reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteuser(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
    }
