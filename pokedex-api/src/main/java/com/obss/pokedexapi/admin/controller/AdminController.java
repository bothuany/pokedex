package com.obss.pokedexapi.admin.controller;

import com.obss.pokedexapi.admin.dto.*;
import com.obss.pokedexapi.admin.mapper.AdminMapper;
import com.obss.pokedexapi.admin.model.Admin;
import com.obss.pokedexapi.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admins")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    @Operation(summary = "Get all admins")
    public ResponseEntity<Page<AdminResponseDto>> getAllAdmins(@RequestParam(name = "page", required = false) Integer page,
                                                               @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(AdminMapper.INSTANCE.adminToAdminResponseDto(adminService.getAllAdmins(page,size)), HttpStatus.OK);
    }

    @GetMapping("/{adminId}")
    @Operation(summary = "Get admin by id")
    public ResponseEntity<AdminResponseDto> getAdminById(@PathVariable("adminId") String adminId){
        return new ResponseEntity<>(AdminMapper.INSTANCE.adminToAdminResponseDto(adminService.getAdminById(adminId)), HttpStatus.OK);
    }
    @GetMapping("/username/{adminUsername}")
    @Operation(summary = "Get admin by username")
    public ResponseEntity<AdminResponseDto> getAdminByUsername(@PathVariable("adminUsername") String adminUsername){
        return new ResponseEntity<>(AdminMapper.INSTANCE.adminToAdminResponseDto(adminService.getAdminByUsername(adminUsername)), HttpStatus.OK);
    }


    @GetMapping("/search")
    @Operation(summary = "Search admin by username")
    public ResponseEntity<Page<AdminResponseDto>> searchAdminByUsername(@RequestParam(name = "username") String username,
                                                                                     @RequestParam(name = "page", required = false) Integer page,
                                                                                     @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(AdminMapper.INSTANCE.adminToAdminResponseDto(adminService.searchAdminsByUsername(username,page,size)), HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Add new admin")
    public ResponseEntity<CreateAdminRequestDto> addAdmin(@RequestBody @Valid CreateAdminRequestDto addAdminRequest){
        Admin admin = AdminMapper.INSTANCE.createAdminRequestDtoToAdmin(addAdminRequest);
        adminService.addAdmin(admin);
        return new ResponseEntity<>(addAdminRequest, HttpStatus.OK);
    }


    @PutMapping("/{adminUsername}")
    @Operation(summary = "Update admin")
    public ResponseEntity<UpdateAdminRequestDto> updateAdmin(@PathVariable(name = "adminUsername") String adminUsername,
                                                          @RequestBody @Valid UpdateAdminRequestDto updateAdminRequest){
        Admin admin = AdminMapper.INSTANCE.updateAdminRequestDtoToAdmin(updateAdminRequest);
        adminService.updateAdminByUsername(adminUsername,admin);
        return new ResponseEntity<>(updateAdminRequest, HttpStatus.OK);
    }

    @PutMapping()
    @Operation(summary = "Update yourself as admin")
    public ResponseEntity<UpdateAdminRequestDto> updateYourselfAsAdmin(@RequestBody @Valid UpdateAdminRequestDto updateAdminRequest){
        Admin admin = AdminMapper.INSTANCE.updateAdminRequestDtoToAdmin(updateAdminRequest);
        adminService.updateYourselfAsAdmin(admin);
        return new ResponseEntity<>(updateAdminRequest, HttpStatus.OK);
    }


    @DeleteMapping("/{adminUsername}")
    @Operation(summary = "Delete admin")
    public ResponseEntity<HttpStatus> deleteAdmin(@PathVariable(name = "adminUsername") String adminUsername){
        adminService.deleteAdminByUsername(adminUsername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    @Operation(summary = "Delete yourself as admin")
    public ResponseEntity<HttpStatus> deleteYourselfAsAdmin(){
        adminService.deleteYourselfAsAdmin();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
